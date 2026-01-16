package com.devwebsite.backend.billing.dto;

import com.devwebsite.backend.billing.entity.Plan;

import java.math.BigDecimal;
import java.util.Map;

public record PlanResponse(
        Long id,
        String name,
        String displayName,
        String description,
        BigDecimal priceMonthly,
        Map<String, Object> features
) {
    public static PlanResponse from(Plan plan) {
        return new PlanResponse(
                plan.getId(),
                plan.getName(),
                plan.getDisplayName(),
                plan.getDescription(),
                plan.getPriceMonthly(),
                plan.getFeatures()
        );
    }
}
