package com.devwebsite.backend.billing.dto;

import com.devwebsite.backend.billing.entity.Subscription;

import java.time.LocalDateTime;

public record SubscriptionResponse(
        Long id,
        PlanResponse plan,
        String status,
        LocalDateTime startedAt,
        LocalDateTime expiresAt,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
    public static SubscriptionResponse from(Subscription subscription) {
        return new SubscriptionResponse(
                subscription.getId(),
                PlanResponse.from(subscription.getPlan()),
                subscription.getStatus().name(),
                subscription.getStartedAt(),
                subscription.getExpiresAt(),
                subscription.getCreatedAt(),
                subscription.getUpdatedAt()
        );
    }
}
