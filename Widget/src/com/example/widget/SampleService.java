package com.example.widget;

import android.app.PendingIntent;
import android.app.Service;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.Toast;

public class SampleService extends Service {
	private final String TAG = SampleService.class.getSimpleName();
	private final String BUTTON_CLICK_ACTION = "BUTTON_CLICK_ACTION";
	private final String BUTTON_CLICK_ACTION2 = "BUTTON_CLICK_ACTION2";
	
	@Override
	public void onStart(Intent intent, int startId) {
		super.onStart(intent, startId);
		Log.v(TAG, "onStart");
		
		// RemoteViews is used with widget. This can be displayed in another process.
		RemoteViews remoteViews = new RemoteViews(getPackageName(), R.layout.main);
		// Prepare Intent which is sent when button is clicked
		Intent buttonIntent = new Intent();

		// If this Intent is issued when button is clicked
		if(BUTTON_CLICK_ACTION.equals(intent.getAction())) {
			buttonIntent.setAction(BUTTON_CLICK_ACTION2);
			remoteViews.setTextViewText(R.id.text, "Push Button");
		}
		else {
			buttonIntent.setAction(BUTTON_CLICK_ACTION);
			remoteViews.setTextViewText(R.id.text, "WidgetSample");
		}
		
		// Handle this Intent as PendingIntent
		PendingIntent pendingIntent = PendingIntent.getService(this, 0, buttonIntent, 0);
		remoteViews.setOnClickPendingIntent(R.id.button, pendingIntent);
		Log.v(TAG, "set PendingIntent here");
		Toast.makeText(this, "set PendingIntent", Toast.LENGTH_SHORT).show();
		
		// Update AppWidget display
		ComponentName thisWidget = new ComponentName(this, WidgetSample.class);
		AppWidgetManager manager = AppWidgetManager.getInstance(this);
		manager.updateAppWidget(thisWidget, remoteViews);
	}

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

}
