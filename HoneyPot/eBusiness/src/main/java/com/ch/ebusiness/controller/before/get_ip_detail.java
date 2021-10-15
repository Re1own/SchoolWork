package com.ch.ebusiness.controller.before;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class get_ip_detail {
    public String filter(String text) throws IOException {
        String s = null;
        //正则
        BufferedReader br = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(text.getBytes(Charset.forName("utf8"))), Charset.forName("utf8")));
        while((s = br.readLine()) != null) {

            String start = " <div id=\"tab0_address\">", end = "</div>";
            String patern = "(?<=\\" + start + ")[^\\" + end + "]+";
            Pattern pattern = Pattern.compile(patern);
            Matcher matcher = pattern.matcher(s);

            while (matcher.find()) {
//                System.out.println(matcher.group());
                if(matcher.group()!=null){
                    return matcher.group();
                }
            }
        }
        return null;

    }
    public String get_place(String ip) throws Exception{
        URL url = new URL("https://www.ip.cn/ip/" + ip + ".html");
        HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
        httpConn.setRequestMethod("GET");

        httpConn.setRequestProperty("authority", "www.ip.cn");
        httpConn.setRequestProperty("sec-ch-ua", "\" Not;A Brand\";v=\"99\", \"Google Chrome\";v=\"91\", \"Chromium\";v=\"91\"");
        httpConn.setRequestProperty("sec-ch-ua-mobile", "?0");
        httpConn.setRequestProperty("upgrade-insecure-requests", "1");
        httpConn.setRequestProperty("user-agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/91.0.4472.114 Safari/537.36");
        httpConn.setRequestProperty("accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9");
        httpConn.setRequestProperty("sec-fetch-site", "none");
        httpConn.setRequestProperty("sec-fetch-mode", "navigate");
        httpConn.setRequestProperty("sec-fetch-user", "?1");
        httpConn.setRequestProperty("sec-fetch-dest", "document");
        httpConn.setRequestProperty("accept-language", "en-US,en;q=0.9");

        InputStream responseStream = httpConn.getResponseCode() / 100 == 2
                ? httpConn.getInputStream()
                : httpConn.getErrorStream();
        Scanner s = new Scanner(responseStream).useDelimiter("\\A");
        String response = s.hasNext() ? s.next() : "";
        response = filter(response);

        return response;

    }
}
