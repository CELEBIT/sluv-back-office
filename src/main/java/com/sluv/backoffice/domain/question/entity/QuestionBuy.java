package com.sluv.backoffice.domain.question.entity;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@Getter
@AllArgsConstructor
@SuperBuilder
@DiscriminatorValue("Buy")
public class QuestionBuy extends Question{


    private LocalDateTime voteEndTime;

}
