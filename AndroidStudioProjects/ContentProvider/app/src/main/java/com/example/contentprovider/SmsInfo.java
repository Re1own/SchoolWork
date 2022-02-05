package com.example.contentprovider;

public class SmsInfo {
    private long date;        //时间
    private int type;         //类型
    private String body;     //短信内容
    private String address; // 发送地址
    private int id;
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    //构造方法
    public SmsInfo() {
    }
    //构造方法
    public SmsInfo(long date, int type, String body, String address,int id) {
        this.date = date;
        this.type = type;
        this.body = body;
        this.address = address;
        this.id = id;
    }
    //构造方法
    public SmsInfo(long date, int type, String body, String address) {
        this.date = date;
        this.type = type;
        this.body = body;
        this.address = address;
    }
    public long getDate() {
        return date;
    }
    public void setDate(long date) {
        this.date = date;
    }
    public int getType() {
        return type;
    }
    public void setType(int type) {
        this.type = type;
    }
    public String getBody() {
        return body;
    }
    public void setBody(String body) {
        this.body = body;
    }
    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }
}
