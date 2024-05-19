package com.example.carsysfinalproject.model.core.dto.reservation;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TicketDto {
    private int ticketId;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String parkingName;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;
    @JsonFormat(pattern = "HH:mm:ss")
    private LocalTime fromTime;
    @JsonFormat(pattern = "HH:mm:ss")
    private LocalTime toTime;
    private int slotNumber;
    private String ticketStatus;
    private String carModel;
    private String carColor;
    private String carPlateNumber;
    private int userId;
    private int parkingId;
    private int slotId;
    private int ticketCount;
}
