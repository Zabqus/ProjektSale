package com.example.projektsale.controller;

import com.example.projektsale.config.SecurityConfig;
import com.example.projektsale.entity.ComputerEquipment;
import com.example.projektsale.entity.Equipment;
import com.example.projektsale.entity.ProjectorEquipment;
import com.example.projektsale.repository.UserRepository;
import com.example.projektsale.service.EquipmentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(EquipmentController.class)
@Import(SecurityConfig.class)
class EquipmentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EquipmentService equipmentService;

    @MockBean
    private UserRepository userRepository;

    private ComputerEquipment testComputer;
    private ProjectorEquipment testProjector;

    @BeforeEach
    void setUp() {
        testComputer = new ComputerEquipment();
        testComputer.setId(1L);
        testComputer.setName("Test Computer");
        testComputer.setOperatingSystem("Windows 11");
        testComputer.setProcessor("Intel i5");
        testComputer.setRamGb(8);

        testProjector = new ProjectorEquipment();
        testProjector.setId(2L);
        testProjector.setName("Test Projector");
        testProjector.setResolution("4K");
        testProjector.setBrightness(3000);
    }

    @Test
    @WithMockUser(roles = "USER")
    void shouldGetAllEquipmentAsUser() throws Exception {
        List<Equipment> equipment = Arrays.asList(testComputer, testProjector);
        when(equipmentService.getAllEquipment()).thenReturn(equipment);

        mockMvc.perform(get("/api/equipment"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].name").value("Test Computer"))
                .andExpect(jsonPath("$[1].name").value("Test Projector"));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void shouldCreateComputerAsAdmin() throws Exception {
        when(equipmentService.createComputer(
                eq("New Computer"), eq("Description"), eq(1L),
                eq("Windows 11"), eq("Intel i7"), eq(16)))
                .thenReturn(testComputer);

        mockMvc.perform(post("/api/equipment/computer")
                        .with(csrf())
                        .param("name", "New Computer")
                        .param("description", "Description")
                        .param("roomId", "1")
                        .param("operatingSystem", "Windows 11")
                        .param("processor", "Intel i7")
                        .param("ramGb", "16"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Test Computer"));
    }

    @Test
    @WithMockUser(roles = "USER")
    void shouldReturn403ForUserWhenCreatingComputer() throws Exception {
        mockMvc.perform(post("/api/equipment/computer")
                        .with(csrf())
                        .param("name", "New Computer")
                        .param("description", "Description")
                        .param("roomId", "1")
                        .param("operatingSystem", "Windows 11")
                        .param("processor", "Intel i7")
                        .param("ramGb", "16"))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void shouldCreateProjectorAsAdmin() throws Exception {
        when(equipmentService.createProjector(
                eq("New Projector"), eq("Description"), eq(1L),
                eq("4K"), eq(4000)))
                .thenReturn(testProjector);

        mockMvc.perform(post("/api/equipment/projector")
                        .with(csrf())
                        .param("name", "New Projector")
                        .param("description", "Description")
                        .param("roomId", "1")
                        .param("resolution", "4K")
                        .param("brightness", "4000"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Test Projector"));
    }

    @Test
    @WithMockUser(roles = "USER")
    void shouldReturn403ForUserWhenCreatingProjector() throws Exception {
        mockMvc.perform(post("/api/equipment/projector")
                        .with(csrf())
                        .param("name", "New Projector")
                        .param("description", "Description")
                        .param("roomId", "1")
                        .param("resolution", "4K")
                        .param("brightness", "4000"))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(roles = "USER")
    void shouldPerformMaintenanceAsUser() throws Exception {
        mockMvc.perform(post("/api/equipment/1/maintenance")
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Maintenance performed on equipment with ID: 1")));
    }
}