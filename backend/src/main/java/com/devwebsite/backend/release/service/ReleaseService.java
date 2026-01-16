package com.devwebsite.backend.release.service;

import com.devwebsite.backend.common.exception.ResourceNotFoundException;
import com.devwebsite.backend.release.dto.CreateReleaseRequest;
import com.devwebsite.backend.release.dto.ReleaseResponse;
import com.devwebsite.backend.release.dto.UpdateReleaseRequest;
import com.devwebsite.backend.release.entity.Release;
import com.devwebsite.backend.release.repository.ReleaseRepository;
import com.devwebsite.backend.user.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ReleaseService {

    private final ReleaseRepository releaseRepository;

    public ReleaseService(ReleaseRepository releaseRepository) {
        this.releaseRepository = releaseRepository;
    }

    @Transactional(readOnly = true)
    public Page<ReleaseResponse> getAllReleases(Pageable pageable) {
        return releaseRepository.findAllWithAuthor(pageable)
                .map(ReleaseResponse::from);
    }

    @Transactional(readOnly = true)
    public Page<ReleaseResponse> getReleasesByType(Release.ReleaseType releaseType, Pageable pageable) {
        return releaseRepository.findAllByReleaseType(releaseType, pageable)
                .map(ReleaseResponse::from);
    }

    @Transactional(readOnly = true)
    public ReleaseResponse getRelease(Long id) {
        Release release = releaseRepository.findByIdWithAuthor(id)
                .orElseThrow(() -> new ResourceNotFoundException("Release not found"));
        return ReleaseResponse.from(release);
    }

    @Transactional
    public ReleaseResponse createRelease(CreateReleaseRequest request, User author) {
        if (releaseRepository.existsByVersion(request.version())) {
            throw new IllegalArgumentException("Version already exists: " + request.version());
        }

        Release release = Release.builder()
                .version(request.version())
                .title(request.title())
                .content(request.content())
                .releaseType(request.releaseType())
                .releasedAt(request.releasedAt())
                .author(author)
                .build();

        release = releaseRepository.save(release);
        return ReleaseResponse.from(release);
    }

    @Transactional
    public ReleaseResponse updateRelease(Long id, UpdateReleaseRequest request) {
        Release release = releaseRepository.findByIdWithAuthor(id)
                .orElseThrow(() -> new ResourceNotFoundException("Release not found"));

        // Check if new version conflicts with existing one (if version is being changed)
        if (request.version() != null && !request.version().equals(release.getVersion())) {
            if (releaseRepository.existsByVersion(request.version())) {
                throw new IllegalArgumentException("Version already exists: " + request.version());
            }
        }

        release.update(
                request.version(),
                request.title(),
                request.content(),
                request.releaseType(),
                request.releasedAt()
        );

        return ReleaseResponse.from(release);
    }

    @Transactional
    public void deleteRelease(Long id) {
        if (!releaseRepository.existsById(id)) {
            throw new ResourceNotFoundException("Release not found");
        }
        releaseRepository.deleteById(id);
    }
}
