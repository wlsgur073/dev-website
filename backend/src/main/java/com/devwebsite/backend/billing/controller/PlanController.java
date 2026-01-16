package com.devwebsite.backend.billing.controller;

import com.devwebsite.backend.billing.dto.PlanResponse;
import com.devwebsite.backend.billing.service.BillingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/plans")
@Tag(name = "Plans", description = "Public plan endpoints")
public class PlanController {

    private final BillingService billingService;

    public PlanController(BillingService billingService) {
        this.billingService = billingService;
    }

    @GetMapping
    @Operation(summary = "Get all available plans")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Plans retrieved successfully")
    })
    public ResponseEntity<List<PlanResponse>> getPlans() {
        List<PlanResponse> plans = billingService.getAllPlans();
        return ResponseEntity.ok(plans);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a plan by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Plan retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Plan not found")
    })
    public ResponseEntity<PlanResponse> getPlan(@PathVariable Long id) {
        PlanResponse plan = billingService.getPlan(id);
        return ResponseEntity.ok(plan);
    }
}
