package com.example.book_donation_system.db;

import android.graphics.Bitmap;
import android.net.Uri;
import android.support.v4.widget.DrawerLayout;

import org.litepal.crud.DataSupport;

/**
 * Created by 何书杰 on 2017/10/5.
 */

public class User extends DataSupport{
    private byte[]headshot;
    private String userQingMing;


    public String getUserQingMing() {
        return userQingMing;
    }

    public void setHeadshot(byte[] headshot) {
        this.headshot = headshot;
    }

    public byte[] getHeadshot() {
        return headshot;
    }

    public void setUserQingMing(String userQingMing) {
        this.userQingMing = userQingMing;
    }
}
