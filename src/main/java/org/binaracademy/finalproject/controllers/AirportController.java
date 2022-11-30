package org.binaracademy.finalproject.controllers;

import org.binaracademy.finalproject.dto.Request.AirportRequest;
import org.binaracademy.finalproject.dto.Response.AirportResponse;
import org.binaracademy.finalproject.dto.ResponseData;
import org.binaracademy.finalproject.entity.AirportEntity;
import org.binaracademy.finalproject.services.AirportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/airport")
public class AirportController {
    @Autowired
    AirportService airportService;

    @PostMapping("/add")
    public ResponseEntity<ResponseData<Object>> create(@Valid @RequestBody AirportRequest data, Errors errors){
        try {
            ResponseData<Object> res = new ResponseData<>();
            if(errors.hasErrors()){
                res.setSuccess(false);
                res.setStatusCode(HttpStatus.BAD_REQUEST.value());
                res.setMessage("Failed!");
                res.setData(null);
                return ResponseEntity.badRequest().body(res);
            }
            res.setSuccess(true);
            res.setStatusCode(HttpStatus.CREATED.value());
            res.setMessage("Successfully!");
            res.setData(airportService.create(new AirportEntity(null, data.getName(), data.getCityId(), null, null, null)));
            return  ResponseEntity.ok().body(res);
        }catch (Exception e){
            ResponseData<Object> res = new ResponseData<>();
            res.setSuccess(false);
            res.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            res.setMessage("Failed!");
            res.setData(null);
            return ResponseEntity.internalServerError().body(res);
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ResponseData<Object>> update(@PathVariable("id") Long id, @Valid @RequestBody AirportRequest data, Errors errors){
        ResponseData<Object> res = new ResponseData<>();
        try{
            if(errors.hasErrors()){
                res.setSuccess(false);
                res.setStatusCode(HttpStatus.BAD_REQUEST.value());
                res.setMessage("Failed!");
                res.setData(null);
                return ResponseEntity.badRequest().body(res);
            }
            res.setSuccess(true);
            res.setStatusCode(HttpStatus.CREATED.value());
            res.setMessage("Successfully!");
            res.setData(airportService.update(id, new AirportEntity(null, data.getName(), data.getCityId(), null, null, null)));
            return  ResponseEntity.ok().body(res);
        }catch (Exception e){
            res.setSuccess(false);
            res.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            res.setMessage("Failed!");
            res.setData(null);
            return ResponseEntity.internalServerError().body(res);
        }
    }

    @GetMapping("/getAirports")
    public ResponseEntity<ResponseData<List<AirportResponse>>> getAll(){
        ResponseData<List<AirportResponse>> res = new ResponseData<>();
        try{
            List<AirportResponse> data = new ArrayList<>();
            airportService.getAll().forEach(airport ->
                    data.add(AirportResponse.builder()
                        .id(airport.getId())
                        .airportName(airport.getName())
                        .cityName(airport.getCity().getName()).build()));
            res.setSuccess(true);
            res.setStatusCode(HttpStatus.ACCEPTED.value());
            res.setMessage("Successfully!");
            res.setData(data);
            return  ResponseEntity.ok().body(res);
        }catch (Exception e){
            res.setSuccess(false);
            res.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            res.setMessage("Failed!");
            res.setData(null);
            return ResponseEntity.internalServerError().body(res);
        }
    }

    @GetMapping("/getAirport/{id}")
    public ResponseEntity<ResponseData<Object>> getOne(@PathVariable("id") Long id){
        ResponseData<Object> res = new ResponseData<>();
        try{
            res.setSuccess(true);
            res.setStatusCode(HttpStatus.ACCEPTED.value());
            res.setMessage("Successfully!");
            AirportEntity sample = airportService.getOne(id);
            AirportResponse data = AirportResponse.builder()
                            .id(sample.getId())
                                    .airportName(sample.getName())
                                            .cityName(sample.getCity().getName())
                                                    .build();
            res.setData(data);
            return  ResponseEntity.ok().body(res);
        }catch (Exception e){
            res.setSuccess(false);
            res.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            res.setMessage("Failed!");
            res.setData(null);
            return ResponseEntity.internalServerError().body(res);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ResponseData<Integer>> delete(@PathVariable("id") Long id){
        ResponseData<Integer> res = new ResponseData<>();
        try{
            res.setSuccess(true);
            res.setStatusCode(HttpStatus.ACCEPTED.value());
            res.setMessage("Successfully!");
            airportService.delete(id);
            res.setData(1);
            return  ResponseEntity.ok().body(res);
        }catch (Exception e){
            res.setSuccess(false);
            res.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            res.setMessage("Failed!");
            res.setData(0);
            return ResponseEntity.internalServerError().body(res);
        }
    }

}
