package org.binaracademy.finalproject.services;

import org.binaracademy.finalproject.entity.ScheduleEntity;

import java.util.List;

public interface ScheduleService {

    ScheduleEntity create(ScheduleEntity schedule);
    List<ScheduleEntity> getAll();
    ScheduleEntity getOneSchedule(Long id);

}
