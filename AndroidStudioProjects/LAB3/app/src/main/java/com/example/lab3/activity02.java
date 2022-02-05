package com.example.lab3;

import androidx.appcompat.app.AppCompatActivity;
import android.app.Activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.content.Intent;


public class activity02 extends Activity {
    private TextView tv_name;
    private TextView tv_password;
    private TextView tv_sex;

    private Button mBtnFinish;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity02);
        Intent intent=getIntent();  //获取activity01中传来的数据

        String name = intent.getStringExtra("name");
        String password = intent.getStringExtra("password");
        String sex = intent.getStringExtra("sex");
        tv_name =(TextView) findViewById(R.id.tv_name);
        tv_password = (TextView) findViewById(R.id.tv_password);
        tv_sex = (TextView) findViewById(R.id.tv_sex);

        tv_name.setText("用户名："+name);
        tv_password.setText("密    码："+password);
        tv_sex.setText("性   别："+sex);

        mBtnFinish = (Button) findViewById(R.id.btn_finish);    //获取button返回值
        mBtnFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View V) {
                Intent intent1 = new Intent();
                setResult(Activity.RESULT_OK, intent1);
                Bundle bundle1 = new Bundle();
                String str = name + password + sex;
                bundle1.putString("title", str);
                intent1.putExtras(bundle1);
                setResult(1, intent1);
                finish();
            }
        });

    }
}
