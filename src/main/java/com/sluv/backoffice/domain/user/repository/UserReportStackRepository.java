package com.sluv.backoffice.domain.user.repository;

import com.sluv.backoffice.domain.user.entity.User;
import com.sluv.backoffice.domain.user.entity.UserReportStack;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;

public interface UserReportStackRepository extends JpaRepository<UserReportStack, Long> {

    Long countByReportedAndCreatedAtAfter(User reportedUser, LocalDateTime createdAt);
}