package com.devwebsite.backend.billing.repository;

import com.devwebsite.backend.billing.entity.Subscription;
import com.devwebsite.backend.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {

    Optional<Subscription> findByUser(User user);

    @Query("SELECT s FROM Subscription s JOIN FETCH s.plan WHERE s.user = :user")
    Optional<Subscription> findByUserWithPlan(@Param("user") User user);

    boolean existsByUser(User user);
}
