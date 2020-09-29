package com.training.question.controllers.cms;

import com.training.core.model.QuestionCMSComponentModel;
import com.training.question.controllers.QuestionControllerConstants;
import de.hybris.platform.addonsupport.controllers.cms.AbstractCMSAddOnComponentController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller("QuestionCMSComponentController")
@RequestMapping(value = QuestionControllerConstants.Actions.Cms.QuestionCMSComponent)
public class QuestionsCMSComponentController extends AbstractCMSAddOnComponentController<QuestionCMSComponentModel> {
    @Override
    protected void fillModel(HttpServletRequest request, Model model, QuestionCMSComponentModel component) {
        model.addAttribute("numberOfQuestionsToShow", component.getNumberOfQuestionsToShow());
        model.addAttribute("fontSize", component.getFontSize());
    }
}
