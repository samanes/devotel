package com.devotel.profileservice.controller;

import com.devotel.profileservice.dto.ProfileCreateRequest;
import com.devotel.profileservice.dto.ProfileResponse;
import com.devotel.profileservice.service.ProfileService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/profiles")
@RequiredArgsConstructor
public class ProfileController {
    private final ProfileService service;

    @PostMapping
    public ResponseEntity<ProfileResponse> create(
            @Valid @RequestBody ProfileCreateRequest req
    ) {
        ProfileResponse resp = service.create(req);
        URI location = URI.create("/profiles/" + resp.id());
        return ResponseEntity.created(location).body(resp);
    }

    @GetMapping("/{id}")
    public ProfileResponse get(@PathVariable Long id) {
        return service.get(id);
    }
}
