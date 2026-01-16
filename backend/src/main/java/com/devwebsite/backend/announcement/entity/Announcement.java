package com.devwebsite.backend.announcement.entity;

import com.devwebsite.backend.user.entity.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "announcements")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Announcement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 255)
    private String title;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    @Column(length = 50)
    private String category;

    @Column(nullable = false)
    private boolean published;

    @Column(name = "published_at")
    private LocalDateTime publishedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id")
    private User author;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @Builder
    public Announcement(String title, String content, String category, boolean published, User author) {
        this.title = title;
        this.content = content;
        this.category = category;
        this.published = published;
        this.author = author;
        if (published) {
            this.publishedAt = LocalDateTime.now();
        }
    }

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    public void update(String title, String content, String category) {
        if (title != null) this.title = title;
        if (content != null) this.content = content;
        if (category != null) this.category = category;
    }

    public void publish() {
        if (!this.published) {
            this.published = true;
            this.publishedAt = LocalDateTime.now();
        }
    }

    public void unpublish() {
        this.published = false;
        this.publishedAt = null;
    }

    public void setPublished(boolean published) {
        if (published) {
            publish();
        } else {
            unpublish();
        }
    }
}
