package com.example.projektsale.controller;

import com.example.projektsale.entity.Room;
import com.example.projektsale.service.RoomService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/rooms")
@Tag(name = "Room Management", description = "Operations for managing conference rooms")
public class RoomController {

    @Autowired
    private RoomService roomService;

    @GetMapping
    @Operation(summary = "Get all rooms", description = "Returns list of all available conference rooms")
    public ResponseEntity<List<Room>> getAllRooms() {
        List<Room> rooms = roomService.getAllRooms();
        return ResponseEntity.ok(rooms);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get room by ID", description = "Returns specific room details by ID")
    public ResponseEntity<Room> getRoomById(@PathVariable Long id) {
        Room room = roomService.getRoomById(id);
        return ResponseEntity.ok(room);
    }

    @PostMapping
    @Operation(summary = "Create new room", description = "Adds new conference room to the system")
    public ResponseEntity<Room> createRoom(@RequestParam String name,
                                           @RequestParam Integer capacity,
                                           @RequestParam String location,
                                           @RequestParam String description) {
        Room room = roomService.createRoom(name, capacity, location, description);
        return ResponseEntity.ok(room);
    }
}