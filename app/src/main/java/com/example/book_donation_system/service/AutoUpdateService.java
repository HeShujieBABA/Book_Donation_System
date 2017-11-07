package com.example.book_donation_system.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class AutoUpdateService extends Service {
    public AutoUpdateService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
