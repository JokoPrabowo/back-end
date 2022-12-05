package org.binaracademy.finalproject.controllers;

import lombok.RequiredArgsConstructor;
import org.binaracademy.finalproject.dto.Request.OrderTicketRequest;
import org.binaracademy.finalproject.dto.ResponseData;
import org.binaracademy.finalproject.entity.OrderEntity;
import org.binaracademy.finalproject.entity.TicketEntity;
import org.binaracademy.finalproject.services.OrderService;
import org.binaracademy.finalproject.services.TicketService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/ticket")
@RequiredArgsConstructor
public class TicketController {
    private final TicketService ticketService;

    @PutMapping("/update")
    public ResponseEntity<ResponseData<List<TicketEntity>>> update(@Valid @RequestBody OrderTicketRequest orderTicketRequest, Errors errors){
        ResponseData<List<TicketEntity>> response = new ResponseData<>();
        try {
            if(errors.hasErrors()){
                response.setData(null);
                response.setSuccess(false);
                response.setStatusCode(HttpStatus.BAD_REQUEST.value());
                response.setMessage("Failed!");
                return ResponseEntity.badRequest().body(response);
            }
            response.setData(ticketService.update(orderTicketRequest));
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

    @GetMapping("/get/{guestId}")
    public ResponseEntity<ResponseData<TicketEntity>> findByGuestId(@PathVariable Long guestId){
        ResponseData<TicketEntity> response = new ResponseData<>();
        try {
            response.setData(ticketService.findByGuestId(guestId));
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
