package com.example.projektsale.controller;

import com.example.projektsale.entity.ComputerEquipment;
import com.example.projektsale.entity.Equipment;
import com.example.projektsale.entity.ProjectorEquipment;
import com.example.projektsale.service.EquipmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/equipment")
public class EquipmentController {

    @Autowired
    private EquipmentService equipmentService;

    @GetMapping
    public ResponseEntity<List<Equipment>> getAllEquipment() {
        List<Equipment> equipment = equipmentService.getAllEquipment();
        return ResponseEntity.ok(equipment);
    }

    @PostMapping("/computer")
    public ResponseEntity<ComputerEquipment> createComputer(@RequestParam String name,
                                                            @RequestParam String description,
                                                            @RequestParam(required = false) Long roomId,
                                                            @RequestParam String operatingSystem,
                                                            @RequestParam String processor,
                                                            @RequestParam Integer ramGb) {

        ComputerEquipment computer = equipmentService.createComputer(name, description, roomId,
                operatingSystem, processor, ramGb);
        return ResponseEntity.ok(computer);
    }

    @PostMapping("/projector")
    public ResponseEntity<ProjectorEquipment> createProjector(@RequestParam String name,
                                                              @RequestParam String description,
                                                              @RequestParam(required = false) Long roomId,
                                                              @RequestParam String resolution,
                                                              @RequestParam Integer brightness) {

        ProjectorEquipment projector = equipmentService.createProjector(name, description, roomId,
                resolution, brightness);
        return ResponseEntity.ok(projector);
    }

    @PostMapping("/{id}/maintenance")
    public ResponseEntity<String> performMaintenance(@PathVariable Long id) {
        equipmentService.performMaintenanceOnEquipment(id);
        return ResponseEntity.ok("Maintenance performed on equipment with ID: " + id);
    }


}