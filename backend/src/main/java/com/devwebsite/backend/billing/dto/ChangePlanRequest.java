package com.devwebsite.backend.billing.dto;

import jakarta.validation.constraints.NotNull;

public record ChangePlanRequest(
        @NotNull(message = "Plan ID is required")
        Long planId
) {
}
