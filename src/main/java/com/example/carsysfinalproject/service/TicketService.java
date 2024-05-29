package com.example.carsysfinalproject.service;

import com.example.carsysfinalproject.model.core.dto.reservation.TicketDto;
import com.example.carsysfinalproject.model.core.entity.reservation.Parking;
import com.example.carsysfinalproject.model.core.entity.reservation.Slot;
import com.example.carsysfinalproject.model.core.entity.reservation.Ticket;
import com.example.carsysfinalproject.model.dao.TicketDao;
import com.example.carsysfinalproject.model.repo.ParkingRepository;
import com.example.carsysfinalproject.model.repo.SlotRepository;
import com.example.carsysfinalproject.model.repo.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static java.util.Objects.nonNull;

@Service
public class TicketService {

    @Autowired
    private TicketDao ticketDao;
    @Autowired
    private TicketRepository ticketRepository;
    @Autowired
    private ParkingRepository parkingRepository;
    @Autowired
    private SlotRepository slotRepository;

    public List<TicketDto> getAllTickets() {
        return ticketDao.getAllTickets();
    }

    public List<TicketDto> getTicketsSortedByDate() {
        return ticketDao.getTicketsSortedByDate();
    }

    public List<TicketDto> getTicketsSortedByStatus() {
        return ticketDao.getTicketsSortedByStatus();
    }

    public List<TicketDto> getTicketsSortedByParkingName() {
        return ticketDao.getTicketsSortedByParkingName();
    }

    public List<TicketDto> getTicketsSortedByStudentName() {
        return ticketDao.getTicketsSortedByStudentName();
    }

    public List<TicketDto> getTicketsByStudentName(String studentName) {
        return ticketDao.getTicketsByStudentName(studentName);
    }

    public TicketDto findTicketById(int ticketId) {
        return ticketDao.findTicketById(ticketId);
    }

    public List<TicketDto> findTicketByCarPlateNumber(String carPlateNumber) {
        return ticketDao.findTicketByCarPlateNumber(carPlateNumber);
    }

    public List<TicketDto> findTicketsByParkingId(int parkingId) {
        return ticketDao.findTicketsByParkingId(parkingId);
    }

    public TicketDto getTicketByParkingIdAndTime(int parkingId, LocalTime time) {
        return ticketDao.getTicketByParkingIdAndTime(parkingId, time);
    }

    public int getBookedSlotsNumber(int parkingId, LocalTime time) {
        return ticketDao.getBookedSlotsNumber(parkingId, time);
    }

    public List<TicketDto> getTicketByUserId(int userId) {
        return ticketDao.getTicketByUserId(userId);
    }

    public List<TicketDto> getActiveTickets() {
        return ticketDao.getActiveTickets();
    }

    public List<TicketDto> getPendingTickets() {
        return ticketDao.getPendingTickets();
    }

    public List<TicketDto> getAbandonedTickets() {
        return ticketDao.getAbandonedTickets();
    }

    public List<TicketDto> getFinishedTickets() {
        return ticketDao.getFinishedTickets();
    }

    public List<Integer> getBookedSlotNumbers(int parkingId, LocalTime time) {
        return ticketDao.getBookedSlotNumbers(parkingId, time);
    }

    public TicketDto save(TicketDto ticketDto) {
        Slot slot = slotRepository.findById(ticketDto.getSlotId())
                .orElseThrow(() -> new IllegalArgumentException("Slot not found"));
        Parking parking = parkingRepository.findById(ticketDto.getParkingId())
                .orElseThrow(() -> new IllegalArgumentException("Parking not found"));

        if (parking.getNumberOfAvailableSlot() <= 0) {
            throw new RuntimeException("Parking is full");
        }

        Ticket overlappingTicket = ticketDao.findOverlappingTicket(ticketDto.getFromTime(), ticketDto.getUserId());
        if (nonNull(overlappingTicket)) {
            throw new RuntimeException("You already have a ticket at this time");
        }

        ticketDto.setTicketStatus("Pending");
        ticketDto.setDate(LocalDate.now());
        ticketDto.setToTime(ticketDto.getFromTime().plusHours(1));
        ticketDto.setFromTime(LocalTime.of(ticketDto.getFromTime().getHour(), ticketDto.getFromTime().getMinute(), ticketDto.getFromTime().getSecond()));
        ticketDto.setToTime(LocalTime.of(ticketDto.getToTime().getHour(), ticketDto.getToTime().getMinute(), ticketDto.getToTime().getSecond()));

        slotRepository.save(slot);
        parking.setNumberOfAvailableSlot(parking.getNumberOfAvailableSlot() - 1);
        parkingRepository.save(parking);
        TicketDto savedTicket = ticketDao.save(ticketDto);
        int generatedTicketId = savedTicket.getTicketId();
        savedTicket.setTicketId(generatedTicketId);
        return savedTicket;
    }


    public void scanTicket(int ticketId) {
        Ticket ticket = ticketRepository.findByTicketId(ticketId);
        if (nonNull(ticket)) {
            if (ticket.getTicketStatus().equals("Pending")) {
                ticket.setTicketStatus("Active");
            }else if (ticket.getTicketStatus().equals("Active")) {
                if (LocalTime.now().isAfter(ticket.getToTime())) {
                    Duration addedTime = Duration.between(ticket.getToTime(), LocalTime.now());
                    //System.out.println("The user is overdue by " + formatDuration(addedTime));
                    ticket.setAddedTime(formatDuration(addedTime));
                }
                delete(ticketId);
            }else if (ticket.getTicketStatus().equals("Finished")) {
                throw new IllegalArgumentException("Ticket not found");
            }else if (ticket.getTicketStatus().equals("Abandoned")) {
                throw new IllegalArgumentException("Ticket not found");
            }
            ticketRepository.save(ticket);
        }
    }

    public void update(TicketDto ticketDto) {
        ticketDao.update(ticketDto);
    }

    public void cancelTicket(int ticketId) {
        Ticket ticket = ticketRepository.findByTicketId(ticketId);
        Slot slot = ticket.getSlot();
        Parking parking = slot.getParking();
        parking.setNumberOfAvailableSlot(parking.getNumberOfAvailableSlot() + 1);
        ticketDao.delete(ticketId);
    }

    public void delete(int ticketId) {
        Ticket ticket = ticketRepository.findByTicketId(ticketId);

        if (nonNull(ticket)) {
            ticket.setTicketStatus("Finished");

            Slot slot = ticket.getSlot();
            if (nonNull(slot)) {

                Parking parking = slot.getParking();
                if (nonNull(parking)) {

                    parking.setNumberOfAvailableSlot(parking.getNumberOfAvailableSlot() + 1);

                    slotRepository.save(slot);
                    parkingRepository.save(parking);
                }
            }
        }
        ticketRepository.save(ticket);
    }

    public void deleteAllTickets() {
        ticketDao.deleteAllTickets();
    }

    private LocalTime formatDuration(Duration duration) {
        long hours = duration.toHours();
        long minutes = duration.toMinutes() % 60;
        long seconds = duration.toSeconds() % 60;
        return LocalTime.of((int) hours, (int) minutes, (int) seconds);
    }
}
