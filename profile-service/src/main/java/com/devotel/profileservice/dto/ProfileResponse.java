package com.devotel.profileservice.dto;

public record ProfileResponse(
        Long id,
        String bio,
        String location,
        Integer age,
        UserDTO user
) {}
