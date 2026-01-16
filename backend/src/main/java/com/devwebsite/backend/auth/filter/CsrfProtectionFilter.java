package com.devwebsite.backend.auth.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Set;

/**
 * CSRF protection filter for cookie-based authentication endpoints.
 * Validates Origin and Referer headers against allowed origins.
 */
@Component
public class CsrfProtectionFilter extends OncePerRequestFilter {

    private static final Logger log = LoggerFactory.getLogger(CsrfProtectionFilter.class);

    private static final Set<String> PROTECTED_PATHS = Set.of(
            "/api/v1/auth/refresh",
            "/api/v1/auth/logout"
    );

    private final List<String> allowedOrigins;

    public CsrfProtectionFilter(@Value("${app.cors.allowed-origins}") String allowedOriginsStr) {
        this.allowedOrigins = List.of(allowedOriginsStr.split(","));
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String path = request.getRequestURI();

        if (shouldProtect(path) && "POST".equalsIgnoreCase(request.getMethod())) {
            if (!validateOrigin(request)) {
                log.warn("CSRF validation failed for path: {} from Origin/Referer mismatch", path);
                response.setStatus(HttpStatus.FORBIDDEN.value());
                response.setContentType("application/problem+json");
                response.getWriter().write("""
                        {
                            "type": "about:blank",
                            "title": "Forbidden",
                            "status": 403,
                            "detail": "CSRF validation failed: Invalid Origin or Referer header"
                        }
                        """);
                return;
            }
        }

        filterChain.doFilter(request, response);
    }

    private boolean shouldProtect(String path) {
        return PROTECTED_PATHS.stream().anyMatch(path::startsWith);
    }

    private boolean validateOrigin(HttpServletRequest request) {
        String origin = request.getHeader("Origin");
        String referer = request.getHeader("Referer");

        // At least one must be present
        if (!StringUtils.hasText(origin) && !StringUtils.hasText(referer)) {
            log.debug("Neither Origin nor Referer header present");
            return false;
        }

        // Validate Origin header if present
        if (StringUtils.hasText(origin)) {
            if (isAllowedOrigin(origin)) {
                return true;
            }
            log.debug("Origin header not in allowed list: {}", origin);
        }

        // Validate Referer header if Origin not valid
        if (StringUtils.hasText(referer)) {
            String refererOrigin = extractOrigin(referer);
            if (refererOrigin != null && isAllowedOrigin(refererOrigin)) {
                return true;
            }
            log.debug("Referer origin not in allowed list: {}", referer);
        }

        return false;
    }

    private boolean isAllowedOrigin(String origin) {
        return allowedOrigins.stream()
                .map(String::trim)
                .anyMatch(allowed -> allowed.equalsIgnoreCase(origin));
    }

    private String extractOrigin(String url) {
        try {
            URI uri = new URI(url);
            int port = uri.getPort();
            if (port == -1 || port == 80 || port == 443) {
                return uri.getScheme() + "://" + uri.getHost();
            }
            return uri.getScheme() + "://" + uri.getHost() + ":" + port;
        } catch (URISyntaxException e) {
            log.debug("Failed to parse URL: {}", url);
            return null;
        }
    }
}
