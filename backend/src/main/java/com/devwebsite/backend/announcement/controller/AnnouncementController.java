package com.devwebsite.backend.announcement.controller;

import com.devwebsite.backend.announcement.dto.AnnouncementResponse;
import com.devwebsite.backend.announcement.service.AnnouncementService;
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
@RequestMapping("/api/v1/announcements")
@Tag(name = "Announcements", description = "Public announcement endpoints")
public class AnnouncementController {

    private final AnnouncementService announcementService;

    public AnnouncementController(AnnouncementService announcementService) {
        this.announcementService = announcementService;
    }

    @GetMapping
    @Operation(summary = "Get published announcements")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Announcements retrieved successfully")
    })
    public ResponseEntity<Page<AnnouncementResponse>> getAnnouncements(
            @Parameter(description = "Filter by category")
            @RequestParam(required = false) String category,
            @PageableDefault(size = 10, sort = "publishedAt", direction = Sort.Direction.DESC) Pageable pageable) {

        Page<AnnouncementResponse> announcements;
        if (category != null && !category.isBlank()) {
            announcements = announcementService.getPublishedAnnouncementsByCategory(category, pageable);
        } else {
            announcements = announcementService.getPublishedAnnouncements(pageable);
        }

        return ResponseEntity.ok(announcements);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a published announcement by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Announcement retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Announcement not found")
    })
    public ResponseEntity<AnnouncementResponse> getAnnouncement(@PathVariable Long id) {
        AnnouncementResponse announcement = announcementService.getPublishedAnnouncement(id);
        return ResponseEntity.ok(announcement);
    }
}
