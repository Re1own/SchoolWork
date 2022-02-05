package com.example.myapplication6;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    //点击Button时触发的方法
    public void click(View view) {
        // content://sms 查询系统所有短信的uri
        Uri uri = Uri.parse("content://sms/");

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            int hasReadSmsPermission = checkSelfPermission(Manifest.permission.READ_SMS);
            if (hasReadSmsPermission != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.READ_SMS}, RESULT_OK);
                return;
            }
        }

        //获取ContentResolver对象
        ContentResolver resolver = getContentResolver();
        //通过ContentResolver对象查询系统短信
        Cursor cursor = resolver.query(uri, new String[] { "address", "date",
                "type", "body" }, null, null, null);
        List<SmsInfo> smsInfos = new ArrayList<SmsInfo>();
        while (cursor.moveToNext()) {
            String address = cursor.getString(0);
            long date = cursor.getLong(1);
            int type = cursor.getInt(2);
            String body = cursor.getString(3);
            SmsInfo smsInfo = new SmsInfo(date, type, body, address);
            smsInfos.add(smsInfo);
        }
        cursor.close();
        File file = new File(getExternalFilesDir(null), "sms.xml");
        SmsUtils.backUpSms(smsInfos, this, file);
    }
}