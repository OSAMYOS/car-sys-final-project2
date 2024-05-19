package com.example.carsysfinalproject.controller;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TickerRequest {
    public int parkingId;
    public LocalTime time;
}
