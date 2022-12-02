package org.binaracademy.finalproject.controllers;

import org.binaracademy.finalproject.dto.Request.GuestRequest;
import org.binaracademy.finalproject.dto.Request.OrderTicketRequest;
import org.binaracademy.finalproject.dto.ResponseData;
import org.binaracademy.finalproject.entity.ContactGuestEntity;
import org.binaracademy.finalproject.entity.GuestEntity;
import org.binaracademy.finalproject.entity.OrderEntity;
import org.binaracademy.finalproject.entity.TicketEntity;
import org.binaracademy.finalproject.services.ContactGuestService;
import org.binaracademy.finalproject.services.GuestService;
import org.binaracademy.finalproject.services.OrderService;
import org.binaracademy.finalproject.services.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/booking")
public class BookingController {
    @Autowired
    private GuestService guestService;
    @Autowired
    private ContactGuestService contactGuestService;

    @Autowired
    private TicketService ticketService;
    @Autowired
    private OrderService orderService;

    @PostMapping("/guest")
    public ResponseEntity<ResponseData<Object>> create(@Valid @RequestBody GuestRequest data, Errors errors){
        try{
            ResponseData<Object> res = new ResponseData();
            if(errors.hasErrors()){
                res.setSuccess(false);
                res.setStatusCode(HttpStatus.BAD_REQUEST.value());
                res.setMessage("Failed!");
                res.setData(null);
                return ResponseEntity.badRequest().body(res);
            }
            ContactGuestEntity contact = contactGuestService.create(new ContactGuestEntity(null, data.getFirstName(),
                    data.getLastName(), data.getNoTelp(), data.getEmail(), null, null));
            res.setSuccess(true);
            res.setStatusCode(HttpStatus.CREATED.value());
            res.setMessage("Successfully!");
            res.setData(guestService.create(new GuestEntity(null, data.getFirstName(), data.getLastName(), data.getBirthDate(),
                    data.getNationality(), data.getCountry(), data.getPassport(), data.getEndPassport(), data.getGoogleId(),
                    data.getUserId(), contact.getId(), null, null, null, null)));
            return ResponseEntity.ok(res);
        }catch (Exception e){
            ResponseData res = new ResponseData();
            res.setSuccess(false);
            res.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            res.setMessage("Failed!");
            res.setData(null);
            return ResponseEntity.internalServerError().body(res);
        }
    }

    @PostMapping("/add")
    public ResponseEntity<ResponseData<List<TicketEntity>>> create(@Valid @RequestBody OrderTicketRequest orderTicketRequest, Errors errors){
        ResponseData<List<TicketEntity>> response = new ResponseData<>();
        try {
            if(errors.hasErrors()){
                response.setData(null);
                response.setSuccess(false);
                response.setStatusCode(HttpStatus.BAD_REQUEST.value());
                response.setMessage("Failed!");
                return ResponseEntity.badRequest().body(response);
            }
            orderService.create(orderTicketRequest);
            response.setData(ticketService.create(orderTicketRequest));
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
