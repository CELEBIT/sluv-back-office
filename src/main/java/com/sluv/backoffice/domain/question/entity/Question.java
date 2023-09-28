package com.sluv.backoffice.domain.question.entity;

import com.sluv.backoffice.domain.comment.entity.Comment;
import com.sluv.backoffice.domain.question.enums.QuestionStatus;
import com.sluv.backoffice.domain.user.entity.User;
import com.sluv.backoffice.global.common.entity.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import java.util.List;


@Entity
@Getter
@NoArgsConstructor
@DynamicInsert
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "QTYPE")
public class Question extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "question_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @NotNull
    private User user;

    @NotNull
    @Size(max = 255)
    private String title;

    @Size(max = 1001)
    private String content;

    @NotNull
    private Long searchNum;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "varchar(45) default 'ACTIVE'")
    private QuestionStatus questionStatus;


    @OneToMany(mappedBy = "question")
    private List<Comment> commentList;

    public Question(Long id, User user, String title, String content,
                    Long searchNum, QuestionStatus questionStatus
                    ) {

        this.id = id;
        this.user = user;
        this.title = title;
        this.content = content;
        this.searchNum = searchNum;
        this.questionStatus = questionStatus;
    }

    public void changeQuestionStatus(QuestionStatus questionStatus){
        this.questionStatus = questionStatus;
    }

    public void increaseSearchNum(){
        this.searchNum++;
    }
    public void decreaseSearchNum(){
        this.searchNum--;
    }

}
