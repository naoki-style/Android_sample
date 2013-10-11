package com.example.samplewebview;

import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

public class SampleWebview extends Activity {
  final private String TAG = "SampleWebView";
  private WebView webview;
  private ProgressDialog loading;
  private ProgressBar bar;
  
  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    // Set Progress Bar on Title
    requestWindowFeature(Window.FEATURE_PROGRESS);
    requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
    setContentView(R.layout.activity_sample_webview);
    
    // Set Main View
    final Button button = (Button)findViewById(R.id.button1);
    webview = (WebView)findViewById(R.id.webView);
    
    // Set Configuration
    webview.getSettings().setJavaScriptEnabled(true);
    webview.getSettings().setBuiltInZoomControls(true);
//    webview.getSettings().setPluginsEnabled(true);
//    webview.getSettings().setUserAgent("");
    webview.getSettings().setLoadWithOverviewMode(true);
    
    final OnClickListener listener = new OnClickListener() {
      @Override
      public void onClick(View v) {
        Log.e(TAG, ">> OnClick()");
        final EditText edit = (EditText)findViewById(R.id.editText1);
        String url = edit.getText().toString();
        Log.e(TAG, "URL : " + url);
        getWebView().loadUrl(url);
        getWebView().requestFocus();
        Log.e(TAG, "<< OnClick()");
      }
    };
    button.setOnClickListener(listener);
    
    // Set behaviour
    loading = new ProgressDialog(this) {
      public void onBackPressed() {
        Log.e(TAG, ">> onBackPressed()");
        webview.stopLoading();
        webview.goBack();
        cancel();
        Log.e(TAG, "<< onBackPressed()");
      }
    };
    loading.setProgressStyle(ProgressDialog.STYLE_SPINNER);
    
    bar = (ProgressBar)findViewById(R.id.progressBar1);
    webview.setWebViewClient(new WebViewClient() {
      @Override
      public boolean shouldOverrideUrlLoading(WebView view, String url) {
        Log.e(TAG, ">> shouldOverrideUrlLoading()");
        if(url.startsWith("http://www.yahoo.co.jp/") || url.startsWith("http://m.yahoo.co.jp/")) {
          Log.e(TAG, "<< shouldOverrideUrlLoading() if");
          // Launch default browser if URL starts with yahoo
          Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
          startActivity(intent);
          return true;
        }
        else {
          Log.e(TAG, "<< shouldOverrideUrlLoading() else");
          // Continue to use WebView
          view.loadUrl(url);
          return false;
        }
      }
      
      @Override
      public void onPageFinished(final WebView view, final String url) {
        Log.e(TAG, ">> onPageFinished()");
        if(loading.isShowing()) {
          Log.e(TAG, "-- onPageFinished() dismiss");
          // If Progress Dialog is being displayed, dismiss it
          loading.dismiss();
        }
//        bar.setVisibility(View.INVISIBLE);
        // If Progress Bar is being displayed, make it invisible
        bar.setVisibility(View.GONE);
        Log.e(TAG, "<< onPageFinished()");
      }
      
      @Override
      public void onPageStarted(WebView view, String url, android.graphics.Bitmap favicon) {
        Log.e(TAG, ">> onPageStarted()");
        // Start displaying Progress Dialog and Bar
        loading.show();
        bar.setVisibility(View.VISIBLE);
        Log.e(TAG, "<< onPageStarted()");
      }
    });
    webview.setWebChromeClient(new WebChromeClient() {
      @Override
      public void onProgressChanged(WebView view, int progress) {
        Log.e(TAG, ">> onProgressChanged()");
        // Update Progress Dialog and Bar
        loading.setProgress(progress);
        bar.setProgress(progress);
        Log.e(TAG, "<< onProgressChanged()");
      }
    });
    webview.addJavascriptInterface(new JavaScriptInterface(this), "Android");
    
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    Log.e(TAG, ">> onCreateOptionsMenu()");
    getMenuInflater().inflate(R.menu.activity_sample_webview, menu);
    Log.e(TAG, "<< onCreateOptionsMenu()");
    return true;
  }
  
  protected WebView getWebView() {
    Log.e(TAG, ">> getWebView()");
    return webview;
  }
  
  private class JavaScriptInterface {
	  Context mContext;
	  
	  JavaScriptInterface(Context c) {
		  mContext = c;
	  }
	  
	  public void showToast(String str) {
		  Toast.makeText(mContext, str, Toast.LENGTH_SHORT).show();
	  }
  }
}
