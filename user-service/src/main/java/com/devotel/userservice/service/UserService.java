package com.devotel.userservice.service;

import com.devotel.userservice.dto.UserDTO;

import java.util.List;

public interface UserService {
    UserDTO create(String name, String email);
    UserDTO getById(Long id);
    List<UserDTO> getAll();
}