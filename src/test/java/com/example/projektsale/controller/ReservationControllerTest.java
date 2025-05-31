package com.example.projektsale.controller;

import com.example.projektsale.config.SecurityConfig;
import com.example.projektsale.entity.Reservation;
import com.example.projektsale.entity.Room;
import com.example.projektsale.entity.User;
import com.example.projektsale.enums.ReservationStatus;
import com.example.projektsale.enums.Role;
import com.example.projektsale.repository.UserRepository;
import com.example.projektsale.service.ReservationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ReservationController.class)
@Import(SecurityConfig.class)
class ReservationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ReservationService reservationService;

    @MockBean
    private UserRepository userRepository;

    private Reservation testReservation;
    private User testUser;
    private Room testRoom;

    @BeforeEach
    void setUp() {
        testUser = new User();
        testUser.setId(1L);
        testUser.setUsername("testuser");
        testUser.setRole(Role.USER);

        testRoom = new Room();
        testRoom.setId(1L);
        testRoom.setName("Test Room");

        testReservation = new Reservation();
        testReservation.setId(1L);
        testReservation.setUser(testUser);
        testReservation.setRoom(testRoom);
        testReservation.setStartTime(LocalDateTime.now().plusHours(1));
        testReservation.setEndTime(LocalDateTime.now().plusHours(3));
        testReservation.setPurpose("Test meeting");
        testReservation.setStatus(ReservationStatus.PENDING);
    }

    @Test
    @WithMockUser(roles = "USER")
    void shouldGetAllReservationsAsUser() throws Exception {
        List<Reservation> reservations = Arrays.asList(testReservation);
        when(reservationService.getAllReservations()).thenReturn(reservations);

        mockMvc.perform(get("/api/reservations"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].purpose").value("Test meeting"))
                .andExpect(jsonPath("$[0].status").value("PENDING"));
    }

    @Test
    @WithMockUser(roles = "USER")
    void shouldCreateReservationAsUser() throws Exception {
        when(reservationService.createReservation(
                eq(1L), eq(1L), any(LocalDateTime.class), any(LocalDateTime.class), eq("Meeting")))
                .thenReturn(testReservation);

        mockMvc.perform(post("/api/reservations")
                        .with(csrf())
                        .param("userId", "1")
                        .param("roomId", "1")
                        .param("startTime", "2025-05-29T09:00:00")
                        .param("endTime", "2025-05-29T11:00:00")
                        .param("purpose", "Meeting"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.purpose").value("Test meeting"))
                .andExpect(jsonPath("$.status").value("PENDING"));
    }

    @Test
    @WithMockUser(roles = "USER")
    void shouldGetReservationsByUser() throws Exception {
        List<Reservation> reservations = Arrays.asList(testReservation);
        when(reservationService.getReservationsByUser(1L)).thenReturn(reservations);

        mockMvc.perform(get("/api/reservations/user/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].purpose").value("Test meeting"));
    }

    @Test
    @WithMockUser(roles = "USER")
    void shouldGetReservationsByRoom() throws Exception {
        List<Reservation> reservations = Arrays.asList(testReservation);
        when(reservationService.getReservationsByRoom(1L)).thenReturn(reservations);

        mockMvc.perform(get("/api/reservations/room/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].purpose").value("Test meeting"));
    }

    @Test
    @WithMockUser(roles = "USER")
    void shouldDeleteReservationAsUser() throws Exception {
        doNothing().when(reservationService).deleteReservation(1L);

        mockMvc.perform(delete("/api/reservations/1")
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Reservation with ID 1 has been deleted successfully")));

        verify(reservationService).deleteReservation(1L);
    }


}