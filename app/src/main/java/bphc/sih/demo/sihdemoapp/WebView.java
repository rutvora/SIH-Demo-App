package bphc.sih.demo.sihdemoapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import bphc.sih.demo.sihdemoapp.Helpers.JavaScriptInterface;
import bphc.sih.demo.sihdemoapp.Helpers.MQTT;

public class WebView extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);

        android.webkit.WebView myWebView = findViewById(R.id.webview);
        myWebView.setOnClickListener(this);
        myWebView.getSettings().setJavaScriptEnabled(true);
        myWebView.addJavascriptInterface(new JavaScriptInterface(this), "JSInterface");

        myWebView.loadUrl("file:///android_asset/WebGUI/index.html");

        new MQTT(this);

//        Scheduler.scheduleJobCheckMessages(this);
//        WiFiDirectService wiFiDirectService = new WiFiDirectService(this);
//        wiFiDirectService.setup();
    }

    @Override
    public void onClick(View view) {

    }
}
