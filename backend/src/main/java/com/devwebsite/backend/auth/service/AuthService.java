package com.devwebsite.backend.auth.service;

import com.devwebsite.backend.auth.dto.AuthResponse;
import com.devwebsite.backend.auth.dto.LoginRequest;
import com.devwebsite.backend.auth.dto.RefreshResponse;
import com.devwebsite.backend.auth.dto.RegisterRequest;
import com.devwebsite.backend.auth.dto.TokenPair;
import com.devwebsite.backend.auth.entity.RefreshToken;
import com.devwebsite.backend.auth.jwt.JwtTokenProvider;
import com.devwebsite.backend.auth.repository.RefreshTokenRepository;
import com.devwebsite.backend.common.exception.ResourceNotFoundException;
import com.devwebsite.backend.user.entity.User;
import com.devwebsite.backend.user.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.util.HexFormat;

@Service
public class AuthService {

    private static final Logger log = LoggerFactory.getLogger(AuthService.class);

    private final UserRepository userRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final AuthenticationManager authenticationManager;

    public AuthService(
            UserRepository userRepository,
            RefreshTokenRepository refreshTokenRepository,
            PasswordEncoder passwordEncoder,
            JwtTokenProvider jwtTokenProvider,
            AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.refreshTokenRepository = refreshTokenRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenProvider = jwtTokenProvider;
        this.authenticationManager = authenticationManager;
    }

    @Transactional
    public AuthResponse register(RegisterRequest request) {
        if (userRepository.existsByEmail(request.email())) {
            throw new IllegalArgumentException("Email already exists");
        }

        User user = User.builder()
                .email(request.email())
                .password(passwordEncoder.encode(request.password()))
                .nickname(request.nickname())
                .role("ROLE_USER")
                .build();

        user = userRepository.save(user);

        String accessToken = jwtTokenProvider.generateAccessToken(user.getEmail());

        return AuthResponse.of(
                accessToken,
                toUserInfo(user)
        );
    }

    @Transactional
    public AuthResponse login(LoginRequest request) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.email(), request.password())
            );

            User user = (User) authentication.getPrincipal();
            String accessToken = jwtTokenProvider.generateAccessToken(authentication);

            return AuthResponse.of(
                    accessToken,
                    toUserInfo(user)
            );
        } catch (BadCredentialsException e) {
            throw new BadCredentialsException("Invalid email or password");
        }
    }

    @Transactional
    public String createRefreshToken(User user) {
        String rawToken = jwtTokenProvider.generateRefreshToken();
        String tokenHash = hashToken(rawToken);

        long refreshExpirationMs = jwtTokenProvider.getRefreshExpirationMs();
        LocalDateTime expiresAt = LocalDateTime.now().plusSeconds(refreshExpirationMs / 1000);

        RefreshToken refreshToken = RefreshToken.builder()
                .user(user)
                .tokenHash(tokenHash)
                .expiresAt(expiresAt)
                .build();

        refreshTokenRepository.save(refreshToken);

        return rawToken;
    }

    /**
     * Refresh tokens with rotation.
     * Returns both new access token and new refresh token.
     */
    @Transactional
    public TokenPair refreshWithRotation(String rawRefreshToken) {
        String tokenHash = hashToken(rawRefreshToken);

        RefreshToken oldToken = refreshTokenRepository.findByTokenHashWithUser(tokenHash)
                .orElseThrow(() -> new ResourceNotFoundException("Invalid or expired refresh token"));

        if (!oldToken.isValid()) {
            throw new ResourceNotFoundException("Invalid or expired refresh token");
        }

        User user = oldToken.getUser();

        // Generate new tokens
        String accessToken = jwtTokenProvider.generateAccessToken(user.getEmail());
        String newRawRefreshToken = jwtTokenProvider.generateRefreshToken();
        String newTokenHash = hashToken(newRawRefreshToken);

        long refreshExpirationMs = jwtTokenProvider.getRefreshExpirationMs();
        LocalDateTime expiresAt = LocalDateTime.now().plusSeconds(refreshExpirationMs / 1000);

        RefreshToken newToken = RefreshToken.builder()
                .user(user)
                .tokenHash(newTokenHash)
                .expiresAt(expiresAt)
                .build();

        newToken = refreshTokenRepository.save(newToken);

        // Revoke old token and link to new one (rotation)
        oldToken.revoke(newToken);

        return new TokenPair(accessToken, newRawRefreshToken);
    }

    @Transactional
    public void logout(String rawRefreshToken) {
        if (rawRefreshToken == null) {
            return;
        }

        String tokenHash = hashToken(rawRefreshToken);
        refreshTokenRepository.findByTokenHashAndRevokedFalse(tokenHash)
                .ifPresent(RefreshToken::revoke);
    }

    @Transactional
    public void logoutAll(User user) {
        refreshTokenRepository.revokeAllByUser(user);
    }

    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }

    private String hashToken(String token) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(token.getBytes(StandardCharsets.UTF_8));
            return HexFormat.of().formatHex(hash);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("SHA-256 algorithm not available", e);
        }
    }

    private AuthResponse.UserInfo toUserInfo(User user) {
        return new AuthResponse.UserInfo(
                user.getId(),
                user.getEmail(),
                user.getNickname(),
                user.getRole()
        );
    }
}
