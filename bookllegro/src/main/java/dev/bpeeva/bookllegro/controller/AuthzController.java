package dev.bpeeva.bookllegro.controller;

import dev.bpeeva.bookllegro.service.UserService;
import dev.bpeeva.bookllegro.security.jwt.JWTService;
import dev.bpeeva.bookllegro.util.dto.UserDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/authz")
@RequiredArgsConstructor
public class AuthzController {

    @Autowired
    private final UserService userService;
    @Autowired
    private final JWTService jwtService;

    @PostMapping("/register")
    private ResponseEntity<String> register(@RequestBody UserDTO userDTO) {

        return ResponseEntity.ok("REACHED REGISTER ENDPOINT!!!");
    }

    @PostMapping("/log-in")
    private ResponseEntity<String> logIn(@RequestBody UserDTO userDTO) {

        return ResponseEntity.ok("REACHED LOG IN ENDPOINT!!!");
    }
}
