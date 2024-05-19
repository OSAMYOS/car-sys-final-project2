package com.example.carsysfinalproject.model.core.entity.reservation;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
@Table(name = "Parking")
public class Parking {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "parking_name", unique = true)
    private String name;

    @Column(name = "address", length = 500)
    private String address;

    @Column(name = "number_of_slots")
    private int numberOfSlot;

    @Column(name = "number_of_available_slots")
    private int numberOfAvailableSlot;

    @OneToMany(mappedBy = "parking", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Slot> slots;

    @OneToMany(mappedBy = "parking", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Ticket> tickets;

    public Parking(int parkingId) {
        this.id = parkingId;
    }

    public Parking() {
    }

}
