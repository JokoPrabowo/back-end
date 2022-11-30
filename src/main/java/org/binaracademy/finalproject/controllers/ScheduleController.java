package org.binaracademy.finalproject.controllers;

import lombok.RequiredArgsConstructor;
import org.binaracademy.finalproject.dto.Request.SchedulePageRequest;
import org.binaracademy.finalproject.dto.Request.ScheduleRequest;
import org.binaracademy.finalproject.dto.ResponseData;
import org.binaracademy.finalproject.entity.ScheduleEntity;
import org.binaracademy.finalproject.services.ScheduleService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ScheduleController {

    private final ScheduleService scheduleService;

    @PostMapping("/add/schedule")
    public ResponseEntity<ResponseData<ScheduleEntity>> create(@Valid @RequestBody ScheduleRequest scheduleRequest, Errors errors){

        ResponseData<ScheduleEntity> response = new ResponseData<>();

        try {
            ScheduleEntity scheduleEntity = scheduleService.create(ScheduleEntity.builder()
                    .departureAiport(scheduleRequest.getDepartureAiport())
                    .arrivalAirport(scheduleRequest.getArrivalAirport())
                    .price(scheduleRequest.getPrice())
                    .maxSeat(scheduleRequest.getMaxSeat())
                    .date(scheduleRequest.getDate())
                    .scheduleTimeId(scheduleRequest.getScheduleTimeId())
                    .categoryClassId(scheduleRequest.getCategoryClassId())
                    .pesawatId(scheduleRequest.getPesawatId()).build());

            response.setData(scheduleEntity);
            response.setSuccess(true);
            response.setStatusCode(HttpStatus.OK.value());
            response.setMessage("Successfully!");
            return ResponseEntity.ok().body(response);
        }catch (Exception e){
            response.setSuccess(false);
            response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setMessage(e.getMessage());
            response.setData(null);
            return ResponseEntity.internalServerError().body(response);
        }
    }

    @PostMapping("/getSchedule/{size}/{page}/{sort}")
    public ResponseEntity<ResponseData<List<ScheduleEntity>>> getPageSchedule(@RequestBody SchedulePageRequest schedulePageRequest,
                                                                             @PathVariable("size") int size, @PathVariable("page") int page,
                                                                              @PathVariable("sort") String sort){

        ResponseData<List<ScheduleEntity>> response = new ResponseData<>();

        try {
            Pageable pageable = PageRequest.of(page, size, Sort.by("id"));
            if (sort.equalsIgnoreCase("desc")){
                pageable = PageRequest.of(page, size, Sort.by("id").descending());
            }
            response.setData(scheduleService.getPageFromTo(schedulePageRequest.getDepartureAiport(),
                    schedulePageRequest.getArrivalAirport(), pageable));
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
