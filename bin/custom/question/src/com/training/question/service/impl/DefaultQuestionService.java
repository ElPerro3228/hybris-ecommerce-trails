package com.training.question.service.impl;

import com.training.question.dao.QuestionDao;
import com.training.question.model.QuestionModel;
import com.training.question.service.QuestionService;
import de.hybris.platform.servicelayer.exceptions.AmbiguousIdentifierException;
import de.hybris.platform.servicelayer.exceptions.UnknownIdentifierException;
import org.springframework.beans.factory.annotation.Required;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DefaultQuestionService implements QuestionService {

    private QuestionDao questionDao;

    @Override
    public QuestionModel getQuestionByCode(String code) {
        final List<QuestionModel> result = questionDao.getQuestionByCode(code);
        if (result.isEmpty())
        {
            throw new UnknownIdentifierException("Question with code '" + code + "' not found!");
        }
        else if (result.size() > 1)
        {
            throw new AmbiguousIdentifierException("Question code '" + code + "' is not unique, " + result.size() + " questions found!");
        }
        return result.get(0);
    }

    @Override
    public Map<String, List<QuestionModel>> getQuestionsGroupedByProductCodeHavingCreationTimeAfterLastPerformance(Date lastPerformance) {
        List<QuestionModel> questions = questionDao.getQuestions();
        Map<String, List<QuestionModel>> questionsMap = new HashMap<>();
        for(QuestionModel question : questions) {
            if (lastPerformance != null) {
                putQuestionIntoMap(lastPerformance, questionsMap, question);
            }
        }
        return questionsMap;
    }

    private void putQuestionIntoMap(Date lastPerformance, Map<String, List<QuestionModel>> questionsMap, QuestionModel question) {
        if (question.getCreationtime().after(lastPerformance)) {
            String productCode = question.getProduct().getCode();
            if (questionsMap.get(productCode) == null) {
                List<QuestionModel> productQuestions = new ArrayList<>();
                productQuestions.add(question);
                questionsMap.put(productCode, productQuestions);
            } else {
                questionsMap.get(productCode).add(question);
            }
        }
    }

    @Override
    public List<QuestionModel> getQuestions() {
        return questionDao.getQuestions();
    }

    @Required
    public void setQuestionDao(final QuestionDao questionDao)
    {
        this.questionDao = questionDao;
    }
}
