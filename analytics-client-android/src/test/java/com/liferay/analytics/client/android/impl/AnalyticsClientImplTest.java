package com.liferay.analytics.client.android.impl;

import com.google.gson.reflect.TypeToken;
import com.liferay.analytics.client.android.transport.AnalyticsEventsMessageModel;
import com.liferay.analytics.client.android.util.JSONParser;
import com.liferay.analytics.model.AnalyticsEventsMessage;

import junit.framework.Assert;

import org.junit.Test;
import org.mockito.Mockito;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * @author Igor Matos
 * @author Allan Melo
 */
public class AnalyticsClientImplTest {

	@Test
	public void sendAnalytics() throws Exception {
		String userId = _getUserId();

		AnalyticsEventsMessage.Builder analyticsEventsMessageBuilder = AnalyticsEventsMessage.builder("liferay.com", userId);

		analyticsEventsMessageBuilder.contextProperty("languageId", "pt_PT");
		analyticsEventsMessageBuilder.contextProperty("url", "http://192.168.108.90:8081/");

		AnalyticsEventsMessage.Event.Builder eventBuilder = AnalyticsEventsMessage.Event.builder("ApplicationId", "View");

		eventBuilder.property("elementId", "banner1");

		analyticsEventsMessageBuilder.event(eventBuilder.build());
		analyticsEventsMessageBuilder.protocolVersion("1.0");
		AnalyticsClientImpl analyticsClientImpl = Mockito.spy(AnalyticsClientImpl.class);

		Mockito.when(analyticsClientImpl.getAnalyticsGatewayHost()).thenReturn("192.168.108.90");
		Mockito.when(analyticsClientImpl.getAnalyticsGatewayProtocol()).thenReturn("http");
		Mockito.when(analyticsClientImpl.getAnalyticsGatewayPort()).thenReturn("8081");
		Mockito.when(analyticsClientImpl.getAnalyticsGatewayPath()).thenReturn("/");

		analyticsClientImpl.sendAnalytics(analyticsEventsMessageBuilder.build());

		RequestBody body = RequestBody.create(MEDIA_TYPE, _getQuery(userId));

		Request request = new Request.Builder()
			.url(_CASSANDRA_URL)
			.post(body)
			.build();

		OkHttpClient client = new OkHttpClient();
		Response response = client.newCall(request).execute();
		String responseBody = response.body().string();

		Type listType = new TypeToken<ArrayList<AnalyticsEventsMessageModel>>() {
		}.getType();

		ArrayList<AnalyticsEventsMessageModel> list = JSONParser.fromJsonString(responseBody, listType);

		Assert.assertTrue(!list.isEmpty());

		AnalyticsEventsMessageModel model = list.get(0);
		Assert.assertEquals(userId, model.userId);
	}

	private String _getUserId() {
		String currentDate = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
		return "ANDROID" + currentDate;
	}

	private String _getQuery(String userId) {
		return "{\"keyspace\":\"analytics\", \"table\":\"analyticsevent\", \"conditions\" : [{\"name\":\"userId\",\"operator\":\"eq\", \"value\":\"" + userId + "\"}]}";
	}

	public static final MediaType MEDIA_TYPE = MediaType.parse("application/json; charset=utf-8");

	private static final String
		_CASSANDRA_URL = "http://192.168.108.90:9095/api/query/execute";
}