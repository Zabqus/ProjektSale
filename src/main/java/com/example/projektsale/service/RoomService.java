package com.example.projektsale.service;

import com.example.projektsale.entity.Room;
import com.example.projektsale.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoomService {

    @Autowired
    private RoomRepository roomRepository;

    public List<Room> getAllRooms() {
        return roomRepository.findAll();
    }

    public Room getRoomById(Long id) {
        return roomRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Room not found"));
    }

    public Room createRoom(String name, Integer capacity, String location, String description) {
        Room room = new Room();
        room.setName(name);
        room.setCapacity(capacity);
        room.setLocation(location);
        room.setDescription(description);
        room.setIsAvailable(true);
        return roomRepository.save(room);
    }
}