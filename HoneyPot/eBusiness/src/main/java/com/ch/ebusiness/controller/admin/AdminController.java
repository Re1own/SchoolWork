package com.ch.ebusiness.controller.admin;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.ch.ebusiness.controller.before.Write;
import com.ch.ebusiness.controller.before.get_ip_detail;
import com.ch.ebusiness.util.IpUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ch.ebusiness.entity.AUser;
import com.ch.ebusiness.service.admin.AdminService;

@Controller
@RequestMapping("/admin")
public class AdminController {
	@Autowired
	private AdminService adminService;
	@RequestMapping("/toLogin")
	public String toLogin(@ModelAttribute("aUser") AUser aUser, HttpServletRequest request) throws Exception {

		Write write = new Write();
		write.WriteIntoMyText("**********************************",1);
		//记录ip
		String ip = IpUtil.getIpAddr(request);//获取ip
//		System.out.println(ip);
		//写入记录
		get_ip_detail p = new get_ip_detail();
		String place = p.get_place(ip);
		write.WriteIntoMyText("来自" + place + "ip地址为["+ip+"]的登录操作信息",1);
		write.WriteIntoMyText("**********************************",1);

		return "admin/login";

	}
	@RequestMapping("/login")
	public String login(@ModelAttribute("aUser") AUser aUser, HttpSession session, Model model,HttpServletRequest request) {

		return adminService.login(aUser, session, model);
	}
}
