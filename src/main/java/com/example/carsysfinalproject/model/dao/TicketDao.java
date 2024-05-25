package com.example.carsysfinalproject.model.dao;

import com.example.carsysfinalproject.model.core.dto.reservation.TicketDto;
import com.example.carsysfinalproject.model.core.entity.reservation.*;
import com.example.carsysfinalproject.model.repo.ParkingRepository;
import com.example.carsysfinalproject.model.repo.SlotRepository;
import com.example.carsysfinalproject.model.repo.TicketRepository;
import com.example.carsysfinalproject.model.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Objects.nonNull;

@Component
public class TicketDao {

    @Autowired
    private TicketRepository ticketRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ParkingRepository parkingRepository;
    @Autowired
    private SlotRepository slotRepository;

    public List<TicketDto> getAllTickets() {
        return ticketRepository.findAll()
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public TicketDto findTicketById(int ticketId) {
        Ticket ticket = ticketRepository.findByTicketId(ticketId);
        return nonNull(ticket) ? toDto(ticket) : null;
    }

    public TicketDto getTicketByParkingIdAndTime(int parkingId, LocalTime time) {
        Ticket ticket = ticketRepository.findTicketByParkingIdAndTime(parkingId, time);
        return nonNull(ticket) ? toDto(ticket) : null;
    }

    public int getBookedSlotsNumber(int parkingId, LocalTime time) {
        return slotRepository.getBookedSlotsNumber(parkingId, time);
    }

    public Ticket findOverlappingTicket(LocalTime fromTime, int userId) {
        return ticketRepository.findOverlappingTicket(fromTime, userId);
    }

    public List<TicketDto> findTicketByCarPlateNumber(String carPlateNumber) {
        return ticketRepository.findByCarPlateNumber(carPlateNumber)
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public List<TicketDto> findTicketsByParkingId(int parkingId) {
        return ticketRepository.findTicketsByParkingId(parkingId)
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public List<Ticket> getTicketsByParkingId(int id) {
        return ticketRepository.findTicketsByParkingId(id);
    }

    public List<TicketDto> getTicketsSortedByDate() {
        return ticketRepository.findAllSortedByDate()
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public List<TicketDto> getTicketsSortedByStatus() {
        return ticketRepository.findAllSortedByStatus()
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public List<TicketDto> getTicketsSortedByParkingName() {
        return ticketRepository.findAllSortedByParkingName()
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public List<TicketDto> getTicketsSortedByStudentName() {
        return ticketRepository.findAllSortedByStudentName()
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public List<TicketDto> getTicketsByStudentName(String studentName) {
        return ticketRepository.findAllByStudentName(studentName)
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public List<TicketDto> getTicketByUserId(int userId) {
        return ticketRepository.getTicketByUserId(userId)
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public List<TicketDto> getActiveTickets() {
        return ticketRepository.findActiveTickets("ACTIVE")
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public List<TicketDto> getPendingTickets() {
        return ticketRepository.findPendingTickets("PENDING")
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public List<TicketDto> getFinishedTickets() {
        return ticketRepository.findFinishedTickets("FINISHED")
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public List<Integer> getBookedSlotNumbers(int parkingId, LocalTime time) {
        return slotRepository.getBookedSlotNumbers(parkingId, time);
    }

    public TicketDto save(TicketDto ticketDto) {
        Ticket ticket = toTicketEntity(ticketDto);
        ticketRepository.save(ticket);
        ticketDto.setTicketId(ticket.getTicketId());
        return ticketDto;
    }

    public void update(TicketDto ticketDto) {
        Ticket ticket = ticketRepository.findByTicketId(ticketDto.getTicketId());
        ticket.setFirstName(ticketDto.getFirstName());
        ticket.setLastName(ticketDto.getLastName());
        ticket.setPhoneNumber(ticketDto.getPhoneNumber());
        ticket.setParkingName(ticketDto.getParkingName());
        ticket.setDate(ticketDto.getDate());
        ticket.setFromTime(ticketDto.getFromTime());
        ticket.setToTime(ticketDto.getToTime());
        ticket.setAddedTime(ticketDto.getAddedTime());
        ticket.setSlotNumber(ticketDto.getSlotNumber());
        ticket.setTicketStatus(ticketDto.getTicketStatus());
        ticket.setCarModel(ticketDto.getCarModel());
        ticket.setCarColor(ticketDto.getCarColor());
        ticket.setCarPlateNumber(ticketDto.getCarPlateNumber());
        ticket.setUser(userRepository.findById(ticketDto.getUserId()).get());
        ticket.setParking(parkingRepository.findById(ticketDto.getParkingId()).get());
        ticket.setSlot(slotRepository.findById(ticketDto.getSlotId()).get());
        ticketRepository.save(ticket);
    }

    public void delete(int ticketId) {
        ticketRepository.deleteByTicketId(ticketId);
    }

    public void deleteAllTickets() {
        ticketRepository.deleteAll();
    }

    private Ticket toTicketEntity(TicketDto ticketDto) {
        Ticket ticket = new Ticket();
        ticket.setTicketId(ticketDto.getTicketId());
        ticket.setFirstName(ticketDto.getFirstName());
        ticket.setLastName(ticketDto.getLastName());
        ticket.setPhoneNumber(ticketDto.getPhoneNumber());
        ticket.setParkingName(ticketDto.getParkingName());
        ticket.setDate(ticketDto.getDate());
        ticket.setFromTime(ticketDto.getFromTime());
        ticket.setToTime(ticketDto.getToTime());
        ticket.setAddedTime(ticketDto.getAddedTime());
        ticket.setSlotNumber(ticketDto.getSlotNumber());
        ticket.setTicketStatus(ticketDto.getTicketStatus());
        ticket.setCarModel(ticketDto.getCarModel());
        ticket.setCarColor(ticketDto.getCarColor());
        ticket.setCarPlateNumber(ticketDto.getCarPlateNumber());
        ticket.setUser(userRepository.findById(ticketDto.getUserId()).get());
        ticket.setParking(parkingRepository.findById(ticketDto.getParkingId()).get());
        ticket.setSlot(slotRepository.findById(ticketDto.getSlotId()).get());
        return ticket;
    }

    private TicketDto toDto(Ticket ticket) {
        TicketDto ticketDto = new TicketDto();
        ticketDto.setTicketId(ticket.getTicketId());
        ticketDto.setFirstName(ticket.getFirstName());
        ticketDto.setLastName(ticket.getLastName());
        ticketDto.setPhoneNumber(ticket.getPhoneNumber());
        ticketDto.setParkingName(ticket.getParkingName());
        ticketDto.setDate(ticket.getDate());
        ticketDto.setFromTime(ticket.getFromTime());
        ticketDto.setToTime(ticket.getToTime());
        ticketDto.setAddedTime(ticket.getAddedTime());
        ticketDto.setSlotNumber(ticket.getSlotNumber());
        ticketDto.setTicketStatus(ticket.getTicketStatus());
        ticketDto.setCarModel(ticket.getCarModel());
        ticketDto.setCarColor(ticket.getCarColor());
        ticketDto.setCarPlateNumber(ticket.getCarPlateNumber());
        ticketDto.setUserId(ticket.getUser().getId());
        ticketDto.setParkingId(ticket.getParking().getId());
        ticketDto.setSlotId(ticket.getSlot().getId());
        return ticketDto;
    }
}