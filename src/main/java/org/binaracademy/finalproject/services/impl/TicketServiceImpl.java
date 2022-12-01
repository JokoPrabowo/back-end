package org.binaracademy.finalproject.services.impl;

import lombok.RequiredArgsConstructor;
import org.binaracademy.finalproject.repositories.TicketRepo;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TicketServiceImpl {

    private final TicketRepo ticketRepo;

}
