package com.devwebsite.backend.release.dto;

import com.devwebsite.backend.release.entity.Release;

import java.time.LocalDateTime;

public record ReleaseResponse(
        Long id,
        String version,
        String title,
        String content,
        String releaseType,
        LocalDateTime releasedAt,
        AuthorInfo author,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
    public static ReleaseResponse from(Release release) {
        AuthorInfo authorInfo = null;
        if (release.getAuthor() != null) {
            authorInfo = new AuthorInfo(
                    release.getAuthor().getId(),
                    release.getAuthor().getNickname()
            );
        }

        return new ReleaseResponse(
                release.getId(),
                release.getVersion(),
                release.getTitle(),
                release.getContent(),
                release.getReleaseType().name(),
                release.getReleasedAt(),
                authorInfo,
                release.getCreatedAt(),
                release.getUpdatedAt()
        );
    }

    public record AuthorInfo(Long id, String nickname) {
    }
}
