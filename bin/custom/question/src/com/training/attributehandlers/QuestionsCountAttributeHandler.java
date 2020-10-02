package com.training.attributehandlers;

import com.training.question.model.QuestionModel;
import de.hybris.platform.core.model.product.ProductModel;
import de.hybris.platform.servicelayer.model.attribute.AbstractDynamicAttributeHandler;

import java.util.Set;

public class QuestionsCountAttributeHandler extends AbstractDynamicAttributeHandler<Integer, ProductModel> {
    @Override
    public Integer get(final ProductModel model) {
        Set<QuestionModel> questionModels = model.getQuestions();
        if (questionModels == null) {
            return 0;
        }
        Integer questionsCount = questionModels.size();
        return questionsCount;
    }
}
