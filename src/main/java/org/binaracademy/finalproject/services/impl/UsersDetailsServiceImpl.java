package org.binaracademy.finalproject.services.impl;

import lombok.extern.slf4j.Slf4j;
import org.binaracademy.finalproject.entity.SeatEntity;
import org.binaracademy.finalproject.entity.UserDetailsEntity;
import org.binaracademy.finalproject.entity.UserEntity;
import org.binaracademy.finalproject.repositories.UsersDetailsRepo;
import org.binaracademy.finalproject.services.UsersDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class UsersDetailsServiceImpl implements UsersDetailsService {

    @Autowired
    private UsersDetailsRepo userDetailRepo;

    private static final String ERROR_FOUND = "Error found : {}";

    @Override
    public UserDetailsEntity create(UserDetailsEntity userdetailsEntity) {
        try{
            UserDetailsEntity userDetails = userDetailRepo.save(userdetailsEntity);
            log.info("User Details has been created");
            return userDetails;
        }catch (Exception e){
            log.error(ERROR_FOUND, e.getMessage());
            return null;
        }
    }

    @Override
    public UserDetailsEntity findByUserid(Long user_id) {
        UserDetailsEntity existUserDetails = userDetailRepo.findUserDetailsByUserId(user_id);

        return existUserDetails;
    }



}
