package com.devwebsite.backend.release.controller;

import com.devwebsite.backend.release.dto.CreateReleaseRequest;
import com.devwebsite.backend.release.dto.ReleaseResponse;
import com.devwebsite.backend.release.dto.UpdateReleaseRequest;
import com.devwebsite.backend.release.service.ReleaseService;
import com.devwebsite.backend.user.entity.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/admin/releases")
@Tag(name = "Admin Releases", description = "Admin release management endpoints")
@SecurityRequirement(name = "bearerAuth")
public class AdminReleaseController {

    private final ReleaseService releaseService;

    public AdminReleaseController(ReleaseService releaseService) {
        this.releaseService = releaseService;
    }

    @GetMapping
    @Operation(summary = "Get all releases")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Releases retrieved successfully"),
            @ApiResponse(responseCode = "403", description = "Access denied")
    })
    public ResponseEntity<Page<ReleaseResponse>> getAllReleases(
            @PageableDefault(size = 10, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {
        Page<ReleaseResponse> releases = releaseService.getAllReleases(pageable);
        return ResponseEntity.ok(releases);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a release by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Release retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Release not found"),
            @ApiResponse(responseCode = "403", description = "Access denied")
    })
    public ResponseEntity<ReleaseResponse> getRelease(@PathVariable Long id) {
        ReleaseResponse release = releaseService.getRelease(id);
        return ResponseEntity.ok(release);
    }

    @PostMapping
    @Operation(summary = "Create a new release")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Release created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input or version already exists"),
            @ApiResponse(responseCode = "403", description = "Access denied")
    })
    public ResponseEntity<ReleaseResponse> createRelease(
            @Valid @RequestBody CreateReleaseRequest request,
            @AuthenticationPrincipal User author) {
        ReleaseResponse release = releaseService.createRelease(request, author);
        return ResponseEntity.status(HttpStatus.CREATED).body(release);
    }

    @PatchMapping("/{id}")
    @Operation(summary = "Update a release")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Release updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input or version already exists"),
            @ApiResponse(responseCode = "404", description = "Release not found"),
            @ApiResponse(responseCode = "403", description = "Access denied")
    })
    public ResponseEntity<ReleaseResponse> updateRelease(
            @PathVariable Long id,
            @Valid @RequestBody UpdateReleaseRequest request) {
        ReleaseResponse release = releaseService.updateRelease(id, request);
        return ResponseEntity.ok(release);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a release")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Release deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Release not found"),
            @ApiResponse(responseCode = "403", description = "Access denied")
    })
    public ResponseEntity<Map<String, String>> deleteRelease(@PathVariable Long id) {
        releaseService.deleteRelease(id);
        return ResponseEntity.ok(Map.of("message", "Release deleted successfully"));
    }
}
