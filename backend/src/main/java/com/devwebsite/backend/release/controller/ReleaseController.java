package com.devwebsite.backend.release.controller;

import com.devwebsite.backend.release.dto.ReleaseResponse;
import com.devwebsite.backend.release.entity.Release;
import com.devwebsite.backend.release.service.ReleaseService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/releases")
@Tag(name = "Releases", description = "Public release endpoints")
public class ReleaseController {

    private final ReleaseService releaseService;

    public ReleaseController(ReleaseService releaseService) {
        this.releaseService = releaseService;
    }

    @GetMapping
    @Operation(summary = "Get all releases")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Releases retrieved successfully")
    })
    public ResponseEntity<Page<ReleaseResponse>> getReleases(
            @Parameter(description = "Filter by release type (MAJOR, MINOR, PATCH, HOTFIX)")
            @RequestParam(required = false) Release.ReleaseType type,
            @PageableDefault(size = 10, sort = "releasedAt", direction = Sort.Direction.DESC) Pageable pageable) {

        Page<ReleaseResponse> releases;
        if (type != null) {
            releases = releaseService.getReleasesByType(type, pageable);
        } else {
            releases = releaseService.getAllReleases(pageable);
        }

        return ResponseEntity.ok(releases);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a release by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Release retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Release not found")
    })
    public ResponseEntity<ReleaseResponse> getRelease(@PathVariable Long id) {
        ReleaseResponse release = releaseService.getRelease(id);
        return ResponseEntity.ok(release);
    }
}
