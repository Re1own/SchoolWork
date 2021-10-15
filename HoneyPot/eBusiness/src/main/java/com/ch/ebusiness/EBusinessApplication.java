package com.ch.ebusiness;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;


@SpringBootApplication
//配置扫描MyBatis接口的包路径
@MapperScan(basePackages={"com.ch.ebusiness.repository"})
public class EBusinessApplication {
	public static void main(String[] args) throws IOException {

		SpringApplication.run(EBusinessApplication.class, args);
//
//		String s = getLocalIP("192.168.1.1");
//		System.out.println(s);

	}
	/**
	 * 获取本地的ip地址作为
	 */
	public static String getLocalIP(String defaultIp) {
		String ip = defaultIp;
		try {
			ip = InetAddress.getLocalHost().getHostAddress();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		return ip;
	}

	/**
	 * 获取真实ip地址 通过阿帕奇代理的也能获取到真实ip
	 * @param request
	 * @return
	 */
	public static String getRealIp(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}


}



