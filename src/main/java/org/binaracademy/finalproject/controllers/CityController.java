package org.binaracademy.finalproject.controllers;

import lombok.RequiredArgsConstructor;
import org.binaracademy.finalproject.dto.CityResponse;
import org.binaracademy.finalproject.dto.ResponseData;
import org.binaracademy.finalproject.services.CityService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class CityController {

    private final CityService cityService;

    @GetMapping("/getCity/{countryId}")
    public ResponseEntity<ResponseData<List<CityResponse>>> getCity(@PathVariable("countryId") Long countryId){

        ResponseData<List<CityResponse>> rs = new ResponseData<>();

        try {
            List<CityResponse> cityResponses = new ArrayList<>();
            cityService.getCity(countryId).forEach(city
                    -> cityResponses.add(CityResponse.builder()
                    .id(city.getId())
                    .cityName(city.getName())
                    .countryName(city.getCountry().getName()).build()));

            rs.setSuccess(true);
            rs.setStatusCode(200);
            rs.setMessage("Successfully!");
            rs.setData(cityResponses);
            return ResponseEntity.ok(rs);
        }catch (Exception e){
            rs.setSuccess(false);
            rs.setStatusCode(500);
            rs.setMessage(e.getMessage());
            rs.setData(null);
            return ResponseEntity.internalServerError().body(rs);
        }
    }

}
