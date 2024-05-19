package com.example.carsysfinalproject.model.core.dto.reservation;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ParkingDto {
    private int id;
    private String name;
    private String address;
    private int numberOfSlot;
    private int numberOfAvailableSlot;

}
