package com.example.googlemap;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

public class Chay_duoi_nen extends Service {

    private static final String TAG = "MyService";

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "Hello");
        Log.d(TAG, "Hello");
        Log.d(TAG, "Hello");
        Log.d(TAG, "Hello");
        Log.d(TAG, "Hello");
        Log.d(TAG, "Hello");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "onStartCommand");
        return START_STICKY; // Chạy lại service nếu bị gián đoạn
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null; // Không cần thiết khi Service không hỗ trợ giao tiếp qua Binder
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy");
    }
}