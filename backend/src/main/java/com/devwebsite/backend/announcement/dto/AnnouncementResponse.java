package com.devwebsite.backend.announcement.dto;

import com.devwebsite.backend.announcement.entity.Announcement;

import java.time.LocalDateTime;

public record AnnouncementResponse(
        Long id,
        String title,
        String content,
        String category,
        boolean published,
        LocalDateTime publishedAt,
        AuthorInfo author,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
    public static AnnouncementResponse from(Announcement announcement) {
        AuthorInfo authorInfo = null;
        if (announcement.getAuthor() != null) {
            authorInfo = new AuthorInfo(
                    announcement.getAuthor().getId(),
                    announcement.getAuthor().getNickname()
            );
        }

        return new AnnouncementResponse(
                announcement.getId(),
                announcement.getTitle(),
                announcement.getContent(),
                announcement.getCategory(),
                announcement.isPublished(),
                announcement.getPublishedAt(),
                authorInfo,
                announcement.getCreatedAt(),
                announcement.getUpdatedAt()
        );
    }

    public record AuthorInfo(Long id, String nickname) {
    }
}
