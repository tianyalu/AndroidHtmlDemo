package com.sty.android.html;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * 参考：https://blog.csdn.net/fenggit/article/details/51028300
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private WebView webView;
    private Button btnInvokeJs;
    private Button btnInvokeJs2;
    private EditText etInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
    }

    @SuppressLint("JavascriptInterface")
    private void initView(){
        webView = findViewById(R.id.web_view);
        btnInvokeJs = findViewById(R.id.btn_invoke_js);
        btnInvokeJs2 = findViewById(R.id.btn_invoke_js2);
        etInput = findViewById(R.id.et_input);

        btnInvokeJs.setOnClickListener(this);
        btnInvokeJs2.setOnClickListener(this);

        webView.setVerticalScrollbarOverlay(true);
        //设置webView支持JavaScript
        webView.getSettings().setJavaScriptEnabled(true);

        //加载本地网页
        webView.loadUrl("file:///android_asset/index.html");

        //在JS中调用本地Java方法
        webView.addJavascriptInterface(new JsInterface(this), "AndroidWebView");

        //添加客户端支持
        webView.setWebChromeClient(new WebChromeClient());
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_invoke_js:
                String msg = etInput.getText().toString().trim();
                //调用JS中的函数：showInfoFromApp(msg)
                webView.loadUrl("javascript:showInfoFromApp2('" + msg + "')");
                break;
            case R.id.btn_invoke_js2:
                //调用JS中的函数：showInfoFromApp()
                webView.loadUrl("javascript:showInfoFromApp()");
                break;
            default:
                break;
        }
    }

    class JsInterface{
        private Context mContext;

        public JsInterface(Context context){
            this.mContext = context;
        }

        /**
         * 在js中调用window.AndroidWebView.showInfoFromJs(name)，便会触发此方法。
         * 此方法名称一定要和js中showInfoFromJava方法一样
         * @param name
         */
        @JavascriptInterface
        public void showInfoFromJs(String name){
            etInput.setText(name);
            Toast.makeText(mContext, "来自JS的信息：" + name, Toast.LENGTH_SHORT).show();
        }

        @JavascriptInterface
        public void showInfoFromJs(){
            Toast.makeText(mContext, "JS调用APP方法", Toast.LENGTH_SHORT).show();
        }
    }
}
