package com.example.carsysfinalproject.model.dao;

import com.example.carsysfinalproject.model.core.dto.reservation.ParkingDto;
import com.example.carsysfinalproject.model.core.entity.reservation.Parking;
import com.example.carsysfinalproject.model.repo.ParkingRepository;
import com.example.carsysfinalproject.service.SlotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

import static java.util.Objects.nonNull;

@Component
public class ParkingDao {

    @Autowired
    private ParkingRepository parkingRepository;
    @Autowired
    private SlotService slotService;

    public List<ParkingDto> getAllParking() {
        return parkingRepository.findAll()
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public ParkingDto findParkingByName(String name) {
        Parking parking = parkingRepository.findByName(name);
        return nonNull(parking) ? toDto(parking) : null;
    }

    public ParkingDto findParkingById(int id) {
        Parking parking = parkingRepository.findById(id).get();
        return nonNull(parking) ? toDto(parking) : null;
    }

    public void save(ParkingDto parkingDto) {
        if (parkingDto != null) {
            Parking parking = toParkingEntity(parkingDto);
            parkingRepository.save(parking);

            if (parking != null) {
                slotService.saveAll(parking.getId(), parking.getNumberOfSlot());
            } else {
                System.out.println("parking is null");
            }
        } else {
            System.out.println("parkingDto is null");
        }
    }

    public void update(ParkingDto parkingDto) {
        Parking parking = parkingRepository.findById(parkingDto.getId()).get();
        parking.setName(parkingDto.getName());
        parking.setAddress(parkingDto.getAddress());
        parking.setNumberOfSlot(parkingDto.getNumberOfSlot());
        parking.setNumberOfAvailableSlot(parkingDto.getNumberOfAvailableSlot());
        parkingRepository.save(parking);
    }

    public void delete(int id) {
        parkingRepository.deleteByParkingId(id);
    }

    public void deleteAllParking() {
        parkingRepository.deleteAll();
    }

    private Parking toParkingEntity(ParkingDto parkingDto) {
        Parking parking = new Parking();
        parking.setName(parkingDto.getName());
        parking.setAddress(parkingDto.getAddress());
        parking.setNumberOfSlot(parkingDto.getNumberOfSlot());
        parking.setNumberOfAvailableSlot(parkingDto.getNumberOfAvailableSlot());
        return parking;
    }

    private ParkingDto toDto(Parking parking) {
        ParkingDto parkingDto = new ParkingDto();
        parkingDto.setId(parking.getId());
        parkingDto.setName(parking.getName());
        parkingDto.setAddress(parking.getAddress());
        parkingDto.setNumberOfSlot(parking.getNumberOfSlot());
        parkingDto.setNumberOfAvailableSlot(parking.getNumberOfAvailableSlot());
        return parkingDto;
    }
}