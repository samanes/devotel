package com.devotel.profileservice.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ProfileCreateRequest(
        @NotBlank(message = "Bio is required")
        String bio,

        @NotBlank(message = "Location is required")
        String location,

        @Min(value = 0, message = "Age must be positive")
        Integer age,

        @NotNull(message = "User ID is required")
        Long userId

) {}
