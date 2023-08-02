package com.sluv.backoffice.domain.closet.repository;

import com.sluv.backoffice.domain.closet.entity.Closet;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClosetRepository extends JpaRepository<Closet, Long> {
}
