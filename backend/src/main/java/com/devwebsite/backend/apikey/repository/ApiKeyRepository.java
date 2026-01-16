package com.devwebsite.backend.apikey.repository;

import com.devwebsite.backend.apikey.entity.ApiKey;
import com.devwebsite.backend.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ApiKeyRepository extends JpaRepository<ApiKey, Long> {

    List<ApiKey> findAllByUserOrderByCreatedAtDesc(User user);

    Optional<ApiKey> findByIdAndUser(Long id, User user);

    Optional<ApiKey> findByKeyHash(String keyHash);

    @Query("SELECT a FROM ApiKey a JOIN FETCH a.user WHERE a.keyHash = :keyHash")
    Optional<ApiKey> findByKeyHashWithUser(@Param("keyHash") String keyHash);

    long countByUser(User user);
}
