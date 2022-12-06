package org.binaracademy.finalproject.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.binaracademy.finalproject.dto.Request.GuestRequest;
import org.binaracademy.finalproject.dto.Request.OrderTicketRequest;
import org.binaracademy.finalproject.dto.ResponseData;
import org.binaracademy.finalproject.entity.ContactGuestEntity;
import org.binaracademy.finalproject.entity.GuestEntity;
import org.binaracademy.finalproject.entity.TicketEntity;
import org.binaracademy.finalproject.services.ContactGuestService;
import org.binaracademy.finalproject.services.GuestService;
import org.binaracademy.finalproject.services.OrderService;
import org.binaracademy.finalproject.services.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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
@Tag(name = "Booking", description = "Operation about Booking")
public class BookingController {
    @Autowired
    private GuestService guestService;
    @Autowired
    private ContactGuestService contactGuestService;

    @Autowired
    private TicketService ticketService;
    @Autowired
    private OrderService orderService;

    @Operation(summary = "Add guest")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "sukses", content = @Content(examples = {
                    @ExampleObject(name = "Create guest",
                            description = "Menampilkan balikan dari create guest",
                            value = "{\n" +
                                    "    \"success\": true,\n" +
                                    "    \"statusCode\": 201,\n" +
                                    "    \"message\": \"Successfully!\",\n" +
                                    "    \"data\": {\n" +
                                    "        \"id\": 1,\n" +
                                    "        \"firstName\": \"Adinda\",\n" +
                                    "        \"lastName\": \"Anzani\",\n" +
                                    "        \"birthDate\": \"1998-04-06\",\n" +
                                    "        \"nationality\": \"Indonesia\",\n" +
                                    "        \"country\": \"Indonesia\",\n" +
                                    "        \"passport\": \"A8989888\",\n" +
                                    "        \"endPassport\": \"2025-12-06\",\n" +
                                    "        \"googleId\": \"1i1iu1i1iu\",\n" +
                                    "        \"userId\": 1,\n" +
                                    "        \"contactId\": 1,\n" +
                                    "        \"user\": null,\n" +
                                    "        \"contact\": null,\n" +
                                    "        \"createAt\": \"2022-12-06T10:51:44.1881502\",\n" +
                                    "        \"updateAt\": null\n" +
                                    "    }\n" +
                                    "}")
            }, mediaType = MediaType.APPLICATION_JSON_VALUE)),
            @ApiResponse(responseCode = "400", content = @Content(examples = {
                    @ExampleObject(name = "Request Error",
                            description = "Tampilan jika request error",
                            value = "{\n"
                                    + "    \"success\": false,\n"
                                    + "    \"statusCode\": 400,\n"
                                    + "    \"message\": \"Request Error Message\",\n"
                                    + "    \"data\": []\n"
                                    + "}")
            }, mediaType = MediaType.APPLICATION_JSON_VALUE)),
            @ApiResponse(responseCode = "500", content = @Content(examples = {
                    @ExampleObject(name = "Server Error",
                            description = "Tampilan jika server error",
                            value = "{\n"
                                    + "    \"success\": false,\n"
                                    + "    \"statusCode\": 500,\n"
                                    + "    \"message\": \"Server Error Message\",\n"
                                    + "    \"data\": []\n"
                                    + "}")
            }, mediaType = MediaType.APPLICATION_JSON_VALUE))
    })
    @PostMapping("/guest")
    public ResponseEntity<ResponseData<Object>> create(@Valid @RequestBody GuestRequest data, Errors errors){
        ResponseData<Object> res = new ResponseData();
        try{
            if(errors.hasErrors()){
                res.setSuccess(false);
                res.setStatusCode(HttpStatus.BAD_REQUEST.value());
                res.setMessage("Failed!");
                res.setData(null);
                return ResponseEntity.badRequest().body(res);
            }
            ContactGuestEntity contact = contactGuestService.create(new ContactGuestEntity(null, data.getGuestFirstname(),
                    data.getGuestLastname(), data.getNoTelp(), data.getEmail(), null, null));
            res.setSuccess(true);
            res.setStatusCode(HttpStatus.CREATED.value());
            res.setMessage("Successfully!");
            res.setData(guestService.create(new GuestEntity(null, data.getFirstName(), data.getLastName(), data.getBirthDate(),
                    data.getNationality(), data.getCountry(), data.getPassport(), data.getEndPassport(), data.getGoogleId(),
                    data.getUserId(), contact.getId(), null, null, null, null)));
            return ResponseEntity.ok(res);
        }catch (Exception e){
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
