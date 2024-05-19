package com.example.carsysfinalproject.model.core.dto.reservation;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SlotDto {
    private int id;
    private int slotNumber;
    private int parkingId;

}
