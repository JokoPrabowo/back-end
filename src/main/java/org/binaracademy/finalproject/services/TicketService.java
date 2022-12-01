package org.binaracademy.finalproject.services;

import org.binaracademy.finalproject.dto.Request.OrderTicketRequest;
import org.binaracademy.finalproject.entity.TicketEntity;

import java.util.List;

public interface TicketService {

    List<TicketEntity> create(OrderTicketRequest orderTicketRequest);

}
