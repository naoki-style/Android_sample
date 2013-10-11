package com.example.samplelocalservice;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

public class LocalService extends Service {
	private final String TAG = LocalService.class.getSimpleName();
	
	@Override
	public void onCreate() {
		Log.v(TAG, "onCreate()");
		super.onCreate();
		Toast.makeText(getBaseContext(), "onCreate()", Toast.LENGTH_SHORT).show();
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		Log.v(TAG, "onStartCommand");
		Toast.makeText(getBaseContext(), "onStartCommand()", Toast.LENGTH_SHORT).show();
		return super.onStartCommand(intent, flags, startId);
		
	}
	
	public class LocalServiceBinder extends Binder {
		LocalService getService() {
			return LocalService.this;
		}
	}
	
	private final IBinder mBinder = new LocalServiceBinder();
	
	@Override
	public IBinder onBind(Intent intent) {
		Log.v(TAG, "onBind()");
		Toast.makeText(getBaseContext(), "onBind()", Toast.LENGTH_SHORT).show();
		return mBinder;
	}
	
	@Override
	public void onRebind(Intent intent) {
		Log.v(TAG, "onRebind()");
		Toast.makeText(getBaseContext(), "onRebind()", Toast.LENGTH_SHORT).show();
		super.onRebind(intent);
	}
	
	@Override
	public boolean onUnbind(Intent intent) {
		Log.v(TAG, "onUnbind()");
		Toast.makeText(getBaseContext(), "onUnbind()", Toast.LENGTH_SHORT).show();
		return super.onUnbind(intent);
	}

}
