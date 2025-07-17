package com.devotel.userservice.dto;

public record UserResponse(
        Long id,
        String name,
        String email
) {}