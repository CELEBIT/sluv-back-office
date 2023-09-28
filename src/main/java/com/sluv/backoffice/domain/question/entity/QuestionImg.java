package com.sluv.backoffice.domain.question.entity;

import com.sluv.backoffice.global.common.entity.BaseEntity;
import com.sluv.backoffice.global.common.enums.ItemImgOrLinkStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;

@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@DynamicInsert
@Builder
@Table(name = "question_img")
public class QuestionImg extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "question_img_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "question_id")
    @NotNull
    private Question question;

    @NotNull
    @Column(columnDefinition = "TEXT")
    private String imgUrl;

    @Size(max = 100)
    private String description;

    @NotNull
    @ColumnDefault("0")
    private Boolean representFlag;

    private Integer sortOrder;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "varchar(45) default 'ACTIVE'")
    private ItemImgOrLinkStatus itemImgOrLinkStatus;

}
