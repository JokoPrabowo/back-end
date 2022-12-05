package org.binaracademy.finalproject.controllers;

import lombok.RequiredArgsConstructor;
import org.binaracademy.finalproject.dto.ResponseData;
import org.binaracademy.finalproject.entity.SeatEntity;
import org.binaracademy.finalproject.services.SeatService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class SeatController {

    private final SeatService seatService;

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
