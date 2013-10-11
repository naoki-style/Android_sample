package com.example.widget;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class WidgetSample extends AppWidgetProvider {
	private final String TAG = WidgetSample.class.getSimpleName();
	
	// LifeCycle of Widget is
	// 1st widget : onEnabled() -> onUpdate() ----> onDeleted()
	// 2nd widget : onUpdate() ----> onDeleted()
	// Once all the widget is closed --> onDisabled()
	// If you define update period, onUpdate() is called per its period.
	@Override
	public void onEnabled(Context context) {
		Log.v(TAG, "onEnabled");
		super.onEnabled(context);
	}
	
	@Override
	public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
		Log.v(TAG, "onUpdate");
		super.onUpdate(context, appWidgetManager, appWidgetIds);
		
		// Start Service
		Intent service = new Intent(context, SampleService.class);
		context.startService(service);
	}
	
	@Override
	public void onDeleted(Context context, int[] appWidgetIds) {
		Log.v(TAG, "onDelete");
		super.onDeleted(context, appWidgetIds);
	}
	
	@Override
	public void onReceive(Context context, Intent intent) {
		Log.v(TAG, "onReceive");
		super.onReceive(context, intent);
	}

}
