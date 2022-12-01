package org.binaracademy.finalproject.services.impl;

import lombok.RequiredArgsConstructor;
import org.binaracademy.finalproject.repositories.OrderRepo;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl {

    private final OrderRepo orderRepo;

}
