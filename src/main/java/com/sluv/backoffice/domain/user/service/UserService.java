package com.sluv.backoffice.domain.user.service;

import com.sluv.backoffice.domain.user.dto.UserInfoDto;
import com.sluv.backoffice.domain.user.entity.User;
import com.sluv.backoffice.domain.user.enums.UserStatus;
import com.sluv.backoffice.domain.user.exception.UserNotFoundException;
import com.sluv.backoffice.domain.user.repository.UserRepository;
import com.sluv.backoffice.global.common.response.PaginationResDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {

    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    public PaginationResDto<UserInfoDto> getAllUserInfo(Pageable pageable) {
        Page<UserInfoDto> users = userRepository.getAllUserInfo(pageable);

        return PaginationResDto.<UserInfoDto>builder()
                .hasNext(users.hasNext())
                .page(users.getNumber())
                .content(users.getContent())
                .build();
    }

    public void updateUserStatus(Long userId, UserStatus userStatus) {
        User user = userRepository.findById(userId)
                .orElseThrow(UserNotFoundException::new);

        user.changeUserStatus(userStatus);
    }
}
