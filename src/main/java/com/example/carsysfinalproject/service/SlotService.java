package com.example.carsysfinalproject.service;

import com.example.carsysfinalproject.model.core.dto.reservation.SlotDto;
import com.example.carsysfinalproject.model.core.entity.reservation.Parking;
import com.example.carsysfinalproject.model.core.entity.reservation.Slot;
import com.example.carsysfinalproject.model.dao.SlotDao;
import com.example.carsysfinalproject.model.repo.ParkingRepository;
import com.example.carsysfinalproject.model.repo.SlotRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.List;

import static java.util.Objects.nonNull;

@Service
public class SlotService {

    @Autowired
    private SlotDao slotDao;
    @Autowired
    private SlotRepository slotRepository;
    @Autowired
    private ParkingRepository parkingRepository;

    public List<SlotDto> getAllSlots() {
        return slotDao.getAllSlots();
    }

    public SlotDto getSlotById(int id) {
        return slotDao.getSlotById(id);
    }

    public int getSlotId(int parkingId, int slotNumber) {
        return slotDao.getSlotId(parkingId, slotNumber);
    }

    public void save(SlotDto slotDto) {
        slotDao.save(slotDto);
    }

    public void saveAll(int parkingId, int numberOfSlot) {
        for (int i = 1; i <= numberOfSlot; i++) {
            SlotDto slotDto = new SlotDto();
            slotDto.setId(i);
            slotDto.setSlotNumber(i);
            slotDto.setParkingId(parkingId);
            slotDao.save(slotDto);
        }
    }

    public void update(SlotDto slotDto) {
        slotDao.update(slotDto);
    }

    public void delete(int id) {
        Slot slot = slotRepository.findById(id).get();
        if (nonNull(slot)) {
            Parking parking = slot.getParking();
            if (nonNull(parking)) {
                if (parking.getNumberOfSlot() > 0) {
                    parking.setNumberOfSlot(parking.getNumberOfSlot() - 1);
                }
                parkingRepository.save(parking);
            }
        }
        slotDao.delete(id);
    }

    public void deleteAllSlots() {
        slotDao.deleteAllSlots();
    }
}