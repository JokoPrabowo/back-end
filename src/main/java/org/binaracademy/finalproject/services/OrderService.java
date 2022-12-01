package org.binaracademy.finalproject.services;

import org.binaracademy.finalproject.dto.Request.OrderTicketRequest;
import org.binaracademy.finalproject.entity.OrderEntity;

public interface OrderService {

    OrderEntity create(OrderTicketRequest orderTicketRequest);

}
