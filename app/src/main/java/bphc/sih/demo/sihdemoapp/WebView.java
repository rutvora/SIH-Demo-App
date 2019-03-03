package bphc.sih.demo.sihdemoapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import java.lang.ref.WeakReference;
import java.util.Arrays;

import bphc.sih.demo.sihdemoapp.Helpers.JavaScriptInterface;
import bphc.sih.demo.sihdemoapp.Helpers.MQTT;

public class WebView extends AppCompatActivity implements View.OnClickListener {
    public static WeakReference<android.webkit.WebView> myWebView;     //TODO: Remove this after proper implementation of calling JS functions
    MQTT mqtt;


    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Setup SharedPrefs
        SharedPreferences sharedPreferences = getSharedPreferences("Default", Context.MODE_PRIVATE);

        setContentView(R.layout.activity_web_view);

        //Maps Activity
//        Intent intent = new Intent(this, MapsActivity.class);
//        startActivity(intent);

        //GUI Webview
        String url;
        if (sharedPreferences.getString("lang", "lang").equals("lang"))
            url = "file:///android_asset/WebGUI/lang.html";
        else url = "file:///android_asset/WebGUI/index.html";
        android.webkit.WebView webView = findViewById(R.id.webview);
        webView.setOnClickListener(this);
        webView.getSettings().setJavaScriptEnabled(true);
        final JavaScriptInterface javaScriptInterface = new JavaScriptInterface(this);
        webView.addJavascriptInterface(javaScriptInterface, "JSInterface");


        javaScriptInterface.data = getIntent().getStringArrayExtra("data");
        if (javaScriptInterface.data != null) {
            Log.w("Data webview", Arrays.toString(javaScriptInterface.data));

        }
        webView.loadUrl(url);
        myWebView = new WeakReference<>(webView);

        //MQTT Setup
        mqtt = new MQTT(this);


        //WifiDirect Setup
//        Scheduler.scheduleJobCheckMessages(this);
//        WiFiDirectService wiFiDirectService = new WiFiDirectService(this);
//        wiFiDirectService.setup();
    }

    @Override
    public void onClick(View view) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mqtt.disconnect();
    }

//    public void putSharedPreference(String key, String value) {
//        SharedPreferences.Editor editor = sharedPreferences.edit();
//        editor.putString(key, value);
//        editor.apply();
//    }
}
