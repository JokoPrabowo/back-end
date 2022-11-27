package org.binaracademy.finalproject.services;

import org.binaracademy.finalproject.entity.GuestEntity;
import org.binaracademy.finalproject.entity.UserEntity;

public interface UserService {
    public UserEntity create(UserEntity data);

    public void processOAuthPostLogin(String email, String nama);

}
