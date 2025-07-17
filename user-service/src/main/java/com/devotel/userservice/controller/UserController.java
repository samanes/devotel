package com.devotel.userservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import com.devotel.userservice.service.UserService;
import com.devotel.userservice.dto.UserDTO;
import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService svc;

    @PostMapping
    public UserDTO create(@RequestBody UserDTO dto) {
        return svc.create(dto.name(), dto.email());
    }

    @GetMapping("/{id}")
    public UserDTO getById(@PathVariable Long id) {
        return svc.getById(id);
    }

    @GetMapping
    public List<UserDTO> getAll() {
        return svc.getAll();
    }
}
