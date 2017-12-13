package com.example.book_donation_system.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.example.book_donation_system.R;
import com.example.book_donation_system.unity.MessageEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

/**
 * Created by 何书杰 on 2017/9/28.
 */

public class BookContentActivity extends AppCompatActivity{
    private static final String TAG = "BookContentActivity";
    private WebView mWebView;
    private SwipeRefreshLayout refreshLayout;
    private Toolbar bookContent_toolbar;
    private TextView mtitle;
    private static String GET_URL = "https://read.douban.com/ebook/34157247/";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.book_content_layout);
        initView();
        //注册EventBus
        if (!EventBus.getDefault().isRegistered(this))
        {
            EventBus.getDefault().register(this);
        }
        setSupportActionBar(bookContent_toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Log.d(TAG,"GET_URL :"+GET_URL);
        mWebView.loadUrl(GET_URL);
        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                mtitle.setText(view.getTitle());
            }
        });
        refreshLayout.setColorSchemeResources(R.color.colorStatusBar);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshWebView();
            }
        });
    }
    @Subscribe
    public void onEvent(MessageEvent event) {
        GET_URL = event.getMsg();
    }

    public void initView(){
        bookContent_toolbar = (Toolbar) findViewById(R.id.book_content_toolbar);
        bookContent_toolbar.setTitle("");
        refreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefresh);
        mWebView = (WebView) findViewById(R.id.webView1);
        mtitle = (TextView) findViewById(R.id.title);
    }
    public void refreshWebView(){
        refreshLayout.setRefreshing(false);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && mWebView.canGoBack()) {
            mWebView.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
    @Override
    protected void onDestroy() {
        if (mWebView != null) {
            mWebView.loadDataWithBaseURL(null, "", "text/html", "utf-8", null);
            mWebView.clearHistory();
            ((ViewGroup) mWebView.getParent()).removeView(mWebView);
            mWebView.destroy();
            mWebView = null;
        }
        super.onDestroy();
    }

}
