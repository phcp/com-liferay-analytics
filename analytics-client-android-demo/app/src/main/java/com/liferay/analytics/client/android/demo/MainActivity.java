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

package com.liferay.analytics.client.android.demo;

import android.os.Bundle;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.text.Editable;

import android.util.Log;

import android.view.Menu;
import android.view.MenuItem;

import android.widget.EditText;
import android.widget.TextView;

import com.liferay.analytics.client.android.impl.LiferayAnalytics;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * @author Igor Matos
 */
public class MainActivity extends AppCompatActivity {

	public void getUserId() {
		TextView userIdTV = findViewById(R.id.userId);

		Flowable flowable = Flowable.fromCallable(
			() -> {
				String userId = LiferayAnalytics.getUserId(
					"igor.matos@liferay.com", "igor", "liferay.com");

				Log.d("ANALYTICS-DEMO", userId);

				return userId;
			});

		flowable = flowable.subscribeOn(Schedulers.newThread());

		flowable = flowable.observeOn(AndroidSchedulers.mainThread());

		flowable.subscribe(userId -> userIdTV.setText((String)userId));
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.

		getMenuInflater().inflate(R.menu.menu_main, menu);

		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.

		int id = item.getItemId();

		//noinspection SimplifiableIfStatement

		if (id == R.id.action_settings) {
			return true;
		}

		return super.onOptionsItemSelected(item);
	}

	public void sendAnalytics(String eventId, String userId) {
		Flowable flowable = Flowable.fromCallable(
			() -> {
				LiferayAnalytics.sendAnalytics("liferay.com", userId, eventId);

				return Observable.empty();
			});

		flowable = flowable.subscribeOn(Schedulers.newThread());

		flowable = flowable.observeOn(AndroidSchedulers.mainThread());

		flowable.subscribe();
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Toolbar toolbar = findViewById(R.id.toolbar);

		setSupportActionBar(toolbar);

		EditText editText = findViewById(R.id.eventId);
		TextView userIdTV = findViewById(R.id.userId);

		getUserId();

		FloatingActionButton fab = findViewById(R.id.fab);

		fab.setOnClickListener(
			view -> {
				Editable eventIdEditable = editText.getText();
				CharSequence userIdCharSequence = userIdTV.getText();

				sendAnalytics(
					eventIdEditable.toString(), userIdCharSequence.toString());
			});
	}

}