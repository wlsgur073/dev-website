package com.devwebsite.backend.apikey.service;

import com.devwebsite.backend.apikey.dto.ApiKeyCreatedResponse;
import com.devwebsite.backend.apikey.dto.ApiKeyResponse;
import com.devwebsite.backend.apikey.dto.CreateApiKeyRequest;
import com.devwebsite.backend.apikey.entity.ApiKey;
import com.devwebsite.backend.apikey.repository.ApiKeyRepository;
import com.devwebsite.backend.common.exception.ResourceNotFoundException;
import com.devwebsite.backend.user.entity.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.HexFormat;
import java.util.List;

@Service
public class ApiKeyService {

    private static final String KEY_PREFIX = "sk_";
    private static final int KEY_LENGTH = 32;
    private static final int DISPLAY_PREFIX_LENGTH = 8;
    private static final int MAX_KEYS_PER_USER = 10;

    private final ApiKeyRepository apiKeyRepository;
    private final SecureRandom secureRandom;

    public ApiKeyService(ApiKeyRepository apiKeyRepository) {
        this.apiKeyRepository = apiKeyRepository;
        this.secureRandom = new SecureRandom();
    }

    @Transactional(readOnly = true)
    public List<ApiKeyResponse> getApiKeys(User user) {
        return apiKeyRepository.findAllByUserOrderByCreatedAtDesc(user)
                .stream()
                .map(ApiKeyResponse::from)
                .toList();
    }

    @Transactional
    public ApiKeyCreatedResponse createApiKey(CreateApiKeyRequest request, User user) {
        // Check max keys limit
        long keyCount = apiKeyRepository.countByUser(user);
        if (keyCount >= MAX_KEYS_PER_USER) {
            throw new IllegalArgumentException("Maximum number of API keys reached (" + MAX_KEYS_PER_USER + ")");
        }

        // Generate raw key
        String rawKey = generateRawKey();

        // Extract prefix for display (e.g., "sk_abc123...")
        String displayPrefix = rawKey.substring(0, Math.min(DISPLAY_PREFIX_LENGTH, rawKey.length())) + "...";

        // Hash the key for storage
        String keyHash = hashKey(rawKey);

        // Create and save API key
        ApiKey apiKey = ApiKey.builder()
                .user(user)
                .name(request.name())
                .keyPrefix(displayPrefix)
                .keyHash(keyHash)
                .build();

        apiKey = apiKeyRepository.save(apiKey);

        // Return with secretOnce (only shown once)
        return ApiKeyCreatedResponse.of(apiKey, rawKey);
    }

    @Transactional
    public void deleteApiKey(Long id, User user) {
        ApiKey apiKey = apiKeyRepository.findByIdAndUser(id, user)
                .orElseThrow(() -> new ResourceNotFoundException("API key not found"));

        apiKeyRepository.delete(apiKey);
    }

    /**
     * Validate an API key and return the associated user.
     * Updates last_used_at timestamp.
     */
    @Transactional
    public User validateApiKey(String rawKey) {
        String keyHash = hashKey(rawKey);

        ApiKey apiKey = apiKeyRepository.findByKeyHashWithUser(keyHash)
                .orElseThrow(() -> new ResourceNotFoundException("Invalid API key"));

        apiKey.updateLastUsedAt();

        return apiKey.getUser();
    }

    private String generateRawKey() {
        byte[] randomBytes = new byte[KEY_LENGTH];
        secureRandom.nextBytes(randomBytes);
        String randomPart = HexFormat.of().formatHex(randomBytes);
        return KEY_PREFIX + randomPart;
    }

    private String hashKey(String key) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(key.getBytes(StandardCharsets.UTF_8));
            return HexFormat.of().formatHex(hash);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("SHA-256 algorithm not available", e);
        }
    }
}
