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
    @Query(value = "INSERT INTO ticket (status, schedule_id, seat_id, guest_id, order_id)\n" +
                                    "VALUES (:status, :scheduleId, :seatId, :guestId, :orderId)", nativeQuery = true)
    TicketEntity createOrderDetail(Boolean status, Long scheduleId, Long seatId, Long guestId, Long orderId);

}
