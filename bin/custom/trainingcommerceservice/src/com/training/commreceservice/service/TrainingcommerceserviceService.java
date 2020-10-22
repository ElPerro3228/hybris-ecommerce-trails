/*
 * Copyright (c) 2019 SAP SE or an SAP affiliate company. All rights reserved.
 */
package com.training.commreceservice.service;

public interface TrainingcommerceserviceService
{
	String getHybrisLogoUrl(String logoCode);

	void createLogo(String logoCode);
}
