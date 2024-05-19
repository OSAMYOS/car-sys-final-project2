package com.example.carsysfinalproject.controller;

import com.example.carsysfinalproject.model.core.dto.reservation.TicketDto;
import com.example.carsysfinalproject.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalTime;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/ticket")
public class TicketController {

    @Autowired
    private TicketService ticketService;

    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('GUARD')")
    @GetMapping("/all")
    public List<TicketDto> getAllTickets() {
        return ticketService.getAllTickets();
    }

    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('GUARD') or hasAuthority('STUDENT')")
    @PostMapping("/getById/{ticketId}")
    public TicketDto getTicketById(@PathVariable("ticketId") int ticketId) {
        return ticketService.findTicketById(ticketId);
    }

    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('GUARD') or hasAuthority('STUDENT')")
    @PostMapping("/getByUserId/{userId}")
    public List<TicketDto> getTicketByUserId(@PathVariable("userId") int userId) {
        return ticketService.getTicketByUserId(userId);
    }

    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('GUARD')")
    @PostMapping("/getByCarPlateNumber")
    public List<TicketDto> getTicketByCarPlateNumber(@RequestBody String carPlateNumber) {
        return ticketService.findTicketByCarPlateNumber(carPlateNumber);
    }

    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('GUARD')")
    @PostMapping("/getByParkingId")
    public List<TicketDto> getTicketsByCarPlateNumber(@RequestBody int parkingId) {
        return ticketService.findTicketsByParkingId(parkingId);
    }

    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('GUARD')")
    @GetMapping("/getByDate")
    public List<TicketDto> getTicketsSortedByDate() {
        return ticketService.getTicketsSortedByDate();
    }

    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('GUARD')")
    @GetMapping("/getByStatus")
    public List<TicketDto> getTicketsSortedByStatus() {
        return ticketService.getTicketsSortedByStatus();
    }

    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('GUARD')")
    @GetMapping("/getActiveTickets")
    public List<TicketDto> getActiveTickets() {
        return ticketService.getActiveTickets();
    }

    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('GUARD')")
    @GetMapping("/getPendingTickets")
    public List<TicketDto> getPendingTickets() {
        return ticketService.getPendingTickets();
    }

    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('GUARD')")
    @GetMapping("/getFinishedTickets")
    public List<TicketDto> getFinishedTickets() {
        return ticketService.getFinishedTickets();
    }

    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('GUARD')")
    @GetMapping("/getByParkingName")
    public List<TicketDto> getTicketsSortedByParkingName() {
        return ticketService.getTicketsSortedByParkingName();
    }

    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('GUARD')")
    @GetMapping("/getByStudentName")
    public List<TicketDto> getTicketsSortedByStudentName() {
        return ticketService.getTicketsSortedByStudentName();
    }

    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('GUARD')")
    @PostMapping("/getByStudentFirstName")
    public List<TicketDto> getTicketsByStudentName(@RequestBody String studentName) {
        return ticketService.getTicketsByStudentName(studentName);
    }

    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('GUARD')")
    @PostMapping("/getByParkingIdAndTime")
    public TicketDto getTicketByParkingIdAndTime(@RequestBody TickerRequest request) {
        int parkingId = request.getParkingId();
        LocalTime time = request.getTime();
        return ticketService.getTicketByParkingIdAndTime(parkingId, time);
    }

    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('GUARD') or hasAuthority('STUDENT')")
    @PostMapping("/getBookedSlotsNumber")
    public int getBookedSlotsNumber(@RequestBody TickerRequest request) {
        int parkingId = request.getParkingId();
        LocalTime time = LocalTime.now();
        int hour = time.getHour();
        time = LocalTime.of(hour, 0);
        return ticketService.getBookedSlotsNumber(parkingId, time);
    }

    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('GUARD') or hasAuthority('STUDENT')")
    @PostMapping("/getBookedSlotNumbers")
    public List<Integer> getBookedSlotNumbers(@RequestBody TickerRequest request) {
        int parkingId = request.getParkingId();
        LocalTime time = request.getTime();
        return ticketService.getBookedSlotNumbers(parkingId, time);
    }

    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('STUDENT')")
    @PostMapping("/save")
    public TicketDto saveTicket(@RequestBody TicketDto ticketDto) {
        return ticketService.save(ticketDto);
    }

    @PreAuthorize("hasAuthority('GUARD') or hasAuthority('ADMIN')")
    @PostMapping("/scan/{ticketId}")
    public void scanTicket(@PathVariable int ticketId) {
        ticketService.scanTicket(ticketId);
    }

    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('GUARD')")
    @PostMapping("/update")
    public void updateTicket(@RequestBody TicketDto ticketDto) {
        ticketService.update(ticketDto);
    }

    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('STUDENT')")
    @PostMapping("/cancel/{ticketId}")
    public void cancelTicket(@PathVariable("ticketId") int ticketId) {
        ticketService.cancelTicket(ticketId);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/delete/{ticketId}")
    public void deleteTicket(@PathVariable("ticketId") int ticketId) {
        ticketService.delete(ticketId);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/deleteAll")
    public void deleteAllTickets() {
        ticketService.deleteAllTickets();
    }
}
