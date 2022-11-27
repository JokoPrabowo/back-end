package org.binaracademy.finalproject.services.impl;

import lombok.extern.slf4j.Slf4j;
import org.binaracademy.finalproject.data.PesawatData;
import org.binaracademy.finalproject.entity.PesawatEntity;
import org.binaracademy.finalproject.repositories.PesawatRepo;
import org.binaracademy.finalproject.services.PesawatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Slf4j
@Service
public class PesawatServiceImpl implements PesawatService {

    @Autowired
    PesawatRepo pesawatRepo;

    public PesawatEntity create(PesawatEntity data){
        try{
            PesawatEntity sample = PesawatEntity.builder()
                    .name(data.getName())
                    .airportId(data.getAirportId())
                    .createAt(LocalDateTime.now()).build();
            log.info("Pesawat has been created {}", sample.getName());
            return pesawatRepo.save(sample);
        }catch (Exception e){
            log.error("Error found {}", e.getMessage());
            return null;
        }
    }
}
