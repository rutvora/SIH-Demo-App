package bphc.sih.demo.sihdemoapp.Helpers;

import android.content.Context;
import android.util.Log;
import android.webkit.JavascriptInterface;

public class JavaScriptInterface {
    private Context context;

    public JavaScriptInterface(Context context) {
        this.context = context;

    }

    @JavascriptInterface
    public void log(String message) {
        Log.w("JSInterface", message);
    }
}
