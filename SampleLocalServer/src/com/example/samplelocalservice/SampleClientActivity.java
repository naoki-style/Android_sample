package com.example.samplelocalservice;

import com.example.samplelocalservice.R;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class SampleClientActivity extends Activity {
	private final String TAG = SampleClientActivity.class.getSimpleName();
	private LocalService mLocalService;
	private boolean mIsBound;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
		Button button = (Button) findViewById(R.id.StartButton);
		button.setOnClickListener(buttonListener);
		
		button = (Button) findViewById(R.id.StopButton);
		button.setOnClickListener(buttonListener);
	}
	
	private ServiceConnection mConnection = new ServiceConnection() {
		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {
			Log.v(TAG, "onServiceConnected");
			mLocalService = ((LocalService.LocalServiceBinder)service).getService();
		}
		
		@Override
		public void onServiceDisconnected(ComponentName name) {
			Log.v(TAG, "onServiceDisconnected");
			mLocalService = null;
		}
	};
	
	void doBindService() {
		Log.v(TAG, "doBindService");
		bindService(new Intent(SampleClientActivity.this, LocalService.class), mConnection, Context.BIND_AUTO_CREATE);
		mIsBound = true;
	}
	
	void doUnbindService() {
		Log.v(TAG, "doUnbindService");
		if(mIsBound) {
			unbindService(mConnection);
			mIsBound = false;
		}
	}
	
	private OnClickListener buttonListener = new OnClickListener() {
		@Override
		public void onClick(View view) {
			Log.v(TAG, "onClick id:" + view.getId());
			switch(view.getId()) {
			case R.id.StartButton:
				startService(new Intent(SampleClientActivity.this, LocalService.class));
				break;
			case R.id.StopButton:
				stopService(new Intent(SampleClientActivity.this, LocalService.class));
				break;
			case R.id.BindButton:
				doBindService();
				break;
			case R.id.UnbindButton:
				doUnbindService();
				break;
			}
		}
	};

}
