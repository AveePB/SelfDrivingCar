package dev.bpeeva.bookllegro.security.jwt;

import dev.bpeeva.bookllegro.db.model.User;
import dev.bpeeva.bookllegro.db.repo.TokenRepo;
import dev.bpeeva.bookllegro.db.repo.UserRepo;
import dev.bpeeva.bookllegro.util.enums.Account;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.UnsupportedJwtException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.test.annotation.DirtiesContext;

import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class JWTServiceTests {

    @Autowired
    private final UserRepo userRepo = null;

    @Autowired
    private final JWTService jwtService = null;
    @Autowired
    private final TokenRepo tokenRepo = null;

    private User jessica;
    private JWT jwt;

    @BeforeEach
    void setUp() {
        jessica = userRepo.save(new User(null, "Jessica", "SDFW#$", Account.CUSTOMER, true, null, null));
        jwt = jwtService.generateJWT(jessica);

        assertThat(userRepo.count()).isEqualTo(1);
        assertThat(tokenRepo.count()).isEqualTo(1);
    }

    @Test
    void shouldSuccessfullyGetAPrincipalName() {
        //Act
        String username = jwtService.getPrincipalName(jwt);

        //Assert
        assertThat(username).isEqualTo(jessica.getUsername());
    }

    @Test
    void shouldFailToGetAPrincipalName() throws Exception {
        //Arrange
        String rawForm = Jwts.builder()
                .signWith(JWTService.getSecretKey(), JWTService.SIGNATURE_ALGORITHM)
                .compact();

        try {
            //Act
            String username = jwtService.getPrincipalName(new JWT(rawForm));
            throw new Exception("Should've thrown ar error...");
        }
        //Assert
        catch (UnsupportedJwtException ex) { }
    }

    @Test
    void shouldSuccessfullyGetAExpirationDate() {
        //Act
        Date exprDate = jwtService.getExpirationDate(jwt);

        //Assert
        assertThat(exprDate).isNotNull();
    }

    @Test
    void shouldFailToGetAnExpirationDate() throws Exception {
        //Arrange
        String rawForm = Jwts.builder()
                .signWith(JWTService.getSecretKey(), JWTService.SIGNATURE_ALGORITHM)
                .compact();

        try {
            //Act
            Date exprDate = jwtService.getExpirationDate(new JWT(rawForm));
            throw new Exception("Should've thrown ar error...");
        }
        //Assert
        catch (UnsupportedJwtException ex) { }
    }

    @Test
    void shouldSuccessfullyValidateAJWT() {
        //Act
        boolean isValid = jwtService.isValid(jwt, jessica.getUsername());

        //Assert
        assertThat(isValid).isTrue();
    }

    @Test
    void shouldFailToValidateAJWT() {
        //Arrange
        String rawForm = Jwts.builder()
                .setSubject(jessica.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 2*JWTService.TOKEN_LIFESPAN))
                .signWith(JWTService.getSecretKey(), JWTService.SIGNATURE_ALGORITHM)
                .compact();

        //Act
        boolean isValid = jwtService.isValid(new JWT(rawForm), jessica.getUsername());
        boolean isValid2 = jwtService.isValid(new JWT(rawForm), "J3ss1c4");

        //Assert
        assertThat(isValid).isFalse();
        assertThat(isValid2).isFalse();
    }

    @Test
    void shouldSuccessfullyGenerateAJWT() {
        //Act
        JWT jwt = jwtService.generateJWT(jessica);

        //Assert
        assertThat(tokenRepo.findByRawForm(jwt.getRawForm()).isPresent()).isTrue();
    }

    @Test
    void shouldFailToGenerateAJWT() throws Exception {
        try {
            //Act
            JWT jwt = jwtService.generateJWT(new User(null, "Alex", "#HO@$#@", Account.CUSTOMER, true, null, null));
            throw new Exception("Should've thrown ar error...");
        }
        //Assert
        catch (InvalidDataAccessApiUsageException ex) { }
    }
}
