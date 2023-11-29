package com.sluv.backoffice.domain.user.entity;

import com.sluv.backoffice.domain.user.enums.SnsType;
import com.sluv.backoffice.domain.user.enums.UserGender;
import com.sluv.backoffice.domain.user.enums.UserStatus;
import com.sluv.backoffice.global.common.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;


@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@DynamicInsert
@Table(name = "user")
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @NotNull
    @Size(max = 320)
    private String email;

    @Size(max = 45)
    private String nickname;

    @NotNull
    @Enumerated(EnumType.STRING)
    private SnsType snsType;

    @Column(columnDefinition = "TEXT")
    private String profileImgUrl;

    @Size(max = 45)
    private String ageRange;

    @Size(max = 45)
    @Enumerated(EnumType.STRING)
    private UserGender gender;

    @Enumerated(EnumType.STRING)
    @Column(length = 45, columnDefinition = "varchar(45) default 'PENDING_PROFILE'")
    private UserStatus userStatus;

    public void changeProfileImgUrl(String profileImgUrl) {
        this.profileImgUrl = profileImgUrl;
    }

    public void changeNickname(String nickname) {
        this.nickname = nickname;
    }

    public void changeUserStatus(UserStatus userStatus) {
        this.userStatus = userStatus;
    }
}
