package com.example.carsysfinalproject.model.repo;

import com.example.carsysfinalproject.model.core.entity.reservation.Parking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface ParkingRepository extends JpaRepository<Parking, Integer> {
    Parking findByName(String name);

    @Modifying
    @Transactional
    @Query("delete from Parking p where p.id = :id")
    void deleteByParkingId(int id);
}