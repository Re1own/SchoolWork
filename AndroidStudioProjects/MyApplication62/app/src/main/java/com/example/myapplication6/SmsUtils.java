package com.example.myapplication6;

import android.content.Context;
import android.os.Environment;
import android.util.Xml;
import android.widget.Toast;

import org.xmlpull.v1.XmlSerializer;

import java.io.File;
import java.io.FileOutputStream;
import java.util.List;

public class SmsUtils {
    //将短息信息保存至sdcard目录下的backup2.xml文件中
    public static void backUpSms(List<SmsInfo> smsInfos, Context context, File file_name) {
        try {
            XmlSerializer serializer = Xml.newSerializer();

            FileOutputStream os = new FileOutputStream(file_name);
            // 初始化 序列号器 指定xml数据写入到哪个文件 并且指定文件的编码方式
            serializer.setOutput(os, "utf-8");
            serializer.startDocument("utf-8", true);
            //构建根节点
            serializer.startTag(null, "smss");
            for (SmsInfo info : smsInfos) {
                //构建父节点开始标签
                serializer.startTag(null, "sms");
                serializer.attribute(null, "id", info.getId() + "");
                //构建子节点body
                serializer.startTag(null, "body");
                serializer.text(info.getBody());
                serializer.endTag(null, "body");
                //构建子节点address
                serializer.startTag(null, "address");
                serializer.text(info.getAddress());
                serializer.endTag(null, "address");
                //构建子节点type
                serializer.startTag(null, "type");
                serializer.text(info.getType() + "");
                serializer.endTag(null, "type");
                //构建子节date
                serializer.startTag(null, "date");
                serializer.text(info.getDate() + "");
                serializer.endTag(null, "date");
                //父节点结束标签
                serializer.endTag(null, "sms");
            }
            serializer.endTag(null, "smss");
            serializer.endDocument();
            os.close();
            Toast.makeText(context, "备份成功", 0).show();
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(context, "备份失败", 0).show();
        }
    }
}
