package com.sty.android.html;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SecondActivity extends AppCompatActivity{
    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        initView();
    }

    @SuppressLint("JavascriptInterface")
    private void initView(){
        webView = findViewById(R.id.web_view);

        webView.setVerticalScrollbarOverlay(true);
        //设置webView支持JavaScript
        webView.getSettings().setJavaScriptEnabled(true);

        //加载本地网页
        webView.loadUrl("file:///android_asset/q_index.html");

        //在JS中调用本地Java方法
        webView.addJavascriptInterface(new JsInterface(this), "msg");

        //添加客户端支持
        webView.setWebChromeClient(new WebChromeClient());
    }

    class JsInterface{
        private Context mContext;

        public JsInterface(Context context){
            this.mContext = context;
        }

        /**
         * 在js中调用window.msg.msg(name)，便会触发此方法。
         * 此方法名称一定要和msg方法一样
         * @param name
         */
        @JavascriptInterface
        public void msg(final String name){
            Log.i("sty", "SecondActivity-msg: " + Thread.currentThread().getName());
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Log.i("sty", "SecondActivity-msg-runnable: " + Thread.currentThread().getName());
                    webView.loadUrl("javascript:setText('" + name + "')");
                }
            });

        }

    }
}
