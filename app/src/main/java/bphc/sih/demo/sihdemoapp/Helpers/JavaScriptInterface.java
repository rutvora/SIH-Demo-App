package bphc.sih.demo.sihdemoapp.Helpers;

import android.content.Context;
import android.util.Log;
import android.webkit.JavascriptInterface;

import bphc.sih.demo.sihdemoapp.WebView;

public class JavaScriptInterface {
    private Context context;

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
        WebView.myWebView.post(new Runnable() {
            @Override
            public void run() {
                WebView.myWebView.evaluateJavascript("javascript:testEcho(\"Hello World!\")", null);
            }
        });

    }
}
