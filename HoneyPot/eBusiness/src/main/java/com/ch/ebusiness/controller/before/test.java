package com.ch.ebusiness.controller.before;

import com.ch.ebusiness.util.IpUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
public class test {
    @RequestMapping("/getIp")
    public String getIp(HttpServletRequest request) {
        System.out.println("hello world");
        System.out.println(IpUtil.getIpAddr(request));
        return "hello";
    }
}