package com.sluv.backoffice.domain.user.repository;

import com.sluv.backoffice.domain.user.entity.User;
import com.sluv.backoffice.domain.user.repository.impl.UserRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long>, UserRepositoryCustom {
}
