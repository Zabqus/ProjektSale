package com.example.projektsale.factory;

import com.example.projektsale.entity.ComputerEquipment;
import com.example.projektsale.entity.Equipment;
import com.example.projektsale.entity.ProjectorEquipment;
import org.springframework.stereotype.Component;

@Component
public class EquipmentFactory {

    public static Equipment createComputer(String name, String os, String processor, Integer ram) {
        ComputerEquipment computer = new ComputerEquipment();
        computer.setName(name);
        computer.setOperatingSystem(os);
        computer.setProcessor(processor);
        computer.setRamGb(ram);
        computer.setIsWorking(true);
        return computer;
    }

    public static Equipment createProjector(String name, String resolution, Integer brightness) {
        ProjectorEquipment projector = new ProjectorEquipment();
        projector.setName(name);
        projector.setResolution(resolution);
        projector.setBrightness(brightness);
        projector.setIsWorking(true);
        return projector;
    }
}