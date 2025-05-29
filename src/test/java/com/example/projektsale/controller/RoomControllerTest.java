package com.example.projektsale.controller;

import com.example.projektsale.config.SecurityConfig;
import com.example.projektsale.entity.Room;
import com.example.projektsale.service.ReservationService;
import com.example.projektsale.service.RoomService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class RoomControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RoomService roomService;

    @MockBean
    private ReservationService reservationService;

    private Room testRoom;

    @BeforeEach
    void setUp() {
        testRoom = new Room();
        testRoom.setId(1L);
        testRoom.setName("Conference Room A");
        testRoom.setCapacity(10);
        testRoom.setLocation("Floor 1");
        testRoom.setDescription("Main conference room");
        testRoom.setIsAvailable(true);
    }

    @Test
    @WithMockUser(roles = "USER")
    void shouldGetAllRoomsAsUser() throws Exception {
        List<Room> rooms = Arrays.asList(testRoom);
        when(roomService.getAllRooms()).thenReturn(rooms);

        mockMvc.perform(get("/api/rooms"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].name").value("Conference Room A"))
                .andExpect(jsonPath("$[0].capacity").value(10));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void shouldGetAllRoomsAsAdmin() throws Exception {
        List<Room> rooms = Arrays.asList(testRoom);
        when(roomService.getAllRooms()).thenReturn(rooms);

        mockMvc.perform(get("/api/rooms"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1));
    }

    @Test
    @WithMockUser(roles = "USER")
    void shouldGetRoomByIdAsUser() throws Exception {

        when(roomService.getRoomById(1L)).thenReturn(testRoom);

        mockMvc.perform(get("/api/rooms/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Conference Room A"))
                .andExpect(jsonPath("$.location").value("Floor 1"));
    }

    @Test
    @WithMockUser(roles = "USER")
    void shouldCreateRoomAsUser() throws Exception {
        when(roomService.createRoom(eq("New Room"), eq(15), eq("Floor 2"), eq("New meeting room")))
                .thenReturn(testRoom);

        mockMvc.perform(post("/api/rooms")
                        .with(csrf())
                        .param("name", "New Room")
                        .param("capacity", "15")
                        .param("location", "Floor 2")
                        .param("description", "New meeting room"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Conference Room A"));
    }
}