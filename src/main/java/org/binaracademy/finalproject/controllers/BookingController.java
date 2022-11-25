package org.binaracademy.finalproject.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.binaracademy.finalproject.dto.GuestRequest;
import org.binaracademy.finalproject.dto.ResponseData;
import org.binaracademy.finalproject.entity.ContactGuestEntity;
import org.binaracademy.finalproject.entity.GuestEntity;
import org.binaracademy.finalproject.services.impl.ContactGuestServiceImpl;
import org.binaracademy.finalproject.services.impl.GuestServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/booking")
public class BookingController {
    @Autowired
    private GuestServiceImpl guestService;
    @Autowired
    private ContactGuestServiceImpl contactGuestService;

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
}
