package com.sluv.backoffice.domain.closet.entity;

import com.sluv.backoffice.domain.closet.enums.ClosetStatus;
import com.sluv.backoffice.domain.user.entity.User;
import com.sluv.backoffice.global.common.entity.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@DynamicInsert
@Builder
@Table(name = "closet")
public class Closet extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "closet_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @NotNull
    private User user;

    @NotNull
    @Size(max = 45)
    private String name;

    @NotNull
    @Column(columnDefinition = "TEXT")
    private String coverImgUrl;

    private String color;

    @NotNull
    @ColumnDefault("0")
    private Boolean basicFlag;

    @Enumerated(EnumType.STRING)
    @Column(length = 45, columnDefinition = "varchar(45) default 'PUBLIC'")
    private ClosetStatus closetStatus;
    public void changeClosetCover(String name, String coverImgUrl, String color, ClosetStatus closetStatus){
        this.name = name;
        this.coverImgUrl = coverImgUrl;
        this.color = color;
        this.closetStatus = closetStatus;
    }
}
