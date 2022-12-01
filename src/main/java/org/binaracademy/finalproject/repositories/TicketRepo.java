package org.binaracademy.finalproject.repositories;

import org.binaracademy.finalproject.entity.TicketEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TicketRepo extends JpaRepository<TicketEntity, Long> {
}
