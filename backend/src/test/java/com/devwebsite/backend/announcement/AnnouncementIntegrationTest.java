package com.devwebsite.backend.announcement;

import com.devwebsite.backend.IntegrationTestBase;
import com.devwebsite.backend.announcement.entity.Announcement;
import com.devwebsite.backend.announcement.repository.AnnouncementRepository;
import com.devwebsite.backend.user.entity.User;
import com.devwebsite.backend.user.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Announcement Integration Tests")
class AnnouncementIntegrationTest extends IntegrationTestBase {

    @Autowired
    private AnnouncementRepository announcementRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private User adminUser;

    @BeforeEach
    @Override
    protected void setUpWebTestClient() {
        super.setUpWebTestClient();
        announcementRepository.deleteAll();

        // Create admin user if not exists
        String adminEmail = "admin-test-" + UUID.randomUUID() + "@example.com";
        adminUser = userRepository.save(User.builder()
                .email(adminEmail)
                .password(passwordEncoder.encode("admin123"))
                .nickname("AdminTest")
                .role("ROLE_ADMIN")
                .build());

        // Create sample announcements (published=true will set publishedAt automatically)
        Announcement published1 = Announcement.builder()
                .title("Published Announcement 1")
                .content("Content of published announcement 1")
                .category("general")
                .author(adminUser)
                .published(true)
                .build();

        Announcement published2 = Announcement.builder()
                .title("Published Announcement 2")
                .content("Content of published announcement 2")
                .category("update")
                .author(adminUser)
                .published(true)
                .build();

        Announcement draft = Announcement.builder()
                .title("Draft Announcement")
                .content("Content of draft announcement")
                .category("general")
                .author(adminUser)
                .published(false)
                .build();

        announcementRepository.saveAll(List.of(published1, published2, draft));
    }

    @Test
    @DisplayName("Get announcements - should return only published announcements")
    void getAnnouncements_shouldReturnOnlyPublished() {
        webTestClient.get()
                .uri("/api/v1/announcements")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.content").isArray()
                .jsonPath("$.content.length()").isEqualTo(2)
                .consumeWith(response -> {
                    String body = new String(Objects.requireNonNull(response.getResponseBody()));
                    assertThat(body).doesNotContain("Draft Announcement");
                });
    }

    @Test
    @DisplayName("Get announcements - should support pagination")
    void getAnnouncements_shouldSupportPagination() {
        webTestClient.get()
                .uri("/api/v1/announcements?page=0&size=1")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.content.length()").isEqualTo(1)
                .jsonPath("$.totalElements").isEqualTo(2)
                .jsonPath("$.totalPages").isEqualTo(2);
    }

    @Test
    @DisplayName("Get announcements - should filter by category")
    void getAnnouncements_shouldFilterByCategory() {
        webTestClient.get()
                .uri("/api/v1/announcements?category=update")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.content.length()").isEqualTo(1)
                .jsonPath("$.content[0].category").isEqualTo("update");
    }

    @Test
    @DisplayName("Get announcement by ID - should return announcement")
    void getAnnouncementById_shouldReturnAnnouncement() {
        Announcement announcement = announcementRepository.findAll().stream()
                .filter(Announcement::isPublished)
                .findFirst()
                .orElseThrow();

        webTestClient.get()
                .uri("/api/v1/announcements/" + announcement.getId())
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.id").isEqualTo(announcement.getId().intValue())
                .jsonPath("$.title").isEqualTo(announcement.getTitle());
    }

    @Test
    @DisplayName("Get announcement by ID - should return 404 for draft")
    void getAnnouncementById_shouldReturn404ForDraft() {
        Announcement draft = announcementRepository.findAll().stream()
                .filter(a -> !a.isPublished())
                .findFirst()
                .orElseThrow();

        webTestClient.get()
                .uri("/api/v1/announcements/" + draft.getId())
                .exchange()
                .expectStatus().isNotFound();
    }

    @Test
    @DisplayName("Get announcement by ID - should return 404 for non-existent")
    void getAnnouncementById_shouldReturn404ForNonExistent() {
        webTestClient.get()
                .uri("/api/v1/announcements/99999")
                .exchange()
                .expectStatus().isNotFound();
    }

    @Test
    @DisplayName("Public endpoints - should not require authentication")
    void publicEndpoints_shouldNotRequireAuth() {
        webTestClient.get()
                .uri("/api/v1/announcements")
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    @DisplayName("Admin create announcement - should require authentication")
    void adminCreateAnnouncement_shouldRequireAuth() {
        Map<String, Object> request = Map.of(
                "title", "New Announcement",
                "content", "Content",
                "category", "general"
        );

        webTestClient.post()
                .uri("/api/v1/admin/announcements")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(request)
                .exchange()
                .expectStatus().isForbidden();
    }
}
