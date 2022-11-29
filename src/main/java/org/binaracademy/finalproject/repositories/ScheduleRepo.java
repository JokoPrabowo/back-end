package org.binaracademy.finalproject.repositories;

import org.binaracademy.finalproject.entity.ScheduleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ScheduleRepo extends JpaRepository<ScheduleEntity, Long> {
}
