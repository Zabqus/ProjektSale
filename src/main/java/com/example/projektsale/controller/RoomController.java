package com.example.projektsale.controller;

import com.example.projektsale.entity.Room;
import com.example.projektsale.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/rooms")
public class RoomController {

    @Autowired
    private RoomService roomService;

    @GetMapping
    public ResponseEntity<List<Room>> getAllRooms() {
        List<Room> rooms = roomService.getAllRooms();
        return ResponseEntity.ok(rooms);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Room> getRoomById(@PathVariable Long id) {
        Room room = roomService.getRoomById(id);
        return ResponseEntity.ok(room);
    }

    @PostMapping
    public ResponseEntity<Room> createRoom(@RequestParam String name,
                                           @RequestParam Integer capacity,
                                           @RequestParam String location,
                                           @RequestParam String description) {
        Room room = roomService.createRoom(name, capacity, location, description);
        return ResponseEntity.ok(room);
    }
}