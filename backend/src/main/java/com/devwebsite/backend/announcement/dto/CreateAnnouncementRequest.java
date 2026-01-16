package com.devwebsite.backend.announcement.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreateAnnouncementRequest(
        @NotBlank(message = "Title is required")
        @Size(max = 255, message = "Title must be at most 255 characters")
        String title,

        @NotBlank(message = "Content is required")
        String content,

        @Size(max = 50, message = "Category must be at most 50 characters")
        String category,

        boolean published
) {
}
