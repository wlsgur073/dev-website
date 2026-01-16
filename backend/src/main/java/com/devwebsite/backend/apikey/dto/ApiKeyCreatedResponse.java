package com.devwebsite.backend.apikey.dto;

import com.devwebsite.backend.apikey.entity.ApiKey;

import java.time.LocalDateTime;

/**
 * Response for newly created API key.
 * Contains secretOnce which is only returned once at creation time.
 */
public record ApiKeyCreatedResponse(
        Long id,
        String name,
        String keyPrefix,
        String secretOnce,
        LocalDateTime createdAt
) {
    public static ApiKeyCreatedResponse of(ApiKey apiKey, String secretOnce) {
        return new ApiKeyCreatedResponse(
                apiKey.getId(),
                apiKey.getName(),
                apiKey.getKeyPrefix(),
                secretOnce,
                apiKey.getCreatedAt()
        );
    }
}
