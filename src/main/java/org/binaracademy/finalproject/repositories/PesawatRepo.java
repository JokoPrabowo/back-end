package org.binaracademy.finalproject.repositories;

import org.binaracademy.finalproject.entity.PesawatEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PesawatRepo extends JpaRepository<PesawatEntity, Long> {
}
