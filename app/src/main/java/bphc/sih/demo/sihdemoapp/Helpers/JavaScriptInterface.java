package bphc.sih.demo.sihdemoapp.Helpers;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.webkit.JavascriptInterface;

import bphc.sih.demo.sihdemoapp.Helpers.Decoders.DisasterManagement;
import bphc.sih.demo.sihdemoapp.Helpers.Decoders.WeatherWarning;

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
    }

    @JavascriptInterface
    public void onLoad() {
        if (data != null) {
            if (data.length == 7) {
                Log.w("Calling Weather", "Yup!");
                WeatherWarning.updateGUI(data[0], data[1], data[2], data[3], data[4], data[5], data[6]);
            } else {
                Log.w("Calling Disaster", "Yup!");
                DisasterManagement.updateGUI(data[0], data[1], data[2], data[3], data[4], data[5], data[6], data[7]);
            }
        }
    }
}
