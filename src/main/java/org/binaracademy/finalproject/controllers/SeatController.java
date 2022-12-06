package org.binaracademy.finalproject.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.binaracademy.finalproject.dto.ResponseData;
import org.binaracademy.finalproject.entity.SeatEntity;
import org.binaracademy.finalproject.services.SeatService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Tag(name = "Seat", description = "Operation about Seat")
public class SeatController {

    private final SeatService seatService;

    @Operation(summary = "Get all Seat")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "sukses", content = @Content(examples = {
                    @ExampleObject(name = "List Seat",
                            description = "Menampilkan semua seat yang ada dalam database",
                            value = "{\n"
                                    + "    \"success\": true,\n"
                                    + "    \"statusCode\": 200,\n"
                                    + "    \"message\": \"Successfully!\",\n"
                                    + "    \"data\": [\n"
                                    + "        {\n"
                                    + "            \"id\": 1,\n"
                                    + "            \"seatName\": \"001\"\n"
                                    + "        },\n"
                                    + "        {\n"
                                    + "            \"id\": 2,\n"
                                    + "            \"seatName\": \"002\"\n"
                                    + "        },\n"
                                    + "        {\n"
                                    + "            \"id\": 3,\n"
                                    + "            \"seatName\": \"003\"\n"
                                    + "        },\n"
                                    + "        {\n"
                                    + "            \"id\": 4,\n"
                                    + "            \"seatName\": \"004\"\n"
                                    + "        },\n"
                                    + "        {\n"
                                    + "            \"id\": 5,\n"
                                    + "            \"seatName\": \"005\"\n"
                                    + "        }\n"
                                    + "    ]\n"
                                    + "}")
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
    @GetMapping("/getSeats")
    public ResponseEntity<ResponseData<List<SeatEntity>>> getAll(){
        ResponseData<List<SeatEntity>> response = new ResponseData<>();
        try {
            response.setData(seatService.getAll());
            response.setSuccess(true);
            response.setStatusCode(HttpStatus.OK.value());
            response.setMessage("Successfully!");
            return ResponseEntity.ok(response);
        }catch (Exception e){
            response.setSuccess(false);
            response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setMessage(e.getMessage());
            response.setData(null);
            return ResponseEntity.internalServerError().body(response);
        }
    }

    @Operation(summary = "Get all Seat Available")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "sukses", content = @Content(examples = {
                    @ExampleObject(name = "List Seat Available",
                            description = "Menampilkan semua seat yang masih tersedia dalam schedule yang ada dalam database",
                            value = "{\n"
                                    + "    \"success\": true,\n"
                                    + "    \"statusCode\": 200,\n"
                                    + "    \"message\": \"Successfully!\",\n"
                                    + "    \"data\": [\n"
                                    + "        {\n"
                                    + "            \"id\": 1,\n"
                                    + "            \"seatName\": \"001\"\n"
                                    + "        },\n"
                                    + "        {\n"
                                    + "            \"id\": 3,\n"
                                    + "            \"seatName\": \"003\"\n"
                                    + "        },\n"
                                    + "        {\n"
                                    + "            \"id\": 5,\n"
                                    + "            \"seatName\": \"005\"\n"
                                    + "        }\n"
                                    + "    ]\n"
                                    + "}")
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
    @GetMapping("/getSeats/{scheduleId}")
    public ResponseEntity<ResponseData<List<SeatEntity>>> getSeatAvail(@PathVariable("scheduleId") Long scheduleId){
        ResponseData<List<SeatEntity>> response = new ResponseData<>();
        try {
            response.setData(seatService.getSeatAvail(scheduleId));
            response.setSuccess(true);
            response.setStatusCode(HttpStatus.OK.value());
            response.setMessage("Successfully!");
            return ResponseEntity.ok(response);
        }catch (Exception e){
            response.setSuccess(false);
            response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setMessage(e.getMessage());
            response.setData(null);
            return ResponseEntity.internalServerError().body(response);
        }
    }

}
