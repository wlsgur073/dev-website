package com.devwebsite.backend.announcement.repository;

import com.devwebsite.backend.announcement.entity.Announcement;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AnnouncementRepository extends JpaRepository<Announcement, Long> {

    @Query("SELECT a FROM Announcement a LEFT JOIN FETCH a.author WHERE a.published = true ORDER BY a.publishedAt DESC")
    Page<Announcement> findAllPublished(Pageable pageable);

    @Query("SELECT a FROM Announcement a LEFT JOIN FETCH a.author WHERE a.published = true AND a.id = :id")
    Optional<Announcement> findByIdAndPublishedTrue(@Param("id") Long id);

    @Query("SELECT a FROM Announcement a LEFT JOIN FETCH a.author WHERE a.published = true AND a.category = :category ORDER BY a.publishedAt DESC")
    Page<Announcement> findAllPublishedByCategory(@Param("category") String category, Pageable pageable);

    @Query("SELECT a FROM Announcement a LEFT JOIN FETCH a.author ORDER BY a.createdAt DESC")
    Page<Announcement> findAllWithAuthor(Pageable pageable);

    @Query("SELECT a FROM Announcement a LEFT JOIN FETCH a.author WHERE a.id = :id")
    Optional<Announcement> findByIdWithAuthor(@Param("id") Long id);
}
