/*
 * Copyright (c) 2019 SAP SE or an SAP affiliate company. All rights reserved.
 */
package com.training.commreceservice.setup;

import static com.training.commreceservice.constants.TrainingcommerceserviceConstants.PLATFORM_LOGO_CODE;

import de.hybris.platform.core.initialization.SystemSetup;

import java.io.InputStream;

import com.training.commreceservice.constants.TrainingcommerceserviceConstants;
import com.training.commreceservice.service.TrainingcommerceserviceService;


@SystemSetup(extension = TrainingcommerceserviceConstants.EXTENSIONNAME)
public class TrainingcommerceserviceSystemSetup
{
	private final TrainingcommerceserviceService trainingcommerceserviceService;

	public TrainingcommerceserviceSystemSetup(final TrainingcommerceserviceService trainingcommerceserviceService)
	{
		this.trainingcommerceserviceService = trainingcommerceserviceService;
	}

	@SystemSetup(process = SystemSetup.Process.INIT, type = SystemSetup.Type.ESSENTIAL)
	public void createEssentialData()
	{
		trainingcommerceserviceService.createLogo(PLATFORM_LOGO_CODE);
	}

	private InputStream getImageStream()
	{
		return TrainingcommerceserviceSystemSetup.class.getResourceAsStream("/trainingcommerceservice/sap-hybris-platform.png");
	}
}
