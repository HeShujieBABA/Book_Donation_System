package com.example.book_donation_system;

import android.app.Application;

import cn.jpush.android.api.JPushInterface;

/**
 * Created by 何书杰 on 2017/10/4.
 */

public class APP extends Application {
    @Override
    public void onCreate() {
       // JPushInterface.setDebugMode(true);
       // JPushInterface.init(this);
    }
}
