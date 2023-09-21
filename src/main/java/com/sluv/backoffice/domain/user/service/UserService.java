package com.sluv.backoffice.domain.user.service;

import com.sluv.backoffice.domain.user.dto.UserInfoDto;
import com.sluv.backoffice.domain.user.repository.UserRepository;
import com.sluv.backoffice.global.common.response.PaginationResDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public PaginationResDto<UserInfoDto> getAllUserInfo(Pageable pageable) {
        Page<UserInfoDto> users = userRepository.findAllUserInfo(pageable);

        return PaginationResDto.<UserInfoDto>builder()
                .hasNext(users.hasNext())
                .page(users.getNumber())
                .content(users.getContent())
                .build();
    }
}
