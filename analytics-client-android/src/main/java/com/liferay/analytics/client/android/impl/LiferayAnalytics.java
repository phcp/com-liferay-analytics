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

import com.liferay.analytics.model.AnalyticsEventsMessage;
import com.liferay.analytics.model.IdentityContextMessage;

/**
 * @author Igor Matos
 */
public class LiferayAnalytics {

	public static String getUserId(
			String email, String name, String analyticsKey)
		throws Exception {

		IdentityContextMessage.Builder identityContextMessageBuilder =
			IdentityContextMessage.builder(analyticsKey);

		identityContextMessageBuilder.dataSourceIdentifier("Liferay");
		identityContextMessageBuilder.dataSourceIndividualIdentifier("12345");
		identityContextMessageBuilder.domain("liferay.com");
		identityContextMessageBuilder.language("en-US");
		identityContextMessageBuilder.protocolVersion("1.0");

		identityContextMessageBuilder.identityFieldsProperty("email", email);
		identityContextMessageBuilder.identityFieldsProperty("name", name);

		IdentityClientImpl identityClientImpl = new IdentityClientImpl();

		return identityClientImpl.getUserId(
			identityContextMessageBuilder.build());
	}

	public static void sendAnalytics(
			String analyticsKey, String userId, String eventId)
		throws Exception {

		AnalyticsEventsMessage.Builder analyticsEventsMessageBuilder =
			AnalyticsEventsMessage.builder(analyticsKey, userId);

		analyticsEventsMessageBuilder.contextProperty("languageId", "en_US");
		analyticsEventsMessageBuilder.contextProperty(
			"url", "http://www.liferay.com");

		AnalyticsEventsMessage.Event.Builder eventBuilder =
			AnalyticsEventsMessage.Event.builder("ApplicationId", eventId);

		eventBuilder.property("elementId", "fabAndroid");

		analyticsEventsMessageBuilder.event(eventBuilder.build());

		analyticsEventsMessageBuilder.protocolVersion("1.0");

		AnalyticsClientImpl analyticsClientImpl = new AnalyticsClientImpl();

		analyticsClientImpl.sendAnalytics(
			analyticsEventsMessageBuilder.build());
	}

}