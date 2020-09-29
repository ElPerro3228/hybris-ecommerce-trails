package com.training.facades.populators;

import com.training.facades.product.data.QuestionData;
import com.training.question.model.QuestionModel;
import de.hybris.platform.servicelayer.dto.converter.ConversionException;
import de.hybris.platform.servicelayer.dto.converter.Converter;

public class QuestionDataConverter implements Converter<QuestionModel, QuestionData> {

    @Override
    public QuestionData convert(QuestionModel questionModel) throws ConversionException {
        QuestionData questionData = new QuestionData();
        questionData.setQuestion(questionModel.getQuestion());
        questionData.setQuestionCustomer(questionModel.getQuestionCustomer().getContactEmail());
        questionData.setAnswer(questionModel.getAnswer());
        questionData.setAnswerCustomer(questionModel.getAnswerCustomer().getContactEmail());
        return questionData;
    }

    @Override
    public QuestionData convert(QuestionModel questionModel, QuestionData questionData) throws ConversionException {
        questionData.setQuestion(questionModel.getQuestion());
        questionData.setQuestionCustomer(questionModel.getQuestionCustomer().getContactEmail());
        questionData.setAnswer(questionModel.getAnswer());
        questionData.setAnswerCustomer(questionModel.getAnswerCustomer().getContactEmail());
        return questionData;
    }
}
