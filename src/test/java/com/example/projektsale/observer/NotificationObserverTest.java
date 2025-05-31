package com.example.projektsale.observer;

import com.example.projektsale.entity.Reservation;
import com.example.projektsale.entity.Room;
import com.example.projektsale.entity.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@ExtendWith(MockitoExtension.class)
class NotificationObserverTest {

    @InjectMocks
    private NotificationObserver notificationObserver;

    private Reservation testReservation;
    private ByteArrayOutputStream outputStream;
    private PrintStream originalOut;

    @BeforeEach
    void setUp() {
        outputStream = new ByteArrayOutputStream();
        originalOut = System.out;
        System.setOut(new PrintStream(outputStream));

        User user = new User();
        user.setUsername("testuser");

        Room room = new Room();
        room.setName("Test Room");

        testReservation = new Reservation();
        testReservation.setUser(user);
        testReservation.setRoom(room);
    }

    @AfterEach
    void tearDown() {
        System.setOut(originalOut);
    }

    @Test
    void shouldPrintNotificationWhenReservationCreated() {
        notificationObserver.onReservationCreated(testReservation);

        String output = outputStream.toString();
        assertTrue(output.contains("Powiadomienie: Nowa rezerwacja dla sali Test Room"));
        assertTrue(output.contains("przez użytkownika testuser"));
    }

    @Test
    void shouldHandleNullReservation() {
        assertDoesNotThrow(() -> notificationObserver.onReservationCreated(null));
    }

    @Test
    void shouldHandleReservationWithNullUser() {
        testReservation.setUser(null);

        assertDoesNotThrow(() -> notificationObserver.onReservationCreated(testReservation));
    }

    @Test
    void shouldHandleReservationWithNullRoom() {
        testReservation.setRoom(null);

        assertDoesNotThrow(() -> notificationObserver.onReservationCreated(testReservation));
    }
}