package com.example.projektsale.controller;

import com.example.projektsale.entity.Reservation;
import com.example.projektsale.service.ReservationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/reservations")
@Tag(name = "Reservation Management", description = "Operations for managing room reservations")
public class ReservationController {

    @Autowired
    private ReservationService reservationService;

    @GetMapping
    @Operation(summary = "Get all reservations", description = "Returns list of all room reservations")
    public ResponseEntity<List<Reservation>> getAllReservations() {
        List<Reservation> reservations = reservationService.getAllReservations();
        return ResponseEntity.ok(reservations);
    }

    @PostMapping
    @Operation(summary = "Create new reservation", description = "Books a room for specified time period")
    public ResponseEntity<Reservation> createReservation(@RequestParam Long userId,
                                                         @RequestParam Long roomId,
                                                         @RequestParam String startTime,
                                                         @RequestParam String endTime,
                                                         @RequestParam String purpose) {

        LocalDateTime start = LocalDateTime.parse(startTime);
        LocalDateTime end = LocalDateTime.parse(endTime);

        Reservation reservation = reservationService.createReservation(userId, roomId, start, end, purpose);
        return ResponseEntity.ok(reservation);
    }

    @GetMapping("/user/{userId}")
    @Operation(summary = "Get reservations by user", description = "Returns all reservations made by specific user")
    public ResponseEntity<List<Reservation>> getReservationsByUser(@PathVariable Long userId) {
        List<Reservation> reservations = reservationService.getReservationsByUser(userId);
        return ResponseEntity.ok(reservations);
    }

    @GetMapping("/room/{roomId}")
    @Operation(summary = "Get reservations by room", description = "Returns all reservations for specific room")
    public ResponseEntity<List<Reservation>> getReservationsByRoom(@PathVariable Long roomId) {
        List<Reservation> reservations = reservationService.getReservationsByRoom(roomId);
        return ResponseEntity.ok(reservations);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Cancel reservation", description = "Permanently deletes reservation by ID")
    public ResponseEntity<String> deleteReservation(@PathVariable Long id) {
        reservationService.deleteReservation(id);
        return ResponseEntity.ok("Reservation with ID " + id + " has been deleted successfully");
    }
}