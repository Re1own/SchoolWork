package com.example.myapplication4;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    List<PlantInfo> plantInfos = null;
    private TextView mPlantContentTV = null;
    private ImageView mPlantImgv = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        try {
            //获取植物信息
            plantInfos = PlantUtils.getPlantInfos(getResources().getAssets().open("plant.xml"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        initView();
    }
    /**
     * 初始化控件
     */
    private void initView() {
        findViewById(R.id.btn_xrq).setOnClickListener(this);
        findViewById(R.id.btn_xrk).setOnClickListener(this);
        findViewById(R.id.btn_xyc).setOnClickListener(this);
        mPlantContentTV = (TextView) findViewById(R.id.tv_plants_content);
        mPlantImgv = (ImageView) findViewById(R.id.imgv_plant);
        mPlantContentTV.setText("\u3000\u3000" +
                plantInfos.get(0).getPlantContent());
        mPlantImgv.setBackgroundResource(R.drawable.a);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_xrq:
                //设置植物简介
                mPlantContentTV.setText("\u3000\u3000" +
                        plantInfos.get(0).getPlantContent());
                mPlantImgv.setBackgroundResource(R.drawable.a);
                break;
            case R.id.btn_xyc:
                mPlantContentTV.setText("\u3000\u3000" +
                        plantInfos.get(1).getPlantContent());
                mPlantImgv.setBackgroundResource(R.drawable.b);
                break;
            case R.id.btn_xrk:
                mPlantContentTV.setText("\u3000\u3000" +
                        plantInfos.get(2).getPlantContent());
                mPlantImgv.setBackgroundResource(R.drawable.c);
                break;
        }
    }
}