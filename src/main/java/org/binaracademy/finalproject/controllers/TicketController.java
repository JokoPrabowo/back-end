package org.binaracademy.finalproject.controllers;

import lombok.RequiredArgsConstructor;
import org.binaracademy.finalproject.dto.Request.OrderTicketRequest;
import org.binaracademy.finalproject.dto.ResponseData;
import org.binaracademy.finalproject.entity.OrderEntity;
import org.binaracademy.finalproject.services.OrderService;
import org.binaracademy.finalproject.services.TicketService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/ticket")
@RequiredArgsConstructor
public class TicketController {

    private final OrderService orderService;
    private final TicketService ticketService;

    public ResponseEntity<ResponseData<OrderEntity>> create(@RequestBody OrderTicketRequest orderTicketRequest){

        ResponseData<OrderEntity> response = new ResponseData<>();

        try {
            response.setData(null);
            response.setSuccess(true);
            response.setStatusCode(HttpStatus.OK.value());
            response.setMessage("Successfully!");
            return ResponseEntity.ok(response);
        }catch (Exception e){
            response.setData(null);
            response.setSuccess(false);
            response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setMessage(e.getMessage());
            return ResponseEntity.internalServerError().body(response);
        }
    }

}
