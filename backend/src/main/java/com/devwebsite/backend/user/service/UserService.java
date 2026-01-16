package com.devwebsite.backend.user.service;

import com.devwebsite.backend.common.exception.ResourceNotFoundException;
import com.devwebsite.backend.user.dto.UpdateUserRequest;
import com.devwebsite.backend.user.dto.UserResponse;
import com.devwebsite.backend.user.entity.User;
import com.devwebsite.backend.user.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional(readOnly = true)
    public UserResponse getCurrentUser(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        return UserResponse.from(user);
    }

    @Transactional
    public UserResponse updateCurrentUser(String email, UpdateUserRequest request) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        if (request.nickname() != null && !request.nickname().isBlank()) {
            user.updateNickname(request.nickname());
        }

        if (request.password() != null && !request.password().isBlank()) {
            user.updatePassword(passwordEncoder.encode(request.password()));
        }

        return UserResponse.from(user);
    }
}
