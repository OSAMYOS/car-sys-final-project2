package com.example.carsysfinalproject.controller;

import com.example.carsysfinalproject.model.core.dto.reservation.ParkingDto;
import com.example.carsysfinalproject.service.ParkingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/parking")
public class ParkingController {

    @Autowired
    private ParkingService parkingService;

    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('GUARD') or hasAuthority('STUDENT')")
    @GetMapping("/all")
    public List<ParkingDto> getAllParking() {
        return parkingService.getAllParking();
    }

    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('GUARD')")
    @PostMapping("/getById")
    public ParkingDto getParking(@RequestBody int parkingId) {
        return parkingService.findParkingById(parkingId);
    }

    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('GUARD')")
    @PostMapping("/getByName")
    public ParkingDto getParking(@RequestBody String parkingName) {
        return parkingService.findParkingByName(parkingName);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/save")
    public void saveParking(@RequestBody ParkingDto parking) {
        parkingService.save(parking);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/update")
    public void updateParking(@RequestBody ParkingDto parking) {
        parkingService.update(parking);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/delete/{parkingId}")
    public void deleteParking(@PathVariable int parkingId) {
        parkingService.delete(parkingId);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/deleteAll")
    public void deleteAllParking() {
        parkingService.deleteAllParking();
    }

}
