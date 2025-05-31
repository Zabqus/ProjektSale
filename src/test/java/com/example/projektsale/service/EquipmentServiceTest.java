package com.example.projektsale.service;

import com.example.projektsale.entity.ComputerEquipment;
import com.example.projektsale.entity.Equipment;
import com.example.projektsale.entity.ProjectorEquipment;
import com.example.projektsale.entity.Room;
import com.example.projektsale.repository.EquipmentRepository;
import com.example.projektsale.repository.RoomRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EquipmentServiceTest {

    @Mock
    private EquipmentRepository equipmentRepository;

    @Mock
    private RoomRepository roomRepository;

    @InjectMocks
    private EquipmentService equipmentService;

    private Room testRoom;
    private ComputerEquipment testComputer;
    private ProjectorEquipment testProjector;

    @BeforeEach
    void setUp() {
        testRoom = new Room();
        testRoom.setId(1L);
        testRoom.setName("Test Room");

        testComputer = new ComputerEquipment();
        testComputer.setId(1L);
        testComputer.setName("Test Computer");
        testComputer.setOperatingSystem("Windows 11");
        testComputer.setProcessor("Intel i5");
        testComputer.setRamGb(8);
        testComputer.setRoom(testRoom);

        testProjector = new ProjectorEquipment();
        testProjector.setId(2L);
        testProjector.setName("Test Projector");
        testProjector.setResolution("4K");
        testProjector.setBrightness(3000);
        testProjector.setRoom(testRoom);
    }

    @Test
    void shouldCreateComputerUsingFactory() {
        when(roomRepository.findById(1L)).thenReturn(Optional.of(testRoom));
        when(equipmentRepository.save(any(ComputerEquipment.class))).thenReturn(testComputer);

        ComputerEquipment result = equipmentService.createComputer(
                "Test Computer", "Description", 1L, "Windows 11", "Intel i5", 8
        );

        assertNotNull(result);
        assertEquals("Test Computer", result.getName());
        assertEquals("Windows 11", result.getOperatingSystem());
        assertEquals("Intel i5", result.getProcessor());
        assertEquals(8, result.getRamGb());
        assertTrue(result.getIsWorking());
        verify(equipmentRepository).save(any(ComputerEquipment.class));
    }

    @Test
    void shouldCreateProjectorUsingFactory() {
        when(roomRepository.findById(1L)).thenReturn(Optional.of(testRoom));
        when(equipmentRepository.save(any(ProjectorEquipment.class))).thenReturn(testProjector);

        ProjectorEquipment result = equipmentService.createProjector(
                "Test Projector", "Description", 1L, "4K", 3000
        );

        assertNotNull(result);
        assertEquals("Test Projector", result.getName());
        assertEquals("4K", result.getResolution());
        assertEquals(3000, result.getBrightness());
        assertTrue(result.getIsWorking());
        verify(equipmentRepository).save(any(ProjectorEquipment.class));
    }

    @Test
    void shouldPerformMaintenanceUsingPolymorphism() {
        when(equipmentRepository.findById(1L)).thenReturn(Optional.of(testComputer));

        equipmentService.performMaintenanceOnEquipment(1L);

        verify(equipmentRepository).findById(1L);
    }

    @Test
    void shouldThrowExceptionWhenEquipmentNotFoundForMaintenance() {
        when(equipmentRepository.findById(999L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> equipmentService.performMaintenanceOnEquipment(999L));
        assertEquals("Equipment not found", exception.getMessage());
    }

    @Test
    void shouldGetAllEquipment() {
        List<Equipment> equipmentList = Arrays.asList(testComputer, testProjector);
        when(equipmentRepository.findAll()).thenReturn(equipmentList);

        List<Equipment> result = equipmentService.getAllEquipment();

        assertEquals(2, result.size());
        assertTrue(result.contains(testComputer));
        assertTrue(result.contains(testProjector));
    }

    @Test
    void shouldDeleteEquipment() {
        when(equipmentRepository.findById(1L)).thenReturn(Optional.of(testComputer));
        doNothing().when(equipmentRepository).delete(testComputer);

        equipmentService.deleteEquipment(1L);

        verify(equipmentRepository).findById(1L);
        verify(equipmentRepository).delete(testComputer);
    }

    @Test
    void shouldThrowExceptionWhenDeletingNonExistentEquipment() {
        when(equipmentRepository.findById(999L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> equipmentService.deleteEquipment(999L));
        assertEquals("Equipment not found with id: 999", exception.getMessage());
    }


}