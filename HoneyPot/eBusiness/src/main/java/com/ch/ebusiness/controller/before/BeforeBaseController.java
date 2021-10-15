package com.ch.ebusiness.controller.before;
import javax.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;

@Controller
public class BeforeBaseController {
	/**
	 * 登录权限控制，处理方法执行前执行该方法 
	 */
	@ModelAttribute  
    public void isLogin(HttpSession session) throws Exception {
       if(session.getAttribute("bUser") == null){  
            throw new Exception("没有登录");
       }  
    } 
}