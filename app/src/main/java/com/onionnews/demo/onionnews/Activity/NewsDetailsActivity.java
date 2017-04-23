package com.onionnews.demo.onionnews.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.onionnews.demo.onionnews.R;

public class NewsDetailsActivity extends AppCompatActivity {
    private WebView wv_headline_details;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_details);
        initView();
    }

    private void initView() {
        wv_headline_details = (WebView) findViewById(R.id.wv_headline_details);
        Intent intent = getIntent();
        String url = intent.getStringExtra("url_item");
        //WebView加载web资源
        wv_headline_details.loadUrl(url);
        //覆盖WebView默认使用第三方或系统默认浏览器打开网页的行为，使网页用WebView打开
        wv_headline_details.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
//                return super.shouldOverrideUrlLoading(view, url);
                //返回值是true的时候控制去WebView打开，为false调用系统浏览器或第三方浏览器
                view.loadUrl(url);
                return true;
            }
        });
        //优先使用缓存
        wv_headline_details.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        wv_headline_details.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
//                super.onProgressChanged(view, newProgress);
                if (newProgress == 100){
                    // 网页加载完成

                }else {
                    // 加载中
//                    Toast.makeText(NewsDetailsActivity.this,"拼命加载中...",Toast.LENGTH_SHORT).show();

                }
            }
        });
    }
}
