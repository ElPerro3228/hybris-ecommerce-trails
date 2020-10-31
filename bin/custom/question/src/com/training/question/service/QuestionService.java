package com.training.question.service;

import com.training.question.model.QuestionModel;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface QuestionService {
    QuestionModel getQuestionByCode(String code);
    List<QuestionModel> getQuestions();
    Map<String, List<QuestionModel>> getQuestionsGroupedByProductCodeHavingCreationTimeAfterLastPerformance(Date lastPerformance);
}
