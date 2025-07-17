package com.devotel.profileservice.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ProfileDTO(
        Long id,

        @NotBlank(message = "Bio must not be blank")
        String bio,

        @NotBlank(message = "Location must not be blank")
        String location,

        @NotNull(message = "Age is required")
        @Min(value = 0, message = "Age must be non-negative")
        Integer age,

        @NotNull(message = "User data is required")
        @Valid
        UserDTO user
) {}
