package com.example.projektsale.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "equipment")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "equipment_type", discriminatorType = DiscriminatorType.STRING)
@Data
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public abstract class Equipment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(name = "is_working")
    private Boolean isWorking = true;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id")
    @JsonIgnoreProperties({"reservations", "equipment"})
    private Room room;

    public abstract String getEquipmentType();
    public abstract void performMaintenance();
}