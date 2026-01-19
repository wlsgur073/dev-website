package com.devwebsite.backend.billing.dto;

import java.time.LocalDateTime;

public record UsageStatsResponse(
        int requestsToday,
        int requestsThisMonth,
        int dailyLimit,
        int monthlyLimit,
        LocalDateTime lastRequestAt
) {
    public static UsageStatsResponse stub() {
        return new UsageStatsResponse(
                0,
                0,
                1000,
                10000,
                null
        );
    }
}
