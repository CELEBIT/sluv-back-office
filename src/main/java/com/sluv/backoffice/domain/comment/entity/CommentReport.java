package com.sluv.backoffice.domain.comment.entity;

import com.sluv.backoffice.domain.comment.enums.CommentReportReason;
import com.sluv.backoffice.domain.user.entity.User;
import com.sluv.backoffice.global.common.entity.BaseEntity;
import com.sluv.backoffice.global.common.enums.ReportStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@DynamicInsert
@Builder
@Getter
@Table(name = "comment_report")
public class CommentReport extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_report_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "comment_id")
    @NotNull
    private Comment comment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reporter_id")
    @NotNull
    private User reporter;

    @NotNull
    @Enumerated(EnumType.STRING)
    private CommentReportReason commentReportReason;

    @Size(max = 1002)
    private String content;

    @Enumerated(EnumType.STRING)
    @Column(length = 45, columnDefinition = "varchar(45) default 'WAITING'")
    private ReportStatus reportStatus;

}
