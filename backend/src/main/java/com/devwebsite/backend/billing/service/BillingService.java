package com.devwebsite.backend.billing.service;

import com.devwebsite.backend.billing.dto.ChangePlanRequest;
import com.devwebsite.backend.billing.dto.PlanResponse;
import com.devwebsite.backend.billing.dto.SubscriptionResponse;
import com.devwebsite.backend.billing.entity.Plan;
import com.devwebsite.backend.billing.entity.Subscription;
import com.devwebsite.backend.billing.repository.PlanRepository;
import com.devwebsite.backend.billing.repository.SubscriptionRepository;
import com.devwebsite.backend.common.exception.ResourceNotFoundException;
import com.devwebsite.backend.user.entity.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class BillingService {

    private static final String DEFAULT_PLAN_NAME = "free";

    private final PlanRepository planRepository;
    private final SubscriptionRepository subscriptionRepository;

    public BillingService(PlanRepository planRepository, SubscriptionRepository subscriptionRepository) {
        this.planRepository = planRepository;
        this.subscriptionRepository = subscriptionRepository;
    }

    @Transactional(readOnly = true)
    public List<PlanResponse> getAllPlans() {
        return planRepository.findAllByOrderByPriceMonthlyAsc()
                .stream()
                .map(PlanResponse::from)
                .toList();
    }

    @Transactional(readOnly = true)
    public PlanResponse getPlan(Long id) {
        Plan plan = planRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Plan not found"));
        return PlanResponse.from(plan);
    }

    @Transactional
    public SubscriptionResponse getSubscription(User user) {
        Subscription subscription = subscriptionRepository.findByUserWithPlan(user)
                .orElse(null);

        if (subscription == null) {
            // Auto-create free subscription for user
            Plan freePlan = planRepository.findByName(DEFAULT_PLAN_NAME)
                    .orElseThrow(() -> new ResourceNotFoundException("Default plan not found"));

            subscription = Subscription.builder()
                    .user(user)
                    .plan(freePlan)
                    .build();

            subscription = subscriptionRepository.save(subscription);
        }

        return SubscriptionResponse.from(subscription);
    }

    @Transactional
    public SubscriptionResponse changePlan(User user, ChangePlanRequest request) {
        Plan newPlan = planRepository.findById(request.planId())
                .orElseThrow(() -> new ResourceNotFoundException("Plan not found"));

        Subscription subscription = subscriptionRepository.findByUserWithPlan(user)
                .orElse(null);

        if (subscription == null) {
            // Create new subscription with the requested plan
            subscription = Subscription.builder()
                    .user(user)
                    .plan(newPlan)
                    .build();
            subscription = subscriptionRepository.save(subscription);
        } else {
            // Change existing subscription's plan
            subscription.changePlan(newPlan);
        }

        return SubscriptionResponse.from(subscription);
    }
}
