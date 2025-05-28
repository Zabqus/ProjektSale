package com.example.projektsale.service;

import com.example.projektsale.entity.Room;
import com.example.projektsale.repository.RoomRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RoomServiceTest {

    @Mock
    private RoomRepository roomRepository;

    @InjectMocks
    private RoomService roomService;

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
    void shouldCreateRoom() {

        when(roomRepository.save(any(Room.class))).thenReturn(testRoom);


        Room result = roomService.createRoom("Conference Room A", 10, "Floor 1", "Main conference room");


        assertNotNull(result);
        assertEquals("Conference Room A", result.getName());
        assertEquals(10, result.getCapacity());
        assertEquals("Floor 1", result.getLocation());
        assertEquals("Main conference room", result.getDescription());
        assertTrue(result.getIsAvailable());
        verify(roomRepository).save(any(Room.class));
    }

    @Test
    void shouldGetAllRooms() {

        Room room2 = new Room();
        room2.setId(2L);
        room2.setName("Meeting Room B");

        List<Room> rooms = Arrays.asList(testRoom, room2);
        when(roomRepository.findAll()).thenReturn(rooms);


        List<Room> result = roomService.getAllRooms();


        assertEquals(2, result.size());
        assertEquals("Conference Room A", result.get(0).getName());
        assertEquals("Meeting Room B", result.get(1).getName());
    }

    @Test
    void shouldGetRoomById() {

        when(roomRepository.findById(1L)).thenReturn(Optional.of(testRoom));


        Room result = roomService.getRoomById(1L);


        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Conference Room A", result.getName());
    }

    @Test
    void shouldThrowExceptionWhenRoomNotFound() {

        when(roomRepository.findById(999L)).thenReturn(Optional.empty());


        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> roomService.getRoomById(999L));
        assertEquals("Room not found", exception.getMessage());
    }
}