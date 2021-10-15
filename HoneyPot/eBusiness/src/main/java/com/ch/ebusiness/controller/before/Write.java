package com.ch.ebusiness.controller.before;

import java.io.*;

public class Write {

    public void WriteIntoMyText(String content,int mark){

        File f = null;
        if(mark == 1){//指定admin文件
//            f=new File("/honeypot/dairy/markAdmin.txt");
            f=new File("/honeypot/markAdmin.txt");
        }else if(mark == 0){//指定user文件
//            f=new File("/honeypot/dairy/markUser.txt");
            f=new File("/honeypot/markUser.txt");
        }else if(mark == 2){//指定register文件
//            f=new File("/honeypot/dairy/markRegister.txt");
            f=new File("/honeypot/markRegister.txt");
        }else if(mark == 3){//指定register文件
//            f=new File("/honeypot/dairy/upLoadFiles.txt");
            f=new File("/honeypot/upLoadFiles.txt");
        }
        
        try {
            
            FileOutputStream fos= null;//创建输出流fos并以f为参数
            fos = new FileOutputStream(f,true);
            OutputStreamWriter osw=new OutputStreamWriter(fos);//创建字符输出流对象osw并以fos为参数
            BufferedWriter bw=new BufferedWriter(osw);//创建一个带缓冲的输出流对象bw，并以osw为参数
            bw.write(content);//使用bw写入一行文字，为字符串形式String
//            System.out.println("ok--");
            bw.newLine();//换行
            bw.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
