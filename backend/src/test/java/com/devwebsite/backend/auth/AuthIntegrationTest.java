package com.devwebsite.backend.auth;

import com.devwebsite.backend.IntegrationTestBase;
import com.devwebsite.backend.auth.repository.RefreshTokenRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import java.util.Map;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DisplayName("Auth Integration Tests")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class AuthIntegrationTest extends IntegrationTestBase {

    @Autowired
    private RefreshTokenRepository refreshTokenRepository;

    private static String accessToken;
    private static String refreshTokenCookie;
    private static final String TEST_EMAIL = "test-" + UUID.randomUUID() + "@example.com";
    private static final String TEST_PASSWORD = "password123";
    private static final String TEST_NICKNAME = "TestUser";

    @BeforeAll
    void cleanUpBefore() {
        // Clean up old refresh tokens from previous test runs
        refreshTokenRepository.deleteAll();
    }

    @Test
    @Order(1)
    @DisplayName("Register - should create new user and return access token")
    void register_shouldCreateUserAndReturnToken() {
        Map<String, String> request = Map.of(
                "email", TEST_EMAIL,
                "password", TEST_PASSWORD,
                "nickname", TEST_NICKNAME
        );

        webTestClient.post()
                .uri("/api/v1/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(request)
                .exchange()
                .expectStatus().isCreated()
                .expectBody()
                .jsonPath("$.accessToken").isNotEmpty()
                .jsonPath("$.tokenType").isEqualTo("Bearer")
                .jsonPath("$.user.email").isEqualTo(TEST_EMAIL)
                .jsonPath("$.user.nickname").isEqualTo(TEST_NICKNAME)
                .jsonPath("$.user.role").isEqualTo("ROLE_USER")
                .consumeWith(response -> {
                    String setCookie = response.getResponseHeaders().getFirst("Set-Cookie");
                    assertThat(setCookie).isNotNull();
                    assertThat(setCookie).contains("refreshToken=");
                    refreshTokenCookie = setCookie;

                    byte[] body = response.getResponseBody();
                    assertThat(body).isNotNull();
                });
    }

    @Test
    @Order(2)
    @DisplayName("Register - should fail with duplicate email")
    void register_shouldFailWithDuplicateEmail() {
        Map<String, String> request = Map.of(
                "email", TEST_EMAIL,
                "password", TEST_PASSWORD,
                "nickname", "AnotherNickname"
        );

        webTestClient.post()
                .uri("/api/v1/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(request)
                .exchange()
                .expectStatus().isBadRequest();
    }

    @Test
    @Order(3)
    @DisplayName("Login - should authenticate and return tokens")
    void login_shouldAuthenticateAndReturnTokens() {
        Map<String, String> request = Map.of(
                "email", TEST_EMAIL,
                "password", TEST_PASSWORD
        );

        webTestClient.post()
                .uri("/api/v1/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(request)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.accessToken").isNotEmpty()
                .jsonPath("$.tokenType").isEqualTo("Bearer")
                .consumeWith(response -> {
                    String setCookie = response.getResponseHeaders().getFirst("Set-Cookie");
                    assertThat(setCookie).isNotNull();
                    refreshTokenCookie = setCookie;
                });
    }

    @Test
    @Order(4)
    @DisplayName("Login - should fail with wrong password")
    void login_shouldFailWithWrongPassword() {
        Map<String, String> request = Map.of(
                "email", TEST_EMAIL,
                "password", "wrongpassword"
        );

        webTestClient.post()
                .uri("/api/v1/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(request)
                .exchange()
                .expectStatus().isUnauthorized();
    }

    @Test
    @Order(5)
    @DisplayName("Login - should return user info and update accessToken")
    void login_shouldReturnUserInfoAndUpdateAccessToken() {
        Map<String, String> request = Map.of(
                "email", TEST_EMAIL,
                "password", TEST_PASSWORD
        );

        webTestClient.post()
                .uri("/api/v1/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(request)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.accessToken").value(token -> {
                    accessToken = (String) token;
                    assertThat(accessToken).isNotEmpty();
                });
    }

    @Test
    @Order(6)
    @DisplayName("Get Me - should return current user info")
    void getMe_shouldReturnCurrentUser() {
        // First login to get access token
        Map<String, String> loginRequest = Map.of(
                "email", TEST_EMAIL,
                "password", TEST_PASSWORD
        );

        webTestClient.post()
                .uri("/api/v1/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(loginRequest)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.accessToken").value(token -> {
                    accessToken = (String) token;
                });

        // Then get me
        webTestClient.get()
                .uri("/api/v1/me")
                .header("Authorization", "Bearer " + accessToken)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.email").isEqualTo(TEST_EMAIL)
                .jsonPath("$.nickname").isEqualTo(TEST_NICKNAME);
    }

    @Test
    @Order(7)
    @Disabled("Requires isolated database cleanup - to be fixed")
    @DisplayName("Refresh - should rotate tokens")
    void refresh_shouldRotateTokens() {
        // Create a fresh user for this test
        String refreshTestEmail = "refresh-test-" + UUID.randomUUID() + "@example.com";
        Map<String, String> registerRequest = Map.of(
                "email", refreshTestEmail,
                "password", TEST_PASSWORD,
                "nickname", "RefreshTestUser"
        );

        final String[] cookie = new String[1];

        // Register and get refresh token
        webTestClient.post()
                .uri("/api/v1/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(registerRequest)
                .exchange()
                .expectStatus().isCreated()
                .expectBody()
                .consumeWith(response -> {
                    String setCookie = response.getResponseHeaders().getFirst("Set-Cookie");
                    cookie[0] = extractCookieValue(setCookie);
                });

        // Then refresh
        webTestClient.post()
                .uri("/api/v1/auth/refresh")
                .header("Cookie", cookie[0])
                .header("Origin", "http://localhost:3000")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.accessToken").isNotEmpty()
                .consumeWith(response -> {
                    String newCookie = response.getResponseHeaders().getFirst("Set-Cookie");
                    assertThat(newCookie).isNotNull();
                    assertThat(newCookie).contains("refreshToken=");
                });
    }

    @Test
    @Order(8)
    @Disabled("Requires isolated database cleanup - to be fixed")
    @DisplayName("Logout - should invalidate refresh token")
    void logout_shouldInvalidateRefreshToken() {
        // Create a fresh user for this test
        String logoutTestEmail = "logout-test-" + UUID.randomUUID() + "@example.com";
        Map<String, String> registerRequest = Map.of(
                "email", logoutTestEmail,
                "password", TEST_PASSWORD,
                "nickname", "LogoutTestUser"
        );

        final String[] cookie = new String[1];

        // Register and get refresh token
        webTestClient.post()
                .uri("/api/v1/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(registerRequest)
                .exchange()
                .expectStatus().isCreated()
                .expectBody()
                .consumeWith(response -> {
                    String setCookie = response.getResponseHeaders().getFirst("Set-Cookie");
                    cookie[0] = extractCookieValue(setCookie);
                });

        // Then logout
        webTestClient.post()
                .uri("/api/v1/auth/logout")
                .header("Cookie", cookie[0])
                .header("Origin", "http://localhost:3000")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.message").isEqualTo("Logged out successfully")
                .consumeWith(response -> {
                    String responseCookie = response.getResponseHeaders().getFirst("Set-Cookie");
                    assertThat(responseCookie).isNotNull();
                    assertThat(responseCookie).contains("Max-Age=0");
                });
    }

    private String extractAccessToken(byte[] body) {
        if (body == null) return null;
        String json = new String(body);
        int start = json.indexOf("\"accessToken\":\"") + 15;
        int end = json.indexOf("\"", start);
        return json.substring(start, end);
    }

    private String extractCookieValue(String setCookieHeader) {
        // Set-Cookie: refreshToken=xxx; HttpOnly; Path=... -> refreshToken=xxx
        if (setCookieHeader == null) return null;
        String[] parts = setCookieHeader.split(";");
        return parts[0].trim();  // Returns "refreshToken=xxx"
    }
}
