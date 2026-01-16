package com.devwebsite.backend.announcement.service;

import com.devwebsite.backend.announcement.dto.AnnouncementResponse;
import com.devwebsite.backend.announcement.dto.CreateAnnouncementRequest;
import com.devwebsite.backend.announcement.dto.UpdateAnnouncementRequest;
import com.devwebsite.backend.announcement.entity.Announcement;
import com.devwebsite.backend.announcement.repository.AnnouncementRepository;
import com.devwebsite.backend.common.exception.ResourceNotFoundException;
import com.devwebsite.backend.user.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AnnouncementService {

    private final AnnouncementRepository announcementRepository;

    public AnnouncementService(AnnouncementRepository announcementRepository) {
        this.announcementRepository = announcementRepository;
    }

    @Transactional(readOnly = true)
    public Page<AnnouncementResponse> getPublishedAnnouncements(Pageable pageable) {
        return announcementRepository.findAllPublished(pageable)
                .map(AnnouncementResponse::from);
    }

    @Transactional(readOnly = true)
    public Page<AnnouncementResponse> getPublishedAnnouncementsByCategory(String category, Pageable pageable) {
        return announcementRepository.findAllPublishedByCategory(category, pageable)
                .map(AnnouncementResponse::from);
    }

    @Transactional(readOnly = true)
    public AnnouncementResponse getPublishedAnnouncement(Long id) {
        Announcement announcement = announcementRepository.findByIdAndPublishedTrue(id)
                .orElseThrow(() -> new ResourceNotFoundException("Announcement not found"));
        return AnnouncementResponse.from(announcement);
    }

    @Transactional(readOnly = true)
    public Page<AnnouncementResponse> getAllAnnouncements(Pageable pageable) {
        return announcementRepository.findAllWithAuthor(pageable)
                .map(AnnouncementResponse::from);
    }

    @Transactional(readOnly = true)
    public AnnouncementResponse getAnnouncement(Long id) {
        Announcement announcement = announcementRepository.findByIdWithAuthor(id)
                .orElseThrow(() -> new ResourceNotFoundException("Announcement not found"));
        return AnnouncementResponse.from(announcement);
    }

    @Transactional
    public AnnouncementResponse createAnnouncement(CreateAnnouncementRequest request, User author) {
        Announcement announcement = Announcement.builder()
                .title(request.title())
                .content(request.content())
                .category(request.category())
                .published(request.published())
                .author(author)
                .build();

        announcement = announcementRepository.save(announcement);
        return AnnouncementResponse.from(announcement);
    }

    @Transactional
    public AnnouncementResponse updateAnnouncement(Long id, UpdateAnnouncementRequest request) {
        Announcement announcement = announcementRepository.findByIdWithAuthor(id)
                .orElseThrow(() -> new ResourceNotFoundException("Announcement not found"));

        announcement.update(request.title(), request.content(), request.category());

        if (request.published() != null) {
            announcement.setPublished(request.published());
        }

        return AnnouncementResponse.from(announcement);
    }

    @Transactional
    public void deleteAnnouncement(Long id) {
        if (!announcementRepository.existsById(id)) {
            throw new ResourceNotFoundException("Announcement not found");
        }
        announcementRepository.deleteById(id);
    }
}
