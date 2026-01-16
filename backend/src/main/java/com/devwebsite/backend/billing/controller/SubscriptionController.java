package com.devwebsite.backend.billing.controller;

import com.devwebsite.backend.billing.dto.ChangePlanRequest;
import com.devwebsite.backend.billing.dto.SubscriptionResponse;
import com.devwebsite.backend.billing.service.BillingService;
import com.devwebsite.backend.user.entity.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/subscription")
@Tag(name = "Subscription", description = "User subscription endpoints")
@SecurityRequirement(name = "bearerAuth")
public class SubscriptionController {

    private final BillingService billingService;

    public SubscriptionController(BillingService billingService) {
        this.billingService = billingService;
    }

    @GetMapping
    @Operation(summary = "Get current user's subscription",
            description = "Returns the current subscription. If no subscription exists, creates a free subscription.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Subscription retrieved successfully"),
            @ApiResponse(responseCode = "401", description = "Not authenticated")
    })
    public ResponseEntity<SubscriptionResponse> getSubscription(@AuthenticationPrincipal User user) {
        SubscriptionResponse subscription = billingService.getSubscription(user);
        return ResponseEntity.ok(subscription);
    }

    @PostMapping
    @Operation(summary = "Change subscription plan",
            description = "Changes the current subscription to a different plan. This is a stub implementation without actual payment processing.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Plan changed successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "404", description = "Plan not found"),
            @ApiResponse(responseCode = "401", description = "Not authenticated")
    })
    public ResponseEntity<SubscriptionResponse> changePlan(
            @AuthenticationPrincipal User user,
            @Valid @RequestBody ChangePlanRequest request) {
        SubscriptionResponse subscription = billingService.changePlan(user, request);
        return ResponseEntity.ok(subscription);
    }
}
