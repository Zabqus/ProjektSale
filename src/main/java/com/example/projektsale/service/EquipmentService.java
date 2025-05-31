package com.example.projektsale.service;

import com.example.projektsale.entity.ComputerEquipment;
import com.example.projektsale.entity.Equipment;
import com.example.projektsale.entity.ProjectorEquipment;
import com.example.projektsale.entity.Room;
import com.example.projektsale.factory.EquipmentFactory;
import com.example.projektsale.repository.EquipmentRepository;
import com.example.projektsale.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EquipmentService {

    @Autowired
    private EquipmentRepository equipmentRepository;

    @Autowired
    private RoomRepository roomRepository;

    public List<Equipment> getAllEquipment() {
        return equipmentRepository.findAll();
    }

    public ComputerEquipment createComputer(String name, String description, Long roomId,
                                            String operatingSystem, String processor, Integer ramGb) {
        Room room = roomRepository.findById(roomId).orElse(null);

        ComputerEquipment computer = (ComputerEquipment) EquipmentFactory.createComputer(
                name, operatingSystem, processor, ramGb
        );
        computer.setDescription(description);
        computer.setRoom(room);

        return equipmentRepository.save(computer);
    }

    public ProjectorEquipment createProjector(String name, String description, Long roomId,
                                              String resolution, Integer brightness) {
        Room room = roomRepository.findById(roomId).orElse(null);

        ProjectorEquipment projector = (ProjectorEquipment) EquipmentFactory.createProjector(
                name, resolution, brightness
        );
        projector.setDescription(description);
        projector.setRoom(room);

        return equipmentRepository.save(projector);
    }

    public void performMaintenanceOnEquipment(Long id) {
        Equipment equipment = equipmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Equipment not found"));
        equipment.performMaintenance();
    }


    public List<Equipment> getEquipmentByRoom(Long roomId) {
        return equipmentRepository.findByRoomId(roomId);
    }

    public void deleteEquipment(Long id) {
        Equipment equipment = equipmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Equipment not found with id: " + id));

        equipmentRepository.delete(equipment);
        System.out.println("Equipment deleted: " + equipment.getName() + " (ID: " + equipment.getId() + ")");
    }

}