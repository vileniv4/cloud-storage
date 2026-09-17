package ru.netology.cloudstorage.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.netology.cloudstorage.dto.AuthTokenResponse;
import ru.netology.cloudstorage.dto.ErrorResponse;
import ru.netology.cloudstorage.dto.LoginRequest;
import ru.netology.cloudstorage.service.AuthService;

@RestController
@RequestMapping("/")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest request) {
        try {
            String token = authService.login(request.login(), request.password());
            return ResponseEntity.ok(new AuthTokenResponse(token));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponse(e.getMessage(), 1));
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(@RequestHeader("auth-token") String token) {
        authService.logout(token);
        return ResponseEntity.ok().build();
    }
}