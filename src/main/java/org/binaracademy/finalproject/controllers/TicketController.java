package org.binaracademy.finalproject.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.binaracademy.finalproject.dto.Request.OrderTicketRequest;
import org.binaracademy.finalproject.dto.ResponseData;
import org.binaracademy.finalproject.entity.TicketEntity;
import org.binaracademy.finalproject.services.TicketService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/ticket")
@RequiredArgsConstructor
@Tag(name = "Ticket", description = "Operation about Ticket")
public class TicketController {
    private final TicketService ticketService;

    @Operation(summary = "Update ticket (EndPoint digunakan untuk update ticket \"https://febe6.up.railway.app/api/ticket/update\")")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "sukses", content = @Content(examples = {
                    @ExampleObject(name = "Update ticket",
                            description = "EndPoint dapat digunakan setelah mendapatkan guestId yang didapat setelah melakukan order," +
                                    "ini hanya untuk update seat penumpang, jika berhasil akan menampilkan data seperti diatas",
                            value = "{\n" +
                                    "    \"success\": true,\n" +
                                    "    \"statusCode\": 200,\n" +
                                    "    \"message\": \"Successfully!\",\n" +
                                    "    \"data\": [\n" +
                                    "        {\n" +
                                    "            \"id\": 1,\n" +
                                    "            \"status\": true,\n" +
                                    "            \"scheduleId\": 1,\n" +
                                    "            \"seatId\": 3,\n" +
                                    "            \"guestId\": 1,\n" +
                                    "            \"orderId\": 1,\n" +
                                    "            \"createAt\": \"2022-12-07T12:56:40.117827\",\n" +
                                    "            \"updateAt\": \"2022-12-07T13:35:16.6613937\"\n" +
                                    "        }\n" +
                                    "    ]\n" +
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
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
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

    @Operation(summary = "Get ticket (EndPoint digunakan untuk mendapat ticket detail dari guestId \"https://febe6.up.railway.app/api/ticket/get/{guestId}\")")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "sukses", content = @Content(examples = {
                    @ExampleObject(name = "Get ticket By guestId",
                            description = "Menampilkan ticket berdasarkan guestId yang diapatkan setelah guest melakukan order," +
                                    "jika berhasil akan menampilkan data seperti diatas",
                            value = "{\n" +
                                    "    \"success\": true,\n" +
                                    "    \"statusCode\": 200,\n" +
                                    "    \"message\": \"Successfully!\",\n" +
                                    "    \"data\": {\n" +
                                    "        \"id\": 1,\n" +
                                    "        \"status\": true,\n" +
                                    "        \"scheduleId\": 1,\n" +
                                    "        \"seatId\": 1,\n" +
                                    "        \"guestId\": 1,\n" +
                                    "        \"orderId\": 1,\n" +
                                    "        \"createAt\": \"2022-12-07T12:56:40.117827\",\n" +
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
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
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
