package com.devwebsite.backend.announcement.dto;

import jakarta.validation.constraints.Size;

public record UpdateAnnouncementRequest(
        @Size(max = 255, message = "Title must be at most 255 characters")
        String title,

        String content,

        @Size(max = 50, message = "Category must be at most 50 characters")
        String category,

        Boolean published
) {
}
