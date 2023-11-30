package com.sluv.backoffice.user;

import static com.sluv.backoffice.domain.user.enums.UserGender.WOMAN;
import static com.sluv.backoffice.domain.user.enums.UserStatus.ACTIVE;
import static org.assertj.core.api.Assertions.assertThat;

import com.sluv.backoffice.domain.user.dto.UserCountByCategoryResDto;
import com.sluv.backoffice.domain.user.dto.UserCountByCategoryResDto.UserCountByEachCategoryResDto;
import com.sluv.backoffice.domain.user.entity.User;
import com.sluv.backoffice.domain.user.enums.SnsType;
import com.sluv.backoffice.domain.user.enums.UserGender;
import com.sluv.backoffice.domain.user.repository.UserRepository;
import com.sluv.backoffice.domain.user.service.UserService;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
public class UserServiceTest {
    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;


    @DisplayName("사용자의 성별 분포 통계를 조회한다")
    @Test
    public void getUserCountByEachGender() {
        //given
        User manUser1 = User.builder()
                .email("test1@sluv.com")
                .snsType(SnsType.KAKAO)
                .userStatus(ACTIVE)
                .gender(UserGender.MAN)
                .build();

        User manUser2 = User.builder()
                .email("test2@sluv.com")
                .snsType(SnsType.KAKAO)
                .userStatus(ACTIVE)
                .gender(UserGender.MAN)
                .build();

        User woManUser1 = User.builder()
                .email("test3@sluv.com")
                .snsType(SnsType.KAKAO)
                .userStatus(ACTIVE)
                .gender(WOMAN)
                .build();

        User woManUser2 = User.builder()
                .email("test4@sluv.com")
                .snsType(SnsType.KAKAO)
                .userStatus(ACTIVE)
                .gender(WOMAN)
                .build();

        User woManUser3 = User.builder()
                .email("test5@sluv.com")
                .snsType(SnsType.KAKAO)
                .userStatus(ACTIVE)
                .gender(WOMAN)
                .build();

        User unKnownUser1 = User.builder()
                .email("test6@sluv.com")
                .snsType(SnsType.KAKAO)
                .userStatus(ACTIVE)
                .gender(UserGender.UNKNOWN)
                .build();

        List<User> users = List.of(manUser1, manUser2, woManUser1, woManUser2, woManUser3, unKnownUser1);
        userRepository.saveAll(users);

        //when
        UserCountByCategoryResDto userCountByGender = userService.getUserCountByGender();
        List<UserCountByEachCategoryResDto> woManUsers = userCountByGender.getEachCategory().stream()
                .filter(dto -> dto.getCategory().equals(WOMAN.toString()))
                .toList();

        //then
        assertThat(userCountByGender.getTotalCount()).isEqualTo(6);
        assertThat(woManUsers.get(0).getCount()).isEqualTo(3);
        assertThat(woManUsers.get(0).getPercent()).isEqualTo(50);

    }
}
