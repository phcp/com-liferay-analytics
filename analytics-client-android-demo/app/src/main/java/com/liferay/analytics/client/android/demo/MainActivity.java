package com.liferay.analytics.client.android.demo;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.liferay.analytics.client.android.impl.LiferayAnalytics;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

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
		fab.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				sendAnalytics(editText.getText().toString(), userIdTV.getText().toString());
			}
		});

	}

	public void getUserId() {
		TextView userIdTV = findViewById(R.id.userId);

		Flowable.fromCallable(() -> {
			String userId = LiferayAnalytics.getUserId("igor.matos@liferay.com", "igor", "liferay.com");
			Log.d("CAMPEAO", userId);
			return userId;
		}).subscribeOn(Schedulers.newThread())
			.observeOn(AndroidSchedulers.mainThread())
			.subscribe(userId -> {
				userIdTV.setText(userId);
			}, Throwable::printStackTrace);
	}

	public void sendAnalytics(String eventId, String userId) {
		Flowable.fromCallable(() -> {
			LiferayAnalytics.sendAnalytics("liferay.com", userId, eventId);
			return Observable.empty();
		}).subscribeOn(Schedulers.newThread())
			.observeOn(AndroidSchedulers.mainThread())
			.subscribe(System.out::println, Throwable::printStackTrace);
	}

//	public void sendAnalytics(String eventId) {
//		Flowable.fromCallable(() -> {
//			String userId = LiferayAnalytics.getUserId("igor.matos@liferay.com", "igor", "liferay.com");
//			Log.d("CAMPEAO", userId);
//			return userId;
//		}).doOnNext(userId -> {
//			LiferayAnalytics.sendAnalytics("liferay.com", userId, eventId);
//		}).subscribeOn(Schedulers.newThread())
//			.observeOn(AndroidSchedulers.mainThread())
//			.subscribe(System.out::println, Throwable::printStackTrace);
//	}

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
}
