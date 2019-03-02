package bphc.sih.demo.sihdemoapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import java.util.Set;

import bphc.sih.demo.sihdemoapp.Helpers.MQTT;

public class WebView extends AppCompatActivity implements View.OnClickListener {
    public static android.webkit.WebView myWebView;     //TODO: Remove this after proper implementation of calling JS functions
    private SharedPreferences sharedPreferences;


    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Setup SharedPrefs
        sharedPreferences = getPreferences(Context.MODE_PRIVATE);

        setContentView(R.layout.activity_web_view);

        //Maps Activity
//        Intent intent = new Intent(this, MapsActivity.class);
//        startActivity(intent);

        //GUI Webview
//        myWebView = findViewById(R.id.webview);
//        myWebView.setOnClickListener(this);
//        myWebView.getSettings().setJavaScriptEnabled(true);
//        myWebView.addJavascriptInterface(new JavaScriptInterface(this), "JSInterface");
//        myWebView.loadUrl("file:///android_asset/WebGUI/index.html");
//
        //MQTT Setup
        new MQTT(this);

        //Notification Test
//        NotificationHandler handler = new NotificationHandler(this);
//        handler.createNotificationChannel();
//        handler.notifyUser("sdfg","sfgds");

        //WifiDirect Setup
//        Scheduler.scheduleJobCheckMessages(this);
//        WiFiDirectService wiFiDirectService = new WiFiDirectService(this);
//        wiFiDirectService.setup();
    }

    @Override
    public void onClick(View view) {

    }

//    public void putSharedPreference(String key, String value) {
//        SharedPreferences.Editor editor = sharedPreferences.edit();
//        editor.putString(key, value);
//        editor.apply();
//    }

    public void putSharedPreference(String key, Set<String> values) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putStringSet(key, values);
        editor.apply();
    }

    public Set<String> getSharedPreference(String key) {
        return sharedPreferences.getStringSet(key, null);
    }
}
