package com.training.facades.populators;

import com.training.facades.product.data.QuestionData;
import com.training.question.model.QuestionModel;
import de.hybris.platform.commercefacades.product.data.ProductData;
import de.hybris.platform.converters.Populator;
import de.hybris.platform.core.model.product.ProductModel;
import de.hybris.platform.servicelayer.dto.converter.ConversionException;
import org.springframework.beans.factory.annotation.Required;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class QuestionProductDataPopulator implements Populator<ProductModel, ProductData> {
    private QuestionDataConverter questionDataConverter;
    @Required
    public void setQuestionDataConverter(QuestionDataConverter questionDataConverter) {
        this.questionDataConverter = questionDataConverter;
    }
    @Override
    public void populate(ProductModel productModel, ProductData productData) throws ConversionException {
        Set<QuestionModel> questionModels = productModel.getQuestions();
        List<QuestionData> questions = new ArrayList<>();
        questionModels.forEach(questionModel -> questions.add(questionDataConverter.convert(questionModel)));
        productData.setQuestions(questions);
    }
}
