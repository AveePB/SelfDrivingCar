package dev.bpeeva.bookllegro.controller;

import dev.bpeeva.bookllegro.db.model.User;
import dev.bpeeva.bookllegro.security.jwt.JWT;
import dev.bpeeva.bookllegro.service.UserService;
import dev.bpeeva.bookllegro.security.jwt.JWTService;
import dev.bpeeva.bookllegro.util.dto.UserDTO;
import dev.bpeeva.bookllegro.util.enums.Account;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

@Controller
@RequestMapping("/authz")
@RequiredArgsConstructor
public class AuthzController {

    private static final String CUSTOMER_REGISTRATION_URL = "customer";
    private static final String SELLER_REGISTRATION_URL = "seller";

    @Autowired
    private final AuthenticationManager authenticationManager;
    @Autowired
    private final PasswordEncoder passwordEncoder;

    @Autowired
    private final UserService userService;
    @Autowired
    private final JWTService jwtService;

    @PostMapping("/register/{accountTypeUrl}")
    private ResponseEntity<JWT> register(@RequestBody UserDTO userDTO, @PathVariable String accountTypeUrl) {
        Optional<User> user = userService.getUser(userDTO.username());
        if (user.isPresent()) return ResponseEntity.status(HttpStatus.CONFLICT).build();

        //Define account type
        Account accountType;
        if (accountTypeUrl.equals(CUSTOMER_REGISTRATION_URL))
            accountType = Account.CUSTOMER;
        else if (accountTypeUrl.equals(SELLER_REGISTRATION_URL))
            accountType = Account.SELLER;
        else
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();

        //Save user
        Optional<User> registeredUser = userService.saveUser(User.builder()
                .username(userDTO.username())
                .password(passwordEncoder.encode(userDTO.password()))
                .account(accountType)
                .isActive(true)
                .build()
        );

        //Generate token
        return registeredUser.map(
                value -> ResponseEntity.ok(jwtService.generateJWT(value)))
                .orElseGet(
                        () -> ResponseEntity.status(HttpStatus.CONFLICT).build()
                );
    }

    @PostMapping("/log-in")
    private ResponseEntity<JWT> logIn(@RequestBody UserDTO userDTO) {
        Optional<User> user = userService.getUser(userDTO.username());
        if (user.isEmpty()) return ResponseEntity.status(HttpStatus.CONFLICT).build();

        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userDTO.username(), userDTO.password()));

        return ResponseEntity.ok(jwtService.generateJWT(user.get()));
    }
}
