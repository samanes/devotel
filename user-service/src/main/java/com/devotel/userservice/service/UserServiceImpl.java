package com.devotel.userservice.service;

import com.devotel.shared.exception.ResourceNotFoundException;
import com.devotel.userservice.dto.UserResponse;
import com.devotel.userservice.entity.User;
import com.devotel.userservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository repo;

    @Override
    public UserResponse create(String name, String email) {
        User saved = repo.save(new User(null, name, email));
        return new UserResponse(saved.getId(), saved.getName(), saved.getEmail());
    }

    @Override
    public UserResponse getById(Long id) {
    return repo.findById(id)
        .map(u -> new UserResponse(u.getId(), u.getName(), u.getEmail()))
        .orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }

    @Override
    public List<UserResponse> getAll() {
        return repo.findAll().stream()
                .map(u -> new UserResponse(u.getId(), u.getName(), u.getEmail()))
                .collect(Collectors.toList());
    }
}
