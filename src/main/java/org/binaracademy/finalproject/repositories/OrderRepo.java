package org.binaracademy.finalproject.repositories;

import org.binaracademy.finalproject.entity.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Repository
public interface OrderRepo extends JpaRepository<OrderEntity, Long> {

    @Query(value = "SELECT order_id FROM orders WHERE schedule_id = :scheduleId AND email_user = :userEmail", nativeQuery = true)
    Long findOrderIdByScheduleIdAndUsername(Long scheduleId, String userEmail);

    @Query(value = "SELECT price FROM schedules WHERE schedule_id = :scheduleId", nativeQuery = true)
    BigDecimal findPriceSchedule(Long scheduleId);

}
