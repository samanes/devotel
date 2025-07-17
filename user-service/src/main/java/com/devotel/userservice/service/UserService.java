package com.devotel.userservice.service;

import com.devotel.userservice.dto.UserResponse;

import java.util.List;

public interface UserService {
    UserResponse create(String name, String email);
    UserResponse getById(Long id);
    List<UserResponse> getAll();
}