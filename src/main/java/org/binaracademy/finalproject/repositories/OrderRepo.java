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

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "INSERT INTO orders (status, total_price, tax, total_pay, expired_at, email_user, schedule_id, create_at)\n" +
                            "VALUES (:status, :totalPrice, :tax, :totalPay, :expiredAt, :userEmail, :scheduleId, :createAt)", nativeQuery = true)
    OrderEntity createOrder(Boolean status, BigDecimal totalPrice, BigDecimal tax, BigDecimal totalPay,
                     LocalDateTime expiredAt, String userEmail, Long scheduleId, LocalDateTime createAt);

}
