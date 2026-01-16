package com.devwebsite.backend.user.dto;

import jakarta.validation.constraints.Size;

public record UpdateUserRequest(
        @Size(min = 2, max = 50, message = "Nickname must be between 2 and 50 characters")
        String nickname,

        @Size(min = 8, max = 100, message = "Password must be between 8 and 100 characters")
        String password
) {
}
