/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.analytics.client.android.impl;

import com.liferay.analytics.model.IdentityContextMessage;

import junit.framework.Assert;

import org.junit.Test;

/**
 * @author Igor Matos
 * @author Allan Melo
 */
public class IdentityClientImplTest {

	@Test
	public void getUserId() throws Exception {
		IdentityContextMessage.Builder identityContextMessageBuilder =
			IdentityContextMessage.builder("liferay.com");

		identityContextMessageBuilder.dataSourceIdentifier("Liferay");
		identityContextMessageBuilder.dataSourceIndividualIdentifier("12345");
		identityContextMessageBuilder.domain("liferay.com");
		identityContextMessageBuilder.language("en-US");
		identityContextMessageBuilder.protocolVersion("1.0");

		identityContextMessageBuilder.identityFieldsProperty("email", "joe.blogss@liferay.com");
		identityContextMessageBuilder.identityFieldsProperty( "name", "Joe Bloggs");

		IdentityClientImpl identityClientImpl = new IdentityClientImpl();

		String userId = identityClientImpl.getUserId(identityContextMessageBuilder.build());

		Assert.assertTrue(userId != null && userId.length() > 1);
	}


}
