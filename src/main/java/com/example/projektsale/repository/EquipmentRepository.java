package com.example.projektsale.repository;

import com.example.projektsale.entity.Equipment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EquipmentRepository extends JpaRepository<Equipment, Long> {

    List<Equipment> findByRoomId(Long roomId);

    List<Equipment> findByIsWorking(Boolean isWorking);

    List<Equipment> findByRoomIdAndIsWorking(Long roomId, Boolean isWorking);

    List<Equipment> findByNameContaining(String name);
}