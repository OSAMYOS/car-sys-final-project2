package com.example.carsysfinalproject.model.repo;

import com.example.carsysfinalproject.model.core.entity.reservation.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalTime;
import java.util.Collection;
import java.util.List;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Integer> {
    List<Ticket> findByCarPlateNumber(String carPlateNumber);

    @Modifying
    @Transactional
    @Query("DELETE FROM Ticket t WHERE t.ticketId = :ticketId")
    void deleteByTicketId(int ticketId);


    Ticket findByTicketId(int ticketId);

    List<Ticket> findTicketsByParkingId(int parkingId);

    @Query("SELECT t FROM Ticket t ORDER BY CONCAT(t.date, ' ', t.fromTime) DESC")
    List<Ticket> findAllSortedByDate();

    @Query("SELECT t FROM Ticket t ORDER BY t.ticketStatus DESC")
    List<Ticket> findAllSortedByStatus();

    @Query("SELECT t FROM Ticket t ORDER BY t.parkingName ASC")
    List<Ticket> findAllSortedByParkingName();

    @Query("SELECT t FROM Ticket t ORDER BY t.firstName ASC")
    List<Ticket> findAllSortedByStudentName();

    @Query("SELECT t FROM Ticket t WHERE t.firstName = :studentName")
    List<Ticket> findAllByStudentName(String studentName);

    @Query("SELECT t FROM Ticket t WHERE t.parking.id = :parkingId AND t.fromTime = :time")
    Ticket findTicketByParkingIdAndTime(int parkingId, LocalTime time);

    @Query("SELECT t FROM Ticket t WHERE t.user.id = :userId order by CONCAT(t.date, ' ', t.fromTime) DESC")
    List<Ticket> getTicketByUserId(int userId);

    @Query("SELECT t FROM Ticket t WHERE t.ticketStatus = :active")
    List<Ticket> findActiveTickets(String active);

    @Query("SELECT t FROM Ticket t WHERE t.ticketStatus = :pending")
    List<Ticket> findPendingTickets(String pending);

    @Query("SELECT t FROM Ticket t WHERE t.ticketStatus = :finished")
    List<Ticket> findFinishedTickets(String finished);

    @Query("SELECT t FROM Ticket t WHERE t.ticketStatus = :abandoned")
    List<Ticket> findAbandonedTickets(String abandoned);

    @Query("SELECT t FROM Ticket t WHERE t.fromTime = :fromTime AND t.user.id = :userId")
    Ticket findOverlappingTicket(LocalTime fromTime, int userId);

    @Query("SELECT DISTINCT t.user.id FROM Ticket t WHERE t.ticketStatus = 'Active'")
    List<Integer> findAllUserIdsWithActiveTickets();

    @Query("SELECT t FROM Ticket t WHERE t.user.id = :userId AND t.ticketStatus = :active")
    List<Ticket> findByUserIdAndTicketStatus(int userId, String active);

    @Query("SELECT t FROM Ticket t WHERE t.user.id = :userId AND t.fromTime BETWEEN :now AND :nextHour")
    List<Ticket> findNextHourTicketsForUser(int userId, LocalTime now, LocalTime nextHour);

    @Query("SELECT t FROM Ticket t WHERE t.ticketStatus = :pending AND t.user.id = :userId")
    List<Ticket> findNextHourPendingTicketsForUser(@Param("pending") String status, @Param("userId") int userId);

}
