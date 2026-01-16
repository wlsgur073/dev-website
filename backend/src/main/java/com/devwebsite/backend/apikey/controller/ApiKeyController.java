package com.devwebsite.backend.apikey.controller;

import com.devwebsite.backend.apikey.dto.ApiKeyCreatedResponse;
import com.devwebsite.backend.apikey.dto.ApiKeyResponse;
import com.devwebsite.backend.apikey.dto.CreateApiKeyRequest;
import com.devwebsite.backend.apikey.service.ApiKeyService;
import com.devwebsite.backend.user.entity.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/api-keys")
@Tag(name = "API Keys", description = "API key management endpoints")
@SecurityRequirement(name = "bearerAuth")
public class ApiKeyController {

    private final ApiKeyService apiKeyService;

    public ApiKeyController(ApiKeyService apiKeyService) {
        this.apiKeyService = apiKeyService;
    }

    @GetMapping
    @Operation(summary = "Get all API keys for current user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "API keys retrieved successfully"),
            @ApiResponse(responseCode = "401", description = "Not authenticated")
    })
    public ResponseEntity<List<ApiKeyResponse>> getApiKeys(@AuthenticationPrincipal User user) {
        List<ApiKeyResponse> apiKeys = apiKeyService.getApiKeys(user);
        return ResponseEntity.ok(apiKeys);
    }

    @PostMapping
    @Operation(summary = "Create a new API key",
            description = "Creates a new API key. The secret is only returned once in this response (secretOnce). " +
                    "Make sure to save it securely as it cannot be retrieved later.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "API key created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input or maximum keys reached"),
            @ApiResponse(responseCode = "401", description = "Not authenticated")
    })
    public ResponseEntity<ApiKeyCreatedResponse> createApiKey(
            @Valid @RequestBody CreateApiKeyRequest request,
            @AuthenticationPrincipal User user) {
        ApiKeyCreatedResponse apiKey = apiKeyService.createApiKey(request, user);
        return ResponseEntity.status(HttpStatus.CREATED).body(apiKey);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete an API key")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "API key deleted successfully"),
            @ApiResponse(responseCode = "404", description = "API key not found"),
            @ApiResponse(responseCode = "401", description = "Not authenticated")
    })
    public ResponseEntity<Map<String, String>> deleteApiKey(
            @PathVariable Long id,
            @AuthenticationPrincipal User user) {
        apiKeyService.deleteApiKey(id, user);
        return ResponseEntity.ok(Map.of("message", "API key deleted successfully"));
    }
}
