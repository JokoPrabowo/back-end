package org.binaracademy.finalproject.services.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.binaracademy.finalproject.dto.Request.OrderTicketRequest;
import org.binaracademy.finalproject.entity.OrderEntity;
import org.binaracademy.finalproject.repositories.OrderRepo;
import org.binaracademy.finalproject.services.OrderService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepo orderRepo;
    private static final String ERROR_FOUND = "Error found : {}";

    @Override
    public OrderEntity create(OrderTicketRequest orderTicketRequest) {
        try {
            int quantity = orderTicketRequest.getSeatId().size();
            BigDecimal price = orderRepo.findPriceSchedule(orderTicketRequest.getScheduleId());
            BigDecimal totalPrice = price.multiply(BigDecimal.valueOf(quantity));
            BigDecimal tax = totalPrice.multiply(BigDecimal.valueOf(0.10));
            BigDecimal totalPay = totalPrice.add(tax);
            LocalDateTime expiredAt = LocalDateTime.now().plusHours(12L);
            OrderEntity order = orderRepo.createOrder(true, totalPrice, tax, totalPay,
                    expiredAt, orderTicketRequest.getUserEmail(),
                    orderTicketRequest.getScheduleId(), LocalDateTime.now());
            log.info("Order has been created : {}", orderTicketRequest.getUserEmail());
            return order;
        }catch (Exception e){
            log.error(ERROR_FOUND, e.getMessage());
            return null;
        }
    }
}