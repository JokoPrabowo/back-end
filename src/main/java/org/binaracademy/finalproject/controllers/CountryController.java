package org.binaracademy.finalproject.controllers;

import lombok.RequiredArgsConstructor;
import org.binaracademy.finalproject.dto.Response.CountryResponse;
import org.binaracademy.finalproject.dto.ResponseData;
import org.binaracademy.finalproject.services.CountryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class CountryController {

    private final CountryService countryService;

    @GetMapping("/getCountry")
    public ResponseEntity<ResponseData<List<CountryResponse>>> getCountry(){

        ResponseData<List<CountryResponse>> rs = new ResponseData<>();

        try {
            List<CountryResponse> cs = new ArrayList<>();
            countryService.getAll().forEach(country
                    -> cs.add(CountryResponse.builder()
                    .id(country.getId())
                    .countryName(country.getName()).build()));

            rs.setSuccess(true);
            rs.setStatusCode(200);
            rs.setMessage("Successfully!");
            rs.setData(cs);
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
