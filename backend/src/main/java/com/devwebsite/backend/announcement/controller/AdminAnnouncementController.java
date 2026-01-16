package com.devwebsite.backend.announcement.controller;

import com.devwebsite.backend.announcement.dto.AnnouncementResponse;
import com.devwebsite.backend.announcement.dto.CreateAnnouncementRequest;
import com.devwebsite.backend.announcement.dto.UpdateAnnouncementRequest;
import com.devwebsite.backend.announcement.service.AnnouncementService;
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
@RequestMapping("/api/v1/admin/announcements")
@Tag(name = "Admin Announcements", description = "Admin announcement management endpoints")
@SecurityRequirement(name = "bearerAuth")
public class AdminAnnouncementController {

    private final AnnouncementService announcementService;

    public AdminAnnouncementController(AnnouncementService announcementService) {
        this.announcementService = announcementService;
    }

    @GetMapping
    @Operation(summary = "Get all announcements (including unpublished)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Announcements retrieved successfully"),
            @ApiResponse(responseCode = "403", description = "Access denied")
    })
    public ResponseEntity<Page<AnnouncementResponse>> getAllAnnouncements(
            @PageableDefault(size = 10, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {
        Page<AnnouncementResponse> announcements = announcementService.getAllAnnouncements(pageable);
        return ResponseEntity.ok(announcements);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get an announcement by ID (including unpublished)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Announcement retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Announcement not found"),
            @ApiResponse(responseCode = "403", description = "Access denied")
    })
    public ResponseEntity<AnnouncementResponse> getAnnouncement(@PathVariable Long id) {
        AnnouncementResponse announcement = announcementService.getAnnouncement(id);
        return ResponseEntity.ok(announcement);
    }

    @PostMapping
    @Operation(summary = "Create a new announcement")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Announcement created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "403", description = "Access denied")
    })
    public ResponseEntity<AnnouncementResponse> createAnnouncement(
            @Valid @RequestBody CreateAnnouncementRequest request,
            @AuthenticationPrincipal User author) {
        AnnouncementResponse announcement = announcementService.createAnnouncement(request, author);
        return ResponseEntity.status(HttpStatus.CREATED).body(announcement);
    }

    @PatchMapping("/{id}")
    @Operation(summary = "Update an announcement")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Announcement updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "404", description = "Announcement not found"),
            @ApiResponse(responseCode = "403", description = "Access denied")
    })
    public ResponseEntity<AnnouncementResponse> updateAnnouncement(
            @PathVariable Long id,
            @Valid @RequestBody UpdateAnnouncementRequest request) {
        AnnouncementResponse announcement = announcementService.updateAnnouncement(id, request);
        return ResponseEntity.ok(announcement);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete an announcement")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Announcement deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Announcement not found"),
            @ApiResponse(responseCode = "403", description = "Access denied")
    })
    public ResponseEntity<Map<String, String>> deleteAnnouncement(@PathVariable Long id) {
        announcementService.deleteAnnouncement(id);
        return ResponseEntity.ok(Map.of("message", "Announcement deleted successfully"));
    }
}
