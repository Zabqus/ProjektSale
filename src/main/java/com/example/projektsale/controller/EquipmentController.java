package com.example.projektsale.controller;

import com.example.projektsale.entity.ComputerEquipment;
import com.example.projektsale.entity.Equipment;
import com.example.projektsale.entity.ProjectorEquipment;
import com.example.projektsale.service.EquipmentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/equipment")
@Tag(name = "Equipment Management", description = "Operations for managing room equipment")
public class EquipmentController {

    @Autowired
    private EquipmentService equipmentService;

    @GetMapping
    @Operation(summary = "Get all equipment", description = "Returns list of all equipment in the system")
    public ResponseEntity<List<Equipment>> getAllEquipment() {
        List<Equipment> equipment = equipmentService.getAllEquipment();
        return ResponseEntity.ok(equipment);
    }

    @PostMapping("/computer")
    @Operation(summary = "Add new computer", description = "Creates new computer equipment entry")
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
    @Operation(summary = "Add new projector", description = "Creates new projector equipment entry")
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
    @Operation(summary = "Perform maintenance", description = "Runs maintenance procedure on specified equipment")
    public ResponseEntity<String> performMaintenance(@PathVariable Long id) {
        equipmentService.performMaintenanceOnEquipment(id);
        return ResponseEntity.ok("Maintenance performed on equipment with ID: " + id);
    }

    @GetMapping("/room/{roomId}")
    @Operation(summary = "Get equipment by room", description = "Returns all equipment assigned to specific room")
    public ResponseEntity<List<Equipment>> getEquipmentByRoom(@PathVariable Long roomId) {
        List<Equipment> equipment = equipmentService.getEquipmentByRoom(roomId);
        return ResponseEntity.ok(equipment);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete equipment", description = "Permanently removes equipment from the system")
    public ResponseEntity<String> deleteEquipment(@PathVariable Long id) {
        equipmentService.deleteEquipment(id);
        return ResponseEntity.ok("Equipment with ID " + id + " has been deleted successfully");
    }
}