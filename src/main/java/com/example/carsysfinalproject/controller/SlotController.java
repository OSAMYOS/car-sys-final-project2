package com.example.carsysfinalproject.controller;

import com.example.carsysfinalproject.model.core.dto.reservation.SlotDto;
import com.example.carsysfinalproject.service.SlotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalTime;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/slot")
public class SlotController {

    @Autowired
    private SlotService slotService;

    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('STUDENT')")
    @GetMapping("/all")
    public List<SlotDto> getAllSlots() {
        return slotService.getAllSlots();
    }

    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('STUDENT')")
    @PostMapping("/getSlotId")
    public int getSlotId(@RequestBody SlotRequest request) {
        int parkingId = request.getParkingId();
        int slotNumber = request.getSlotNumber();
        return slotService.getSlotId(parkingId, slotNumber);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/get")
    public SlotDto getSlotById(@RequestBody int id) {
        return slotService.getSlotById(id);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/save")
    public void save(@RequestBody SlotDto slotDto) {
        slotService.save(slotDto);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/saveAll")
    public void saveAll(@RequestBody SaveAllRequest request) {
        slotService.saveAll(request.getParkingId(), request.getNumberOfSlot());
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/update")
    public void update(SlotDto slotDto) {
        slotService.update(slotDto);
    }

    // no need
    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/delete")
    public void deleteSlot(@RequestBody int id) {
        if (id <= 0) {
            throw new IllegalArgumentException("Id cannot be less than or equal to 0");
        } else {
            slotService.delete(id);
        }
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/deleteAll")
    public void deleteAllSlots() {
        slotService.deleteAllSlots();
    }

}
