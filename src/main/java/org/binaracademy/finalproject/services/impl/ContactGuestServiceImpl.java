package org.binaracademy.finalproject.services.impl;

import lombok.extern.slf4j.Slf4j;
import org.binaracademy.finalproject.entity.ContactGuestEntity;
import org.binaracademy.finalproject.repositories.ContactGuestRepo;
import org.binaracademy.finalproject.services.ContactGuestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Slf4j
@Service
public class ContactGuestServiceImpl implements ContactGuestService {
    @Autowired
    private ContactGuestRepo contactGuestRepo;

    public ContactGuestEntity create(ContactGuestEntity data){
        try{
            log.info("Contact Guest has been created");
            ContactGuestEntity sample = data;
            LocalDateTime created = LocalDateTime.now();
            DateTimeFormatter format = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
            String formatted = created.format(format);
            sample.setCreateAt(LocalDateTime.parse(formatted, format));
            return contactGuestRepo.save(sample);
        }catch (Exception e){
            log.error("Error found {}", e);
            return  null;
        }
    }
}
