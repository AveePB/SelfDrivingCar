package dev.bpeeva.bookllegro.security.jwt;

import dev.bpeeva.bookllegro.db.model.Token;
import dev.bpeeva.bookllegro.db.model.User;
import dev.bpeeva.bookllegro.db.repo.TokenRepo;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class JWTService {

    public final static String SECRET_KEY_256BIT = "SvjhiYF8f69sKu2LgXWmsl032ia05TG0T1tVCzJKXs9to/P9SC0A9n3cke0dQOQv";
    public final static SignatureAlgorithm SIGNATURE_ALGORITHM = SignatureAlgorithm.HS256;
    public final static int TOKEN_LIFESPAN = 5 * 60 * 1000; //5 minutes

    @Autowired
    private final TokenRepo tokenRepo;

    public static Key getSecretKey() {
        byte[] keyInBytes = Decoders.BASE64.decode(SECRET_KEY_256BIT);
        return Keys.hmacShaKeyFor(keyInBytes);
    }

    /**
     * Fetches all claims from the token
     * @param bearerToken the bearer token
     * @return claims object
     */
    private Claims extractAllClaims(String bearerToken) {
        return Jwts.parserBuilder()
                .setSigningKey(getSecretKey())
                .build()
                .parseClaimsJws(bearerToken)
                .getBody();
    }

    /**
     * Fetches a principal name from the token
     * @param jwt the json web token
     * @return string object
     */
    public String getPrincipalName(JWT jwt) {

        return extractAllClaims(jwt.getRawForm()).getSubject();
    }

    /**
     * Fetches an expiration date from the token
     * @param jwt the json web token
     * @return date object
     */
    public Date getExpirationDate(JWT jwt) {

        return extractAllClaims(jwt.getRawForm()).getExpiration();
    }

    /**
     * Validates the token
     * @param jwt the json web token
     * @return boolean value
     */
    public boolean isValid(JWT jwt, String username) {
        //Token isn't in db
        if (tokenRepo.findByRawForm(jwt.getRawForm()).isEmpty()) return false;

        //Username doesn't match
        if (!getPrincipalName(jwt).equals(username)) return false;

        //Token is out of date
        if (getExpirationDate(jwt).before(new Date(System.currentTimeMillis()))) return false;

        return true;
    }

    /**
     * Generates a new jwt
     * @param user the user object
     * @return json web token object
     */
    public JWT generateJWT(User user) {

        String rawForm = Jwts.builder()
                .setSubject(user.getUsername())
                .setAudience(user.getAccount().toString())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + TOKEN_LIFESPAN))
                .signWith(getSecretKey(), SIGNATURE_ALGORITHM)
                .compact();

        if (tokenRepo.findByRawForm(rawForm).isEmpty())
            tokenRepo.save(new Token(null, rawForm, user));
        return new JWT(rawForm);
    }
}
