package com.training.question.context;

import com.training.core.model.process.QuestionsEmailProcessModel;
import com.training.facades.populators.QuestionDataConverter;
import com.training.facades.process.email.context.CustomerEmailContext;
import com.training.facades.product.data.QuestionData;
import com.training.question.model.QuestionModel;
import de.hybris.platform.acceleratorservices.model.cms2.pages.EmailPageModel;
import de.hybris.platform.commerceservices.model.process.StoreFrontCustomerProcessModel;
import org.springframework.beans.factory.annotation.Required;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class QuestionsEmailContext extends CustomerEmailContext {

    private Map<String, List<QuestionData>> questionsMap;

    private QuestionDataConverter questionDataConverter;

    @Override
    public void init(final StoreFrontCustomerProcessModel processModel, final EmailPageModel emailPageModel)
    {
        super.init(processModel, emailPageModel);
        if (processModel instanceof QuestionsEmailProcessModel)
        {
            QuestionsEmailProcessModel questionsEmailProcessModel = (QuestionsEmailProcessModel) processModel;
            setQuestionsMap(convert(questionsEmailProcessModel.getQuestionsMap()));
            String emailReceiver = questionsEmailProcessModel.getEmailReceiver();
            String displayName = questionsEmailProcessModel.getEmailReceiver();

            put(DISPLAY_NAME, displayName);
            put(EMAIL, emailReceiver);
            customerData = getCustomerConverter().convert(getCustomer(processModel));
        }
    }

    private Map<String, List<QuestionData>> convert(Map<String, List<QuestionModel>> questionModelsMap) {
        return questionModelsMap.entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey,
                                          e -> e.getValue().stream()
                                                           .map(questionModel -> questionDataConverter.convert(questionModel))
                                                           .collect(Collectors.toList())));
    }
    public Map<String, List<QuestionData>> getQuestionsMap() {
        return questionsMap;
    }

    public void setQuestionsMap(Map<String, List<QuestionData>> questionsMap) {
        this.questionsMap = questionsMap;
    }

    @Required
    public void setQuestionDataConverter(QuestionDataConverter questionDataConverter) {
        this.questionDataConverter = questionDataConverter;
    }
}
