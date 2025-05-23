package com.example.projektsale.entity;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;
@Entity
@DiscriminatorValue("PROJECTOR")
@Data
@EqualsAndHashCode(callSuper = true)
public class ProjectorEquipment extends Equipment {

    private String resolution;
    private Integer brightness;

    @Override
    public String getEquipmentType() {
        return "Projector";
    }

    @Override
    public void performMaintenance() {
        System.out.println("Checking if all fine for: " + getName());
    }
}