package com.devotel.profileservice.service;

import com.devotel.profileservice.dto.*;
import com.devotel.profileservice.entity.Profile;
import com.devotel.profileservice.repository.ProfileRepository;
import com.devotel.userservice.soap.gen.GetUserByIdResponse;
import com.devotel.userservice.soap.gen.User;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ws.client.core.WebServiceTemplate;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProfileServiceImplTest {

    @Mock ProfileRepository repo;
    @Mock WebServiceTemplate ws;
    @InjectMocks ProfileServiceImpl svc;

    @Test
    void create_successful() {
        ProfileCreateRequest dto = new ProfileCreateRequest(
                "bio", "loc", 30, 1L
        );
        // SOAP stub
        GetUserByIdResponse resp = new GetUserByIdResponse();
        User u = new User();
        u.setId(1L); u.setName("A"); u.setEmail("a@x.com");
        resp.setUser(u);
        when(ws.marshalSendAndReceive(any())).thenReturn(resp);
        // Repo stub
        when(repo.save(any())).thenAnswer(i -> {
            Profile p = i.getArgument(0);
            p.setId(5L);
            return p;
        });

        ProfileResponse result = svc.create(dto);
        assertThat(result.id()).isEqualTo(5L);
        assertThat(result.user().name()).isEqualTo("A");
    }

    @Test
    void get_notFound() {
        when(repo.findById(9L)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> svc.get(9L))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("Profile not found");
    }
}
