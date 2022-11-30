package org.binaracademy.finalproject.repositories;

import org.binaracademy.finalproject.entity.ScheduleEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ScheduleRepo extends JpaRepository<ScheduleEntity, Long> {

    @Query(value = "SELECT * FROM schedules WHERE departure_airport = :departureAiport AND arrival_airport = :arrivalAirport", nativeQuery = true)
    List<ScheduleEntity> findScheduleFromTo(String departureAiport, String arrivalAirport);

    @Query(value = "SELECT * FROM schedules WHERE departure_airport = :departureAiport AND arrival_airport = :arrivalAirport", nativeQuery = true)
    Page<ScheduleEntity> findScheduleFromToPage(String departureAiport, String arrivalAirport, Pageable pageable);

}
