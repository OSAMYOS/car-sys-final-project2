package com.example.carsysfinalproject.service;

import com.example.carsysfinalproject.model.core.dto.reservation.ParkingDto;
import com.example.carsysfinalproject.model.core.entity.reservation.Slot;
import com.example.carsysfinalproject.model.core.entity.reservation.Ticket;
import com.example.carsysfinalproject.model.dao.ParkingDao;
import com.example.carsysfinalproject.model.dao.SlotDao;
import com.example.carsysfinalproject.model.dao.TicketDao;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ParkingService {

    @Autowired
    private ParkingDao parkingDao;
    @Autowired
    private SlotDao slotDao;
    @Autowired
    private TicketDao ticketDao;
    @Autowired
    private SlotService slotService;

    public List<ParkingDto> getAllParking() {
        return parkingDao.getAllParking();
    }

    public ParkingDto findParkingById(int id) {
        return parkingDao.findParkingById(id);
    }

    public ParkingDto findParkingByName(String name) {
        return parkingDao.findParkingByName(name);
    }

    public void save(ParkingDto parkingDto) {
        parkingDto.setNumberOfAvailableSlot(parkingDto.getNumberOfSlot());
        parkingDao.save(parkingDto);
    }

    public void update(ParkingDto parkingDto) {
        parkingDao.update(parkingDto);
    }

    public void delete(int id) {
        List<Ticket> tickets = ticketDao.getTicketsByParkingId(id);
        for (Ticket ticket : tickets) {
            ticketDao.delete(ticket.getTicketId());
        }
        List<Slot> slots = slotDao.getSlotsByParkingId(id);
        for (Slot slot : slots) {
            slotDao.delete(slot.getId());
        }
        parkingDao.delete(id);
    }

    public void deleteAllParking() {
        parkingDao.deleteAllParking();
    }
}
