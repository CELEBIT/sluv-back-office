package com.sluv.backoffice.domain.user.repository.impl;

import com.sluv.backoffice.domain.user.dto.UserInfoDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserRepositoryCustom {

    Page<UserInfoDto> findAllUserInfo(Pageable pageable);
}
