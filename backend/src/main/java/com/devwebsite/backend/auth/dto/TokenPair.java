package com.devwebsite.backend.auth.dto;

public record TokenPair(
        String accessToken,
        String refreshToken
) {
}
