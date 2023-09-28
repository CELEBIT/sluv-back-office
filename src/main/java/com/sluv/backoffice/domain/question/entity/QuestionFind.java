package com.sluv.backoffice.domain.question.entity;

import com.sluv.backoffice.domain.celeb.entity.Celeb;
import com.sluv.backoffice.domain.celeb.entity.NewCeleb;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@DiscriminatorValue("Find")
public class QuestionFind extends Question{

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "celeb_id")
    private Celeb celeb;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "new_celeb_id")
    private NewCeleb newCeleb;

}
