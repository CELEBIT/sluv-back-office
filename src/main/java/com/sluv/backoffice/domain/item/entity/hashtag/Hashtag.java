package com.sluv.backoffice.domain.item.entity.hashtag;

import com.sluv.backoffice.global.common.entity.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Table(name = "hashtag")
public class Hashtag extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "hashtag_id")
    private Long id;

    @NotNull
    @Size(max = 45)
    private String content;

    @Enumerated(EnumType.STRING)
    private HashtagStatus hashtagStatus = HashtagStatus.ACTIVE;
    public void changeStatus(HashtagStatus status){
        this.hashtagStatus = status;
    }
}
