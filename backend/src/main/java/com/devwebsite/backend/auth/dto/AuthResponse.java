package com.devwebsite.backend.auth.dto;

public record AuthResponse(
        String accessToken,
        String tokenType,
        UserInfo user
) {
    public static AuthResponse of(String accessToken, UserInfo user) {
        return new AuthResponse(accessToken, "Bearer", user);
    }

    public record UserInfo(
            Long id,
            String email,
            String nickname,
            String role
    ) {
    }
}
