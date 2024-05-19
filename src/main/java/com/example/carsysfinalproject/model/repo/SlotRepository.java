package com.example.carsysfinalproject.model.repo;

import com.example.carsysfinalproject.model.core.entity.reservation.Slot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalTime;
import java.util.List;

@Repository
public interface SlotRepository extends JpaRepository<Slot, Integer> {

    @Modifying
    @Transactional
    @Query("delete from Slot s where s.id = :id")
    void deleteBySlotId(int id);

    @Query("select s from Slot s where s.parking.id = :id")
    List<Slot> findByParkingId(int id);

    @Query("SELECT s.id FROM Slot s WHERE s.parking.id = :parkingId AND s.slotNumber = :slotNumber")
    int getSlotId(int parkingId, int slotNumber);

    @Query("SELECT COUNT(t.ticketId) FROM Ticket t WHERE t.parking.id = :parkingId AND t.fromTime = :time")
    int getBookedSlotsNumber(int parkingId, LocalTime time);

    @Query("SELECT t.slot.slotNumber FROM Ticket t WHERE t.parking.id = :parkingId AND t.fromTime = :time AND t.date = CURRENT_DATE() AND t.ticketStatus IN ('Pending', 'Active')")
    List<Integer> getBookedSlotNumbers(int parkingId, LocalTime time);

}
