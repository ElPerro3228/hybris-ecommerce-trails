package com.training.question.dao.impl;

import com.training.question.dao.QuestionDao;
import com.training.question.model.QuestionModel;
import de.hybris.platform.servicelayer.search.FlexibleSearchQuery;
import de.hybris.platform.servicelayer.search.FlexibleSearchService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class DefaultQuestionDao implements QuestionDao {

    @Autowired
    private FlexibleSearchService flexibleSearchService;

    @Override
    public List<QuestionModel> getQuestionByCode(String code) {
        final String queryString = //
                "SELECT {p:" + QuestionModel.PK + "}" //
                        + "FROM {" + QuestionModel._TYPECODE + " AS p} "//
                        + "WHERE " + "{p:" + QuestionModel.CODE + "}=?code ";
        final FlexibleSearchQuery query = new FlexibleSearchQuery(queryString);
        query.addQueryParameter("code", code);
        return flexibleSearchService.<QuestionModel> search(query).getResult();
    }

    @Override
    public List<QuestionModel> getQuestions() {
        final String queryString = //
                "SELECT {p:" + QuestionModel.PK + "} "
                        + "FROM {" + QuestionModel._TYPECODE + " AS p} ";
        final FlexibleSearchQuery query = new FlexibleSearchQuery(queryString);
        return flexibleSearchService.<QuestionModel> search(query).getResult();
    }
}
