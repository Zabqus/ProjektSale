package com.example.projektsale.controller;

import com.example.projektsale.config.SecurityConfig;
import com.example.projektsale.entity.User;
import com.example.projektsale.enums.Role;
import com.example.projektsale.repository.UserRepository;
import com.example.projektsale.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)
@Import(SecurityConfig.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @MockBean
    private UserRepository userRepository;

    private User testUser;
    private User adminUser;

    @BeforeEach
    void setUp() {
        testUser = new User();
        testUser.setId(1L);
        testUser.setUsername("testuser");
        testUser.setEmail("test@test.com");
        testUser.setRole(Role.USER);

        adminUser = new User();
        adminUser.setId(2L);
        adminUser.setUsername("admin");
        adminUser.setEmail("admin@test.com");
        adminUser.setRole(Role.ADMIN);
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void shouldGetAllUsersAsAdmin() throws Exception {
        List<User> users = Arrays.asList(testUser, adminUser);
        when(userService.getAllUsers()).thenReturn(users);

        mockMvc.perform(get("/api/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].username").value("testuser"))
                .andExpect(jsonPath("$[1].username").value("admin"));
    }

    @Test
    @WithMockUser(roles = "USER")
    void shouldReturn403ForUserRoleWhenGettingAllUsers() throws Exception {
        mockMvc.perform(get("/api/users"))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void shouldGetUserByIdAsAdmin() throws Exception {
        when(userService.getUserById(1L)).thenReturn(testUser);

        mockMvc.perform(get("/api/users/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.username").value("testuser"))
                .andExpect(jsonPath("$.email").value("test@test.com"));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void shouldCreateUserAsAdmin() throws Exception {
        when(userService.createUser(eq("newuser"), eq("new@test.com"), eq("password"), eq(Role.USER)))
                .thenReturn(testUser);

        mockMvc.perform(post("/api/users")
                        .with(csrf())
                        .param("username", "newuser")
                        .param("email", "new@test.com")
                        .param("password", "password")
                        .param("role", "USER"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("testuser"));
    }

    @Test
    @WithMockUser(roles = "USER")
    void shouldReturn403ForUserRoleWhenCreatingUser() throws Exception {
        mockMvc.perform(post("/api/users")
                        .with(csrf())
                        .param("username", "newuser")
                        .param("email", "new@test.com")
                        .param("password", "password")
                        .param("role", "USER"))
                .andExpect(status().isForbidden());
    }
}