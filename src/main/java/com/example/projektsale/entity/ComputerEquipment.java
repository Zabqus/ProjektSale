package com.example.projektsale.entity;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@DiscriminatorValue("COMPUTER")
@Data
@EqualsAndHashCode(callSuper = true)
public class ComputerEquipment extends Equipment {

    private String operatingSystem;
    private String processor;
    private Integer ramGb;

    @Override
    public String getEquipmentType() {
        return "Computer";
    }

    @Override
    public void performMaintenance() {
        System.out.println("Running system check for " + getName());
    }
}