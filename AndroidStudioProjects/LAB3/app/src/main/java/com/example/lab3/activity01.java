package com.example.lab3;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

public class activity01 extends Activity {
    private Button btn_send;
    private EditText et_name;
    private RadioButton manRadio;
    private RadioButton womanRadio;
    private EditText et_password;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity01);
        et_name = (EditText) findViewById(R.id.et_name);
        et_password = (EditText) findViewById(R.id.et_password);
        btn_send = (Button) findViewById(R.id.btn_send);
        manRadio = (RadioButton) findViewById(R.id.radioMale);
        womanRadio = (RadioButton) findViewById(R.id.radioFemale);
        //点击发送按钮进行数据传递
        btn_send.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                passDate();
            }
        });
    }
    //传递数据
    public void passDate() {
        //创建Intent对象，启动Activity02
        Intent intent = new Intent(this, activity02.class);

        //将数据存入Intent对象
        intent.putExtra("name", et_name.getText().toString().trim());
        intent.putExtra("password", et_password.getText().toString().trim());
        String str = "";
        if(manRadio.isChecked()){
            str = "男";
        }else if(womanRadio.isChecked()){
            str = "女";
        }
        intent.putExtra("sex", str);
        startActivityForResult(intent, 1);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Toast.makeText(activity01.this, data.getExtras().getString("title"), Toast.LENGTH_LONG).show();
        if (resultCode == 1) {
            String adata = data.getStringExtra("title");
            Log.i("activity01", adata);
        }
    }
}