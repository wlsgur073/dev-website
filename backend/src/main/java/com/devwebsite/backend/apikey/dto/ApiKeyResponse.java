package com.devwebsite.backend.apikey.dto;

import com.devwebsite.backend.apikey.entity.ApiKey;

import java.time.LocalDateTime;

public record ApiKeyResponse(
        Long id,
        String name,
        String keyPrefix,
        LocalDateTime createdAt,
        LocalDateTime lastUsedAt
) {
    public static ApiKeyResponse from(ApiKey apiKey) {
        return new ApiKeyResponse(
                apiKey.getId(),
                apiKey.getName(),
                apiKey.getKeyPrefix(),
                apiKey.getCreatedAt(),
                apiKey.getLastUsedAt()
        );
    }
}
