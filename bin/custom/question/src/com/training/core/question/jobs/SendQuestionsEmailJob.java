package com.training.core.question.jobs;

import com.training.core.model.process.QuestionsEmailProcessModel;
import com.training.core.question.cronjob.model.SendNewQuestionsCronJobModel;
import com.training.question.model.QuestionModel;
import com.training.question.service.QuestionService;
import de.hybris.platform.commerceservices.customer.CustomerService;
import de.hybris.platform.commerceservices.i18n.CommerceCommonI18NService;
import de.hybris.platform.cronjob.enums.CronJobResult;
import de.hybris.platform.cronjob.enums.CronJobStatus;
import de.hybris.platform.processengine.BusinessProcessService;
import de.hybris.platform.servicelayer.cronjob.AbstractJobPerformable;
import de.hybris.platform.servicelayer.cronjob.PerformResult;
import de.hybris.platform.servicelayer.time.TimeService;
import de.hybris.platform.site.BaseSiteService;
import de.hybris.platform.store.services.BaseStoreService;
import de.hybris.platform.util.Config;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Required;

import java.util.Date;
import java.util.List;
import java.util.Map;

public class SendQuestionsEmailJob extends AbstractJobPerformable<SendNewQuestionsCronJobModel> {

    private static final Logger LOG = Logger.getLogger(SendQuestionsEmailJob.class);

    private BusinessProcessService businessProcessService;
    private QuestionService questionService;
    private TimeService timeService;
    private BaseSiteService baseSiteService;
    private CommerceCommonI18NService commerceCommonI18NService;
    private BaseStoreService baseStoreService;
    private CustomerService customerService;

    @Override
    public PerformResult perform(SendNewQuestionsCronJobModel sendNewQuestionsCronJobModel) {
        try {
            Date lastPerformance = sendNewQuestionsCronJobModel.getLastPerformance();
            Date currentDate = timeService.getCurrentTime();
            String emailAddressReceiver = sendNewQuestionsCronJobModel.getEmailTo();

            Map<String, List<QuestionModel>> questionsMap = questionService
                    .getQuestionsGroupedByProductCodeHavingCreationTimeAfterLastPerformance(lastPerformance);

            sendNewQuestionsCronJobModel.setLastPerformance(currentDate);

            final QuestionsEmailProcessModel questionsEmailProcessModel = (QuestionsEmailProcessModel) businessProcessService
                    .createProcess("questionsEmailProcess" + "-" + System.currentTimeMillis(), "questionsEmailProcess");
            questionsEmailProcessModel.setEmailReceiver(emailAddressReceiver);
            questionsEmailProcessModel.setQuestionsMap(questionsMap);
            questionsEmailProcessModel.setSite(baseSiteService.getBaseSiteForUID("electronics"));
            questionsEmailProcessModel.setStore(baseStoreService.getBaseStoreForUid("electronics"));
            questionsEmailProcessModel.setLanguage(commerceCommonI18NService.getCurrentLanguage());
            questionsEmailProcessModel.setCustomer(customerService.getCustomerByCustomerId(
                    Config.getString(emailAddressReceiver, "stas.logvin.99@gmail.com")));
            modelService.save(questionsEmailProcessModel);
            businessProcessService.startProcess(questionsEmailProcessModel);
            modelService.save(sendNewQuestionsCronJobModel);
            LOG.info("Email with new questions was sent");
            return new PerformResult(CronJobResult.SUCCESS, CronJobStatus.FINISHED);
        } catch (Exception e) {
            LOG.error("Problem sending new email.", e);
            return new PerformResult(CronJobResult.FAILURE, CronJobStatus.FINISHED);
        }
    }

    @Required
    public void setBusinessProcessService(BusinessProcessService businessProcessService) {
        this.businessProcessService = businessProcessService;
    }

    @Required
    public void setQuestionService(QuestionService questionService) {
        this.questionService = questionService;
    }

    @Required
    public void setTimeService(TimeService timeService) {
        this.timeService = timeService;
    }

    @Required
    public void setBaseSiteService(BaseSiteService baseSiteService) {
        this.baseSiteService = baseSiteService;
    }

    @Required
    public void setCommerceCommonI18NService(CommerceCommonI18NService commerceCommonI18NService) {
        this.commerceCommonI18NService = commerceCommonI18NService;
    }

    @Required
    public void setBaseStoreService(BaseStoreService baseStoreService) {
        this.baseStoreService = baseStoreService;
    }

    @Required
    public void setCustomerService(CustomerService customerService) {
        this.customerService = customerService;
    }
}
