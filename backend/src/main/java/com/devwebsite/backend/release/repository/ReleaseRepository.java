package com.devwebsite.backend.release.repository;

import com.devwebsite.backend.release.entity.Release;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ReleaseRepository extends JpaRepository<Release, Long> {

    @Query("SELECT r FROM Release r LEFT JOIN FETCH r.author ORDER BY r.releasedAt DESC")
    Page<Release> findAllWithAuthor(Pageable pageable);

    @Query("SELECT r FROM Release r LEFT JOIN FETCH r.author WHERE r.id = :id")
    Optional<Release> findByIdWithAuthor(@Param("id") Long id);

    @Query("SELECT r FROM Release r LEFT JOIN FETCH r.author WHERE r.releaseType = :releaseType ORDER BY r.releasedAt DESC")
    Page<Release> findAllByReleaseType(@Param("releaseType") Release.ReleaseType releaseType, Pageable pageable);

    boolean existsByVersion(String version);
}
