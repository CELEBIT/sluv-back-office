package com.sluv.backoffice.domain.user.entity;

import com.sluv.backoffice.global.common.entity.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "user_report_stack")
public class UserReportStack extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_report_stack_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "reported_id")
    @NotNull
    private User reported;

}
