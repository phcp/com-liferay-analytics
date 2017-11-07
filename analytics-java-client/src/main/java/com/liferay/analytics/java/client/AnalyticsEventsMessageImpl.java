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

package com.liferay.analytics.java.client;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import com.liferay.analytics.model.AnalyticsEventsMessage;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Eduardo Garcia
 * @author Marcellus Tavares
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public final class AnalyticsEventsMessageImpl
	implements AnalyticsEventsMessage<AnalyticsEventsMessageImpl.Event>,
			Serializable {

	public static AnalyticsEventsMessageImpl.Builder builder(
		String analyticsKey, String userId) {

		return new AnalyticsEventsMessageImpl.Builder(analyticsKey, userId);
	}

	@Override
	public String getAnalyticsKey() {
		return _analyticsKey;
	}

	@Override
	public Map<String, String> getContext() {
		return Collections.unmodifiableMap(_context);
	}

	@Override
	public List<Event> getEvents() {
		return _events;
	}

	@Override
	public String getProtocolVersion() {
		return _protocolVersion;
	}

	@Override
	public String getUserId() {
		return _userId;
	}

	public static final class Builder {

		public AnalyticsEventsMessageImpl build() {
			if (_analyticsEventsMessage._events.size() == 0) {
				throw new IllegalStateException(
					"The message should contain at least one event");
			}

			return _analyticsEventsMessage;
		}

		public Builder context(Map<String, String> context) {
			_analyticsEventsMessage._context = context;

			return this;
		}

		public Builder contextProperty(String key, String value) {
			_analyticsEventsMessage._context.put(key, value);

			return this;
		}

		public Builder event(Event event) {
			_analyticsEventsMessage._events.add(event);

			return this;
		}

		public Builder protocolVersion(String protocolVersion) {
			_analyticsEventsMessage._protocolVersion = protocolVersion;

			return this;
		}

		protected Builder(String analyticsKey, String userId) {
			_analyticsEventsMessage._analyticsKey = analyticsKey;
			_analyticsEventsMessage._userId = userId;
		}

		private final AnalyticsEventsMessageImpl _analyticsEventsMessage =
			new AnalyticsEventsMessageImpl();

	}

	@JsonIgnoreProperties(ignoreUnknown = true)
	public static final class Event implements AnalyticsEventsMessage.Event {

		public static Event.Builder builder(
			String applicationId, String eventId) {

			return new Event.Builder(applicationId, eventId);
		}

		public String getApplicationId() {
			return _applicationId;
		}

		public String getEventId() {
			return _eventId;
		}

		public Map<String, String> getProperties() {
			return Collections.unmodifiableMap(_properties);
		}

		public static final class Builder {

			public AnalyticsEventsMessageImpl.Event build() {
				return _event;
			}

			public Event.Builder properties(Map<String, String> properties) {
				_event._properties = properties;

				return this;
			}

			public Event.Builder property(String key, String value) {
				_event._properties.put(key, value);

				return this;
			}

			protected Builder(String applicationId, String eventId) {
				_event._applicationId = applicationId;
				_event._eventId = eventId;
			}

			private final AnalyticsEventsMessageImpl.Event _event =
				new AnalyticsEventsMessageImpl.Event();

		}

		private Event() {
		}

		@JsonProperty("applicationId")
		private String _applicationId;

		@JsonProperty("eventId")
		private String _eventId;

		@JsonProperty("properties")
		private Map<String, String> _properties = new HashMap<>();

	}

	private AnalyticsEventsMessageImpl() {
	}

	@JsonProperty("analyticsKey")
	private String _analyticsKey;

	@JsonProperty("context")
	private Map<String, String> _context = new HashMap<>();

	@JsonProperty("events")
	private final List<Event> _events = new ArrayList<>();

	@JsonProperty("protocolVersion")
	private String _protocolVersion;

	@JsonProperty("userId")
	private String _userId;

}