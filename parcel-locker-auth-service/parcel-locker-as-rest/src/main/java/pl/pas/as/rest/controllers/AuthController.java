package pl.pas.as.rest.controllers;

import static org.springframework.http.HttpStatus.CONFLICT;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NOT_ACCEPTABLE;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.io.Files;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

import jakarta.validation.Valid;
import jakarta.validation.ValidationException;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import pl.pas.as.core.model.exceptions.ClientManagerException;
import pl.pas.as.core.model.user.Client;
import pl.pas.as.ports.incoming.AuthService;
import pl.pas.as.ports.incoming.UserService;
import pl.pas.as.rest.controllers.dto.ClientDto;
import pl.pas.as.rest.controllers.dto.Credentials;
import pl.pas.as.rest.security.JwtProvider;

@RequiredArgsConstructor
@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;
    private final JwtProvider jwtProvider;
    private final UserService userService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    @Value("${jwt.public.key}")
    private File rsaPublicKeyFile;

    @PostMapping("/login")
    @HystrixCommand(fallbackMethod = "serviceFallback")
    public ResponseEntity<String> login(@RequestBody @Valid Credentials credentials) {
        try {
            Authentication authentication = authService.login(credentials.getTelNumber(), credentials.getPassword());
            String token = jwtProvider.encode(authentication.getName(), authentication.getAuthorities());
            return ResponseEntity.status(OK).body(token);
        } catch (Exception e) {
            return ResponseEntity.status(UNAUTHORIZED).body("Wrong phone number or password");
        }
    }

    @PostMapping("/sign-up")
    @HystrixCommand(fallbackMethod = "serviceFallback")
    public ResponseEntity registerClient(@RequestBody @Valid ClientDto clientDTO) {
        try {
            String token = jwtProvider.encode("INTERNAL", List.of());
            Client newUser = userService.registerClient(
                clientDTO.firstName,
                clientDTO.lastName,
                clientDTO.telNumber,
                bCryptPasswordEncoder.encode(clientDTO.password),
                token
            );
            return ResponseEntity.status(CREATED).body(newUser);
        } catch (ValidationException | NullPointerException e) {
            return ResponseEntity.status(NOT_ACCEPTABLE).build();
        } catch (ClientManagerException e) {
            return ResponseEntity.status(CONFLICT).body(e.getMessage());
        }
    }

    @SneakyThrows
    @GetMapping("/public-key")
    public ResponseEntity getPublicKey() {
        return ResponseEntity.ok().body(Files.toString(rsaPublicKeyFile, StandardCharsets.UTF_8));
    }

    private ResponseEntity<String> serviceFallback(Credentials credentials) {
        String fallbackMessage = "Login service is currently unavailable. Please try again later.";
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(fallbackMessage);
    }

}
