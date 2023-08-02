package com.sluv.backoffice.domain.celeb.repository;

import com.sluv.backoffice.domain.celeb.entity.Celeb;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CelebRepository extends JpaRepository<Celeb, Long> {
}
