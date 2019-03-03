package bphc.sih.demo.sihdemoapp.Helpers;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.util.Pair;
import android.webkit.JavascriptInterface;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

import bphc.sih.demo.sihdemoapp.Helpers.Decoders.DisasterManagement;
import bphc.sih.demo.sihdemoapp.Helpers.Decoders.Hotspots;
import bphc.sih.demo.sihdemoapp.Helpers.Decoders.WeatherWarning;
import bphc.sih.demo.sihdemoapp.MapsActivity;
import bphc.sih.demo.sihdemoapp.WebView;

public class JavaScriptInterface {
    private Context context;
    public String[] data = null;

    public JavaScriptInterface(Context context) {
        this.context = context;

    }

    @JavascriptInterface
    public void log(String message) {
        Log.w("JSInterface", message);
    }

    @JavascriptInterface
    public void test() {
        Log.w("JS", "Now trying to evaluate");
    }

    @JavascriptInterface
    public void setLang(String lang) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("Default", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("lang", lang);
        editor.apply();

        WebView.myWebView.get().post(new Runnable() {
            @Override
            public void run() {
                WebView.myWebView.get().loadUrl("file:///android_asset/WebGUI/index.html");
            }
        });
    }

    @JavascriptInterface
    public void onLoad() {
        if (data != null) {
            if (data.length == 7) {
                Log.w("Calling Weather", "Yup!");
                WeatherWarning.updateGUI(data[0], data[1], data[2], data[3], data[4], data[5], data[6]);
            } else {
                Log.w("Calling Disaster", "Yup!");
                DisasterManagement.updateGUI(data[0], data[1], data[2], data[3], data[4], data[5], data[6], data[7], data[8]);
            }
        }
    }

    @JavascriptInterface
    public void openMap(String message) {
        Log.w("openMap", "Trying");

        Intent intent = new Intent(context, MapsActivity.class);
        Pair<ArrayList<LatLng>, ArrayList<String>> hotspots = Hotspots.decode(context, message.substring(120, 212)
                + "00000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000");
        intent.putExtra("locations", hotspots.first);
        intent.putExtra("amenities", hotspots.second);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        context.startActivity(intent);

    }
}
