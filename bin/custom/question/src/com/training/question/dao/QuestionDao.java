package com.training.question.dao;

import com.training.question.model.QuestionModel;

import java.util.List;

public interface QuestionDao {
    List<QuestionModel> getQuestionByCode(String code);
    List<QuestionModel> getQuestions();
}
