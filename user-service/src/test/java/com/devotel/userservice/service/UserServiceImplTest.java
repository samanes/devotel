package com.devotel.userservice.service;

import com.devotel.userservice.repository.UserRepository;
import com.devotel.userservice.entity.User;
import com.devotel.userservice.dto.UserDTO;
import org.junit.jupiter.api.*;
import org.mockito.*;
import java.util.*;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceImplTest {
    @Mock UserRepository repo;
    @InjectMocks
    UserServiceImpl svc;

    @BeforeEach
    void init() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void create_and_retrieve() {
        var user = new User(1L, "Alice", "a@x.com");
        when(repo.save(any())).thenReturn(user);

        UserDTO dto = svc.create("Alice", "a@x.com");
        assertThat(dto.id()).isEqualTo(1L);
        assertThat(dto.name()).isEqualTo("Alice");
    }

    @Test
    void getById_notFound() {
        when(repo.findById(2L)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> svc.getById(2L))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("User not found");
    }

    @Test
    void listAll() {
        var list = List.of(new User(1L,"A","a@x"), new User(2L,"B","b@x"));
        when(repo.findAll()).thenReturn(list);
        var dtos = svc.getAll();
        assertThat(dtos).hasSize(2)
                .extracting(UserDTO::name).containsExactly("A","B");
    }
}
