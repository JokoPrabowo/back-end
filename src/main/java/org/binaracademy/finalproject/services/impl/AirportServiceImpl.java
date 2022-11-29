package org.binaracademy.finalproject.services.impl;

import lombok.extern.slf4j.Slf4j;
import org.binaracademy.finalproject.data.AirportData;
import org.binaracademy.finalproject.entity.AirportEntity;
import org.binaracademy.finalproject.repositories.AirportRepo;
import org.binaracademy.finalproject.services.AirportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Slf4j
@Service
public class AirportServiceImpl implements AirportService {

    @Autowired
    AirportRepo airportRepo;

    public AirportEntity create (AirportEntity data){
        try{
            AirportEntity sample = AirportEntity.builder()
                    .name(data.getName())
                    .cityId(data.getCityId())
                    .createAt(LocalDateTime.now()).build();
            log.info("Airport has been created {}", sample.getName());
            return airportRepo.save(sample);
        }catch (Exception e){
            log.error("Error found {}", e.getMessage());
            return null;
        }
    }
}
