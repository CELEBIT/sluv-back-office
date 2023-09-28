package com.sluv.backoffice.domain.celeb.entity;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.sluv.backoffice.domain.celeb.enums.CelebStatus;
import com.sluv.backoffice.global.common.entity.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@DynamicInsert
@Builder
@JsonPropertyOrder({"id", "parent", "celebCategory", "celebNameKr", "celebNameEn", "celebStatus", "created_at", "updated_at"})
@Table(name = "celeb")
public class Celeb extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "celeb_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private Celeb parent;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "celeb_category_id")
    @NotNull
    private CelebCategory celebCategory;

    @NotNull
    @Size(max = 255)
    private String celebNameKr;

    @NotNull
    @Size(max = 255)
    private String celebNameEn;

    @Enumerated(EnumType.STRING)
    @Column(length = 45, columnDefinition = "varchar(45) default 'ACTIVE'")
    private CelebStatus celebStatus;

    @OneToMany(mappedBy = "parent")
    private List<Celeb> subCelebList;
}
