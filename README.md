# AndroidHtmlDemo
#####这是Android源码和HTML中JS相互交互的简单demo，主要是实现两者之间方法的相互调用。
1. Android调用JS：webView.loadUrl("javascript:callBackMethodInJS()");
2. JS调用Android：window.AndroidWebView.callBackMethodInAndroid();  
两者之间的相互调用需要如下设置：  
``` 
   webView.setVerticalScrollbarOverlay(true);
   //设置webView支持JavaScript
   webView.getSettings().setJavaScriptEnabled(true);
   //加载本地网页
   webView.loadUrl("file:///android_asset/index.html");
   //在JS中调用本地Java方法
   webView.addJavascriptInterface(new JsInterface(this), "AndroidWebView");
   //添加客户端支持
   webView.setWebChromeClient(new WebChromeClient());
```
展示效果如下图所示：  
![image](https://github.com/tianyalu/AndroidHtmlDemo/blob/master/screenshot/show.gif)

#####新增：增加SecondActivity,展示两个坑（未在截图中展示）  
1. HTML中`<button onclick="click('hello')">click me</button>` 名为“click()”的方法点击事件不响应，但是修改其他名称就可以，具体原因尚不清楚，若有大神指导请不吝赐教；  
2. JS调Android的修改UI的方法应放在主线程中执行【巨坑】。（例外情况：Toast方法可以在JavaBridge线程中执行，原因不明）。代码如下：  
```
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
```