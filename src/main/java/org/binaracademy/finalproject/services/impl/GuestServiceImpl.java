package org.binaracademy.finalproject.services.impl;

import lombok.extern.slf4j.Slf4j;
import org.binaracademy.finalproject.entity.GuestEntity;
import org.binaracademy.finalproject.repositories.GuestRepo;
import org.binaracademy.finalproject.services.GuestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Slf4j
@Service
public class GuestServiceImpl implements GuestService {
    @Autowired
    private GuestRepo guestRepo;

    public GuestEntity create(GuestEntity data){
        try{
            log.info("Guest has been created");
            GuestEntity sample = data;
            sample.setCreateAt(LocalDateTime.now());
            return guestRepo.save(sample);
        }catch (Exception e){
            log.error("Error found {}", e);
            return null;
        }
    }
}
