# hfish的搭建

`docker search hfish`

![image-20210705151216355](https://tva1.sinaimg.cn/large/008i3skNly1gs63do3ygjj31fw0kswl4.jpg)

然后`docker pull imdevops/hfish`，这里就不截图了，因为已经下了，docker run运行

`docker run -d --name hfish1 -p 21:21 -p 23:23 -p 69:69 -p 3306:3306 -p 5900:5900 -p 6379:6379 -p 8080:8080 -p 8081:8081 -p 8989:8989 -p 9000:9000 -p 9001:9001 -p 9200:9200 -p 11211:11211  --restart=always imdevops/hfish:latest`

在运行Hfish后，我们进入Hfish的后台

`docker exec -it hfish1 sh`

![image-20210705141359916](https://tva1.sinaimg.cn/large/008i3skNly1gs61p2k9xpj323e0cwjt1.jpg)

进入后编辑config.ini修改下Hfish登陆的密码，初始密码非常简单防止被爆破，账号修改为hfishaccount、密码修改为hfishpassword

![image-20210705141701359](https://tva1.sinaimg.cn/large/008i3skNly1gs61s6m5gzj315n0u0amc.jpg)

再就是修改前端页面，把网页变得挑衅一点

![image-20210705150148745](https://tva1.sinaimg.cn/large/008i3skNly1gs632sh3imj30ta0quaby.jpg)

HFish的管理页面，从这里我们可以看到攻击的一个汇总

![image-20210705151621608](https://tva1.sinaimg.cn/large/008i3skNly1gs63hx955sj31c00u0dug.jpg)

