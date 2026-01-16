package com.devwebsite.backend.auth.controller;

import com.devwebsite.backend.auth.dto.*;
import com.devwebsite.backend.auth.jwt.JwtTokenProvider;
import com.devwebsite.backend.auth.service.AuthService;
import com.devwebsite.backend.user.entity.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/auth")
@Tag(name = "Authentication", description = "Authentication endpoints")
public class AuthController {

    private static final String REFRESH_TOKEN_COOKIE_NAME = "refreshToken";

    private final AuthService authService;
    private final JwtTokenProvider jwtTokenProvider;
    private final boolean secureCookie;

    public AuthController(
            AuthService authService,
            JwtTokenProvider jwtTokenProvider,
            @Value("${server.ssl.enabled:false}") boolean secureCookie) {
        this.authService = authService;
        this.jwtTokenProvider = jwtTokenProvider;
        this.secureCookie = secureCookie;
    }

    @PostMapping("/register")
    @Operation(summary = "Register a new user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "User registered successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input or email already exists")
    })
    public ResponseEntity<AuthResponse> register(
            @Valid @RequestBody RegisterRequest request,
            HttpServletResponse response) {
        AuthResponse authResponse = authService.register(request);

        User user = authService.getUserByEmail(request.email());
        String refreshToken = authService.createRefreshToken(user);
        setRefreshTokenCookie(response, refreshToken);

        return ResponseEntity.status(HttpStatus.CREATED).body(authResponse);
    }

    @PostMapping("/login")
    @Operation(summary = "Login with email and password")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Login successful"),
            @ApiResponse(responseCode = "401", description = "Invalid credentials")
    })
    public ResponseEntity<AuthResponse> login(
            @Valid @RequestBody LoginRequest request,
            HttpServletResponse response) {
        AuthResponse authResponse = authService.login(request);

        User user = authService.getUserByEmail(request.email());
        String refreshToken = authService.createRefreshToken(user);
        setRefreshTokenCookie(response, refreshToken);

        return ResponseEntity.ok(authResponse);
    }

    @PostMapping("/refresh")
    @Operation(summary = "Refresh access token using refresh token cookie")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Token refreshed successfully"),
            @ApiResponse(responseCode = "401", description = "Invalid or expired refresh token")
    })
    public ResponseEntity<RefreshResponse> refresh(
            HttpServletRequest request,
            HttpServletResponse response) {
        String refreshToken = getRefreshTokenFromCookie(request);
        if (refreshToken == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        TokenPair tokenPair = authService.refreshWithRotation(refreshToken);
        setRefreshTokenCookie(response, tokenPair.refreshToken());

        return ResponseEntity.ok(RefreshResponse.of(tokenPair.accessToken()));
    }

    @PostMapping("/logout")
    @Operation(summary = "Logout and invalidate refresh token")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Logout successful")
    })
    public ResponseEntity<Map<String, String>> logout(
            HttpServletRequest request,
            HttpServletResponse response) {
        String refreshToken = getRefreshTokenFromCookie(request);
        authService.logout(refreshToken);
        clearRefreshTokenCookie(response);

        return ResponseEntity.ok(Map.of("message", "Logged out successfully"));
    }

    private void setRefreshTokenCookie(HttpServletResponse response, String refreshToken) {
        Cookie cookie = new Cookie(REFRESH_TOKEN_COOKIE_NAME, refreshToken);
        cookie.setHttpOnly(true);
        cookie.setSecure(secureCookie);
        cookie.setPath("/api/v1/auth");
        cookie.setMaxAge((int) (jwtTokenProvider.getRefreshExpirationMs() / 1000));
        cookie.setAttribute("SameSite", "Strict");
        response.addCookie(cookie);
    }

    private void clearRefreshTokenCookie(HttpServletResponse response) {
        Cookie cookie = new Cookie(REFRESH_TOKEN_COOKIE_NAME, "");
        cookie.setHttpOnly(true);
        cookie.setSecure(secureCookie);
        cookie.setPath("/api/v1/auth");
        cookie.setMaxAge(0);
        cookie.setAttribute("SameSite", "Strict");
        response.addCookie(cookie);
    }

    private String getRefreshTokenFromCookie(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies == null) {
            return null;
        }

        return Arrays.stream(cookies)
                .filter(cookie -> REFRESH_TOKEN_COOKIE_NAME.equals(cookie.getName()))
                .map(Cookie::getValue)
                .findFirst()
                .orElse(null);
    }
}
