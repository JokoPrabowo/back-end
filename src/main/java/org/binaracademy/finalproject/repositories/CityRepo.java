package org.binaracademy.finalproject.repositories;

import org.binaracademy.finalproject.entity.CityEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CityRepo extends JpaRepository<CityEntity, Long> {
}
