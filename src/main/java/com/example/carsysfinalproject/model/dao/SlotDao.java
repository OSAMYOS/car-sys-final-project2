package com.example.carsysfinalproject.model.dao;

import com.example.carsysfinalproject.model.core.dto.reservation.SlotDto;
import com.example.carsysfinalproject.model.core.entity.reservation.Parking;
import com.example.carsysfinalproject.model.core.entity.reservation.Slot;
import com.example.carsysfinalproject.model.repo.ParkingRepository;
import com.example.carsysfinalproject.model.repo.SlotRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.util.Objects.nonNull;

@Component
public class SlotDao {

    @Autowired
    private SlotRepository slotRepository;

    @Autowired
    private ParkingRepository parkingRepository;

    public List<SlotDto> getAllSlots() {
        return slotRepository.findAll()
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public SlotDto getSlotById(int id) {
        Slot slot = slotRepository.findById(id).get();
        return nonNull(slot) ? toDto(slot) : null;
    }

    public List<Slot> getSlotsByParkingId(int id) {
        return slotRepository.findByParkingId(id);
    }

    public void save(SlotDto slotDto) {
        Slot slot = toSlotEntity(slotDto);
        assert slot != null;
        slotRepository.save(slot);
    }

    public void update(SlotDto slotDto) {
        Slot slot = slotRepository.findById(slotDto.getId()).get();
        slot.setSlotNumber(slotDto.getSlotNumber());
        slot.setParking(parkingRepository.findById(slotDto.getParkingId()).get());
        slotRepository.save(slot);
    }


    public void delete(int id) {
        slotRepository.deleteBySlotId(id);
    }

    public void deleteAllSlots() {
        slotRepository.deleteAll();
    }

    private Slot toSlotEntity(SlotDto slotDto) {
        Optional<Parking> parking = parkingRepository.findById(slotDto.getParkingId());
        if(parking.isEmpty()) {
            throw new IllegalArgumentException("parking not found");
        }
        Slot slot = new Slot();
        slot.setSlotNumber(slotDto.getSlotNumber());
        slot.setParking(parking.get());
        return slot;
    }

    private SlotDto toDto(Slot slot) {
        SlotDto slotDto = new SlotDto();
        slotDto.setSlotNumber(slot.getSlotNumber());
        slotDto.setParkingId(slot.getParking().getId());
        return slotDto;
    }

    public int getSlotId(int parkingId, int slotNumber) {
        return slotRepository.getSlotId(parkingId, slotNumber);
    }
}
