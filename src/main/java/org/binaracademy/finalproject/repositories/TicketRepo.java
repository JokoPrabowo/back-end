package org.binaracademy.finalproject.repositories;

import org.binaracademy.finalproject.entity.TicketEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
public interface TicketRepo extends JpaRepository<TicketEntity, Long> {

    @Query(value = "SELECT order_id FROM orders WHERE schedule_id = :scheduleId AND email_user = :userEmail", nativeQuery = true)
    Long findOrderIdByScheduleIdAndUsername(Long scheduleId, String userEmail);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "INSERT INTO order_detail (seat_id, schedule_id, studio_id, order_id, username)\n" +
            "VALUES (:seatId, :scheduleId, :studioId, :orderId, :userId)", nativeQuery = true)
    void createOrderDetail(Long seatId, Long scheduleId, Long studioId, Long orderId, Long userId);
}
