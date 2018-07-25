# AndroidHtmlDemo
这是Android源码和HTML中JS相互交互的简单demo，主要是实现两者之间方法的相互调用。
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
![image](https://github.com/tianyalu/AndroidHtmlDemo/bolb/master/screenshot/show.gif)