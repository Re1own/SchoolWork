package com.example.myapplication8;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

public class ALiPayService extends Service {
    private static final String TAG = "ALiPayService";

    @Override
    public IBinder onBind(Intent intent) {
        Log.v(TAG, "绑定支付宝，准备付费");
        return new MyBinder();
    }
    private class MyBinder extends IService.Stub {
        @Override
        public void callALiPayService() {
            methodInService();
        }
    }
    private void methodInService() {
        Log.v(TAG, "开始付费，购买装备");
    }
    @Override
    public void onCreate() {
        Log.v(TAG, "调用支付宝成功");
        super.onCreate();
    }
    @Override
    public void onDestroy() {
        Log.v(TAG, "关闭支付宝");
        super.onDestroy();
    }
    @Override
    public boolean onUnbind(Intent intent) {
        Log.v(TAG, "取消付费");
        return super.onUnbind(intent);
    }
}