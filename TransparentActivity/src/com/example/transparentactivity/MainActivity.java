package com.example.transparentactivity;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.util.Log;
import android.view.Menu;
import android.widget.Toast;

public class MainActivity extends Activity {
	private static final String TAG = MainActivity.class.getSimpleName();
	private static final int DIALOG_ID1 = 1;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showDialog(DIALOG_ID1);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
    
    @Override
    public Dialog onCreateDialog(int id) {
    	Log.v(TAG, "onCreateDialog(" + id + ")");
    	switch(id) {
    	case DIALOG_ID1:
    		return new AlertDialog.Builder(this).
    				setMessage("You can see idle screen behind this.").
    				setPositiveButton("Toast", new DialogInterface.OnClickListener() {
    					@Override
    					public void onClick(DialogInterface dialog, int which) {
    						Log.v(TAG, "onClick() from Toast");
    						dialog.dismiss();
    						Toast.makeText(getBaseContext(), "You cannot click menu button on idle screen", Toast.LENGTH_LONG).show();
    					}
					}).setNegativeButton("Finish", new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
    						Log.v(TAG, "onClick() from Finish");
							dialog.dismiss();
    						Toast.makeText(getBaseContext(), "finish Activity", Toast.LENGTH_SHORT).show();
							finish();							
						}
					}).
    				create();
    	}
    	return null;
    }
    
    @Override
    protected void onPrepareDialog(int id, Dialog dialog) {
    	Log.v(TAG, "onPrepareDialog");
    	super.onPrepareDialog(id, dialog);
    }

}
