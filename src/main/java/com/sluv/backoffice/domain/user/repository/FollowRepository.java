package com.sluv.backoffice.domain.user.repository;

import com.sluv.backoffice.domain.user.entity.Follow;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FollowRepository extends JpaRepository<Follow, Long> {
}
