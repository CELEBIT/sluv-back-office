package com.sluv.backoffice.domain.user.repository.impl;

import com.sluv.backoffice.domain.user.dto.HotUserResDto;
import com.sluv.backoffice.domain.user.dto.UserInfoDto;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserRepositoryCustom {

    Page<UserInfoDto> getAllUserInfo(Pageable pageable);

    List<HotUserResDto> getTop3HotUser();
}
