package com.devwebsite.backend.release.dto;

import com.devwebsite.backend.release.entity.Release;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;

public record UpdateReleaseRequest(
        @Size(max = 50, message = "Version must be at most 50 characters")
        String version,

        @Size(max = 255, message = "Title must be at most 255 characters")
        String title,

        String content,

        Release.ReleaseType releaseType,

        LocalDateTime releasedAt
) {
}
