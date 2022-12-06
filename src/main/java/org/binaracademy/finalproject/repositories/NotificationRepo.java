package org.binaracademy.finalproject.repositories;

import org.binaracademy.finalproject.entity.NotificationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationRepo extends JpaRepository<NotificationEntity, Long> {

    List<NotificationEntity> findByUserId(Long userId);

}
