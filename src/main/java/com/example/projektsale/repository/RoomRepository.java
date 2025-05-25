package com.example.projektsale.repository;

import com.example.projektsale.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {

    List<Room> findByIsAvailable(Boolean isAvailable);

    List<Room> findByCapacityGreaterThanEqual(Integer capacity);

    List<Room> findByNameContaining(String name);

    List<Room> findByLocation(String location);
}