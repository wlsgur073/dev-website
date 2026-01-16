package com.devwebsite.backend.release.entity;

import com.devwebsite.backend.user.entity.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "releases")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Release {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 50)
    private String version;

    @Column(nullable = false, length = 255)
    private String title;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    @Column(name = "release_type", nullable = false, length = 20)
    @Enumerated(EnumType.STRING)
    private ReleaseType releaseType;

    @Column(name = "released_at", nullable = false)
    private LocalDateTime releasedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id")
    private User author;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @Builder
    public Release(String version, String title, String content, ReleaseType releaseType, LocalDateTime releasedAt, User author) {
        this.version = version;
        this.title = title;
        this.content = content;
        this.releaseType = releaseType != null ? releaseType : ReleaseType.MINOR;
        this.releasedAt = releasedAt != null ? releasedAt : LocalDateTime.now();
        this.author = author;
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

    public void update(String version, String title, String content, ReleaseType releaseType, LocalDateTime releasedAt) {
        if (version != null) this.version = version;
        if (title != null) this.title = title;
        if (content != null) this.content = content;
        if (releaseType != null) this.releaseType = releaseType;
        if (releasedAt != null) this.releasedAt = releasedAt;
    }

    public enum ReleaseType {
        MAJOR,
        MINOR,
        PATCH,
        HOTFIX
    }
}
