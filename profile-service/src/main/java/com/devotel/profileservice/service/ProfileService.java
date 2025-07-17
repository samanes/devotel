package com.devotel.profileservice.service;

import com.devotel.profileservice.dto.ProfileCreateRequest;
import com.devotel.profileservice.dto.ProfileResponse;

public interface ProfileService {
    ProfileResponse create(ProfileCreateRequest req);
    ProfileResponse get(Long id);
}
