package com.sluv.backoffice.domain.question.entity;

import com.sluv.backoffice.domain.celeb.entity.Celeb;
import com.sluv.backoffice.domain.celeb.entity.NewCeleb;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@DiscriminatorValue("Find")
public class QuestionFind extends Question{

    @ManyToOne
    @JoinColumn(name = "celeb_id")
    private Celeb celeb;

    @ManyToOne
    @JoinColumn(name = "new_celeb_id")
    private NewCeleb newCeleb;

}
