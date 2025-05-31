package com.example.projektsale.service;

import com.example.projektsale.entity.Reservation;
import com.example.projektsale.entity.Room;
import com.example.projektsale.entity.User;
import com.example.projektsale.enums.ReservationStatus;
import com.example.projektsale.enums.Role;
import com.example.projektsale.observer.NotificationObserver;
import com.example.projektsale.repository.ReservationRepository;
import com.example.projektsale.repository.RoomRepository;
import com.example.projektsale.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ReservationServiceTest {

    @Mock
    private ReservationRepository reservationRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private RoomRepository roomRepository;

    // DODANE - mockowanie NotificationObserver
    @Mock
    private NotificationObserver notificationObserver;

    @InjectMocks
    private ReservationService reservationService;

    private User testUser;
    private Room testRoom;
    private Reservation testReservation;
    private LocalDateTime startTime;
    private LocalDateTime endTime;

    @BeforeEach
    void setUp() {
        testUser = new User();
        testUser.setId(1L);
        testUser.setUsername("testuser");
        testUser.setRole(Role.USER);

        testRoom = new Room();
        testRoom.setId(1L);
        testRoom.setName("Test Room");

        startTime = LocalDateTime.now().plusHours(1);
        endTime = LocalDateTime.now().plusHours(3);

        testReservation = new Reservation();
        testReservation.setId(1L);
        testReservation.setUser(testUser);
        testReservation.setRoom(testRoom);
        testReservation.setStartTime(startTime);
        testReservation.setEndTime(endTime);
        testReservation.setPurpose("Test meeting");
        testReservation.setStatus(ReservationStatus.PENDING);
    }

    @Test
    void shouldCreateReservation() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(testUser));
        when(roomRepository.findById(1L)).thenReturn(Optional.of(testRoom));
        when(reservationRepository.save(any(Reservation.class))).thenReturn(testReservation);

        Reservation result = reservationService.createReservation(1L, 1L, startTime, endTime, "Test meeting");

        assertNotNull(result);
        assertEquals("Test meeting", result.getPurpose());
        assertEquals(ReservationStatus.PENDING, result.getStatus());
        assertEquals(testUser, result.getUser());
        assertEquals(testRoom, result.getRoom());

        // WERYFIKACJA - sprawdzamy czy repository zostało wywołane
        verify(reservationRepository).save(any(Reservation.class));

        // NOWE - sprawdzamy czy observer został powiadomiony
        verify(notificationObserver).onReservationCreated(any(Reservation.class));
    }

    @Test
    void shouldThrowExceptionWhenUserNotFound() {
        when(userRepository.findById(999L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> reservationService.createReservation(999L, 1L, startTime, endTime, "Test"));
        assertEquals("User not found", exception.getMessage());

        // Observer NIE powinien być wywołany przy błędzie
        verify(notificationObserver, never()).onReservationCreated(any());
    }

    @Test
    void shouldThrowExceptionWhenRoomNotFound() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(testUser));
        when(roomRepository.findById(999L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> reservationService.createReservation(1L, 999L, startTime, endTime, "Test"));
        assertEquals("Room not found", exception.getMessage());

        // Observer NIE powinien być wywołany przy błędzie
        verify(notificationObserver, never()).onReservationCreated(any());
    }

    @Test
    void shouldGetReservationsByUser() {
        List<Reservation> reservations = Arrays.asList(testReservation);
        when(reservationRepository.findByUserIdOrderByStartTimeDesc(1L)).thenReturn(reservations);

        List<Reservation> result = reservationService.getReservationsByUser(1L);

        assertEquals(1, result.size());
        assertEquals(testReservation, result.get(0));
    }

    @Test
    void shouldGetReservationsByRoom() {
        List<Reservation> reservations = Arrays.asList(testReservation);
        when(reservationRepository.findByRoomIdOrderByStartTime(1L)).thenReturn(reservations);

        List<Reservation> result = reservationService.getReservationsByRoom(1L);

        assertEquals(1, result.size());
        assertEquals(testReservation, result.get(0));
    }

    @Test
    void shouldGetAllReservations() {
        List<Reservation> reservations = Arrays.asList(testReservation);
        when(reservationRepository.findAll()).thenReturn(reservations);

        List<Reservation> result = reservationService.getAllReservations();

        assertEquals(1, result.size());
        assertEquals(testReservation, result.get(0));
    }

    @Test
    void shouldDeleteReservation() {
        when(reservationRepository.findById(1L)).thenReturn(Optional.of(testReservation));
        doNothing().when(reservationRepository).delete(testReservation);

        reservationService.deleteReservation(1L);

        verify(reservationRepository).findById(1L);
        verify(reservationRepository).delete(testReservation);
    }

    @Test
    void shouldThrowExceptionWhenDeletingNonExistentReservation() {
        when(reservationRepository.findById(999L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> reservationService.deleteReservation(999L));
        assertEquals("Reservation not found with id: 999", exception.getMessage());
    }


}