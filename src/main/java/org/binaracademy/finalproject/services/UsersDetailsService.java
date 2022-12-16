package org.binaracademy.finalproject.services;

import org.binaracademy.finalproject.entity.UserDetailsEntity;

public interface UsersDetailsService {

    UserDetailsEntity create (UserDetailsEntity userdetailsEntity);
    UserDetailsEntity findByUserid (Long user_id);
}
