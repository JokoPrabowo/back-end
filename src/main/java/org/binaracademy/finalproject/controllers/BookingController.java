package org.binaracademy.finalproject.controllers;

import org.binaracademy.finalproject.dto.GuestRequest;
import org.binaracademy.finalproject.dto.ResponseData;
import org.binaracademy.finalproject.entity.ContactGuestEntity;
import org.binaracademy.finalproject.entity.GuestEntity;
import org.binaracademy.finalproject.services.impl.ContactGuestServiceImpl;
import org.binaracademy.finalproject.services.impl.GuestServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/booking")
public class BookingController {
    @Autowired
    private GuestServiceImpl guestService;
    @Autowired
    private ContactGuestServiceImpl contactGuestService;

    @PostMapping("/guest")
    public ResponseEntity<ResponseData> create(@RequestBody GuestRequest data){
        try{
            ResponseData res = new ResponseData();
            ContactGuestEntity contact = contactGuestService.create(new ContactGuestEntity(null, data.getFirstName(),
                    data.getLastName(), data.getNoTelp(), data.getEmail(), null, null));
            res.setSuccess(true);
            res.setStatusCode(200);
            res.setMessage("Successfully!");
            res.setData(guestService.create(new GuestEntity(null, data.getFirstName(), data.getLastName(), data.getBirthDate(),
                    data.getNationality(), data.getCountry(), data.getPassport(), data.getEndPassport(), data.getGoogleId(),
                    data.getUserId(), contact.getId(), null, null, null, null)));
            return ResponseEntity.ok(res);
        }catch (Exception e){
            ResponseData res = new ResponseData();
            res.setSuccess(false);
            res.setStatusCode(400);
            res.setMessage("Failed!");
            res.setData(null);
            return ResponseEntity.badRequest().body(res);
        }
    }
}
