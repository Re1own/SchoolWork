# Springboot基础

## SpringBoot特性：

1、去除xml配置

2、全部采用注解化的方式配置

3、内嵌入Tomcat

SpringBoot框架默认的情况下spring-boot-start-web已经帮助问整合好了SpringMVC框架



SpringBoot与SpringCloud之间的区别

SpringCloud微服务解决框架，微服务技术解决方案

原理：封装Maven依赖



## 常见注解的使用

@RequestMapping("/test")	/通过/test来访问该类、函数

@RestController	使整个类中所有SpringMVCUrl接口映射都是返回json格式

@ResponseBody	只是该函数返回json格式

@ComponentScan("com.example.demo")

测试：

DemoApplication.class

```java
package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@EnableAutoConfiguration
@RestController //使整个类中所有SpringMVCUrl接口映射都是返回json格式
@ComponentScan("com.example.demo")  //扫描com.example.demo包下面的所有类，这样就能访问其他映射了
public class DemoApplication {
    @RequestMapping("/hello")
    public String hello() {
        return "ok";
    }

    public static void main(String[] args) {
        //启动类入口class,默认整合Tomcat容器端口8080
        SpringApplication.run(DemoApplication.class, args);
    }

}
```

Demo2.class

```java
package com.example.demo;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class Demo2 {
    @RequestMapping("/xixi")
    public String haha() {
        return "嘻嘻";
    }
}
```

访问主函数下的hello映射

![image-20210601121947617](https://tva1.sinaimg.cn/large/008i3skNly1gr2nbpz65dj30ie05c3yo.jpg)

访问Demo2类中的xixi映射

![image-20210601122127253](https://tva1.sinaimg.cn/large/008i3skNly1gr2ndg5vovj30iu05k74g.jpg)

注解@SpringBootApplication的原理，其实就是包含了@SpringBootConfiguration、@EnableAutoConfiguration、@ComponentScan，其中@ComponentScan只能扫描到**当前包的子包的类和当前包的类**

```java
@SpringBootConfiguration
@EnableAutoConfiguration
@ComponentScan(
    excludeFilters = {@Filter(
    type = FilterType.CUSTOM,
    classes = {TypeExcludeFilter.class}
), @Filter(
    type = FilterType.CUSTOM,
    classes = {AutoConfigurationExcludeFilter.class}
)}
```

动静分离：部署在cdn上

Cdn：减少带宽距离传输，减少自己服务器带宽

图片放在resources目录下的static目录下就可以访问了，但是这样访问太多带宽会拥挤，后续可以使用cdn来减压

![image-20210601124816937](https://tva1.sinaimg.cn/large/008i3skNly1gr2o5d56wdj30bo0mo401.jpg)

## SpringBoot的配置方式

SpringBoot支持两种配置方式，一种是properties文件，一种是yml。使用yml可以减少配置文件的重复性，例如：application.properties, application.yml，默认都是application

![image-20210601125046651](https://tva1.sinaimg.cn/large/008i3skNly1gr2o7ypsnoj30os07gwf1.jpg)

测试：

application.properties

```properties
persons.name=zhz
persons.age=22
```

在类中用@Value("${}")来映射property中的值

```java
package com.example.demo;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class Demo2 {
    @RequestMapping("/xixi")
    public String haha() {
        return "嘻嘻";
    }
    @Value("${persons.name}")
    private String st_name;

    @Value("${persons.age}")
    private String st_age;

    @RequestMapping("/name")
    public String name() {
        return st_name;
    }

    @RequestMapping("/age")
    public String age() {
        return st_age;
    }
}
```

运行结果：

![image-20210601133208722](https://tva1.sinaimg.cn/large/008i3skNly1gr2pf0dsqwj30iy06cq34.jpg)

![image-20210601133411358](https://tva1.sinaimg.cn/large/008i3skNly1gr2ph4pdkyj30gi05g74g.jpg)

如果用yml（比properties更方便），格式应该如下：（一定要注意空格）

```yml
persons:
  name: zhz
  age: 22
```

## 模板引擎

渲染Web页面：

controller---视图层：渲染页面

service---业务逻辑层

dao---数据库访问层

除了使用注解@RestController来处理请求，返回的内容为json对象，如果需要渲染html页面的时候，可以通过模板引擎（能够更好地帮助seo搜索到该网页）

Spring Boot提供了默认配置的模板引擎主要有以下几种：

**Thymeleaf**

**FreeMarker**

**Velocity**

**Groovy**

**Mustache**



### FreeMarker

首先在pom.xml中引入依赖

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-freemarker</artifactId>
</dependency>
```

然后在resources下的templates中新建freemarkerIndex.ftl文件

```ftl
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Insert title here</title>
</head>
<body>
Springboot freemarker整合html页面
<h1>name: ${name}</h1>
</body>
</html>
```

创建控制器专门的包，写控制器test.java

```java
package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Controller
public class test {
    @RequestMapping("/freemarkerIndex")
    public String freemarkerIndex(Map<String, String> result, HttpServletRequest request) {
        result.put("name", "zhz");
        return "freemarkerIndex";
    }
}
```

### Tymeleaf

#### 单一输出信息

引入Tymeleaf依赖

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-thymeleaf</artifactId>
</dependency>
```

创建entity包和实体类UserEntity.java

```java
package com.example.demo.entity;

public class UserEntity {
    private String userName;
    private Integer age;
    public UserEntity(String userName, Integer age) {
        this.userName = userName;
        this.age = age;
    }
    public String getUserName() {
        return userName;
    }
    public Integer getAge() {
        return age;
    }
}
```

创建controller包和ThymeleafController.java，两种写法：

第一种写法：

```java
package com.example.demo.controller;import com.example.demo.entity.UserEntity;import org.springframework.stereotype.Controller;import org.springframework.web.bind.annotation.GetMapping;import org.springframework.web.bind.annotation.RequestMapping;import javax.servlet.http.HttpServletRequest;@Controllerpublic class ThymeleafController {    @GetMapping("/myThymeleaf")    public String myThymeleaf(HttpServletRequest request) {        request.setAttribute("user", new UserEntity("zhz", 22));        return "myThymeleaf";    }}
```

第二种写法（更为推荐，不容易误报冲突）：

```java
package com.example.demo.controller;

import com.example.demo.entity.UserEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Controller
public class ThymeleafController {
    @GetMapping("/myThymeleaf")
    public String myThymeleaf(HttpServletRequest request, Map<String, Object> result) {
        result.put("user", new UserEntity("zhz", 22));
        return "myThymeleaf";
    }
}
```

在controller和entity的包外面创建DemoApplication.java，并写入主函数

```java
package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class DemoApplication {
    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }
}
```

在resources的templates目录下创建myThymeleaf.html前端模板

```html
<!DOCTYPE html>
<html lang="en" xmlns:th="http://www.thymeleaf.org">
<head>
    <meta charset="UTF-8">
    <title>Show User</title>
</head>
<body>
<table>
    姓名：<span th:text="${user.userName}"></span>
    年龄：<span th:text="${user.age}"></span>
</table>
</body>
</html>
```

最后在application.yml中配置

```yml
spring:
  thymeleaf:
    prefix: classpath:/templates/
    check-template-location: true
    cache: false
    suffix: .html
    encoding: UTF-8
    mode: HTML
```

文件布局：

![image-20210601155009105](https://tva1.sinaimg.cn/large/008i3skNly1gr2teltcsgj30qq0o876s.jpg)

运行结果：

![image-20210601155022584](https://tva1.sinaimg.cn/large/008i3skNly1gr2teu2g5uj30om066aaf.jpg)

### 如何遍历

在控制器ThymeleafController中通过ArrayList存储数据，然后再put传

ThymeleafController.java

```java
package com.example.demo.controller;import com.example.demo.entity.UserEntity;import org.apache.catalina.User;import org.springframework.stereotype.Controller;import org.springframework.web.bind.annotation.GetMapping;import org.springframework.web.bind.annotation.RequestMapping;import javax.servlet.http.HttpServletRequest;import java.util.ArrayList;import java.util.Map;@Controllerpublic class ThymeleafController {    @GetMapping("/myThymeleaf")    public String myThymeleaf(HttpServletRequest request, Map<String, Object> result) {        result.put("user", new UserEntity("zhz", 22));        ArrayList<UserEntity> userEntities = new ArrayList<>();        userEntities.add(new UserEntity("zhz1", 10));        userEntities.add(new UserEntity("zhz2", 11));        userEntities.add(new UserEntity("zhz3", 14));        userEntities.add(new UserEntity("zhz4", 15));        result.put("userlist", userEntities);        return "myThymeleaf";    }}
```

myThymeleaf.html

```html
<!DOCTYPE html>
<html lang="en" xmlns:th="http://www.thymeleaf.org">
<head>
    <meta charset="UTF-8">
    <title>Show User</title>
</head>
<body>
<table>
    姓名：<span th:text="${user.userName}"></span>
    年龄：<span th:text="${user.age}"></span>
    <ul th:each="user:${userlist}">
        <li th:text="${user.userName}"></li>
        <li th:text="${user.age}"></li>
    </ul>
</table>
</body>
</html>
```

运行结果：

![image-20210601160505515](https://tva1.sinaimg.cn/large/008i3skNly1gr2tu5cn7hj30n00s4abl.jpg)

### html中的if判断

```html
<span th:if="${user.age>17}">已经成年</span>
<span th:if="${user.age<17}">未成年</span>
```



## 数据库访问

### 用jdbc直接查询

在pom.xml中引入mysql和jdbc

引入jdbc依赖：

```xml
<dependency>    <groupId>org.springframework.boot</groupId>    <artifactId>spring-boot-starter-jdbc</artifactId></dependency>
```

引入mysql依赖：

```xml
<dependency>    <groupId>mysql</groupId>    <artifactId>mysql-connector-java</artifactId>    <version>8.0.23</version></dependency>
```

配置application.yml（**url后面的javaee是我用来实验的数据库**）

```yml
spring:  thymeleaf:    prefix: classpath:/templates/    check-template-location: true    cache: false    suffix: .html    encoding: UTF-8    mode: HTML  datasource:    url: jdbc:mysql://localhost:3306/javaee    username: root    password: 12345678    driver-class-name: com.mysql.cj.jdbc.Driver
```

创建user表（**AUTO_INCREMENT用来自增序号**）

```mysql
CREATE TABLE users (	id INT(11) NOT NULL AUTO_INCREMENT,	name VARCHAR(20) NOT NULL COMMENT '用户名称',	age int(11) DEFAULT NULL,	PRIMARY KEY(id))
```

先插入一个数据

```mysql
INSERT INTO users VALUES(NULL, 'zhz', '22')
```

创建service包和UserService.java(用来进行数据库操作的类)

```java
package com.example.demo.service;import org.springframework.beans.factory.annotation.Autowired;import org.springframework.jdbc.core.JdbcTemplate;import org.springframework.web.bind.annotation.RequestMapping;import org.springframework.web.bind.annotation.RestController;@RestControllerpublic class UserService {    @Autowired    private JdbcTemplate jdbcTemplate;    @RequestMapping("/insertUser")    public String insertUser(String userName, Integer age) {        int update = jdbcTemplate.update("INSERT into users values(null, ?, ?)", userName, age);        return update > 0?  "success" : "false";    }}
```

测试：

![image-20210601182046517](https://tva1.sinaimg.cn/large/008i3skNly1gr2xrbsfivj30wo06kdgd.jpg)

![image-20210601182303773](https://tva1.sinaimg.cn/large/008i3skNly1gr2xtpjsk3j30us06agm2.jpg)

**把?后面的参数名字换掉也是没有关系的哦**

![image-20210601182327800](https://tva1.sinaimg.cn/large/008i3skNly1gr2xu4mh6ij30wa0540t6.jpg)

数据库中也可以看到是实打实的增加了用户数据

![image-20210601182628466](https://tva1.sinaimg.cn/large/008i3skNly1gr2xx8zmq6j317u0u0n04.jpg)

### 整合mybaits框架

#### 使用mybaits查询和插入

在pom.xml中添加mybaits依赖（注意必须得用2版本的才行，高版本的MapperScan注解用不了

```xml
<dependency>
		<groupId>org.mybatis.spring.boot</groupId>
		<artifactId>mybatis-spring-boot-starter</artifactId>
		<version>2.1.0</version>
</dependency>
```

创建mapper包和userMapper**接口**（也就是创建插入和查询的接口）

对于`name as userName`意思是把userName（实体类中的属性是userName）替换成name（数据库中叫name），这样才能查询

```java
package com.example.demo.mapper;

import com.example.demo.entity.UserEntity;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface UserMapper {
    @Insert("INSERT into users values (null, #{userName}, #{age});")
    int insertUser(@Param("userName") String userName, @Param("age") Integer age);

    @Select("select id as id, name as userName, age as age from users where id=#{id}")
    UserEntity selectByUserId(@Param("id") Integer id);

}
```

在UserService中使用上述接口，设置映射url用mybaits查询和插入

```java
package com.example.demo.service;

import com.example.demo.entity.UserEntity;
import com.example.demo.mapper.UserMapper;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.swing.text.html.parser.Entity;

@RestController
public class UserService {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private UserMapper userMapper;

    @RequestMapping("/insertUser")
    public String insertUser(String userName, Integer age) {
        int update = jdbcTemplate.update("INSERT into users values(null, ?, ?)", userName, age);
        return update > 0?  "success" : "false";
    }

//    mybaits查询
    @RequestMapping("/mybaitsfindbyId")
    public UserEntity mybaitsfindId(Integer id) {
        return userMapper.selectByUserId(id);
    }

//    mybaits插入
    @RequestMapping("/insertUserM")
    public String insertUserM(String userName, Integer age) {
        int insert = userMapper.insertUser(userName, age);
        return insert > 0? "success" : "fail";
    }
}
```

最后不要忘了在主函数中添加扫描器MapperScan（注意mybaits的版本不要高）

```java
package com.example.demo;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;



@SpringBootApplication
@MapperScan("com.example.demo.mapper")
public class DemoApplication {
    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

}
```

查询报错，原因是在UserEntity中没有无参构造方法

![image-20210603211306791](https://tva1.sinaimg.cn/large/008i3skNly1gr5dzaqq6mj31360e676j.jpg)

补上（第八行）

```java
package com.example.demo.entity;

public class UserEntity {
    private String userName;
    private Integer age;
    private Integer id;

    public UserEntity(Integer id, String userName, Integer age) {
        this.userName = userName;
        this.id = id;
        this.age = age;
    }
    public UserEntity(String userName, Integer age) {
        this.userName = userName;
        this.age = age;
    }
    public String getUserName() {
        return userName;
    }
    public Integer getAge() {
        return age;
    }


}
```

成功查询！

![image-20210603212416788](https://tva1.sinaimg.cn/large/008i3skNly1gr5eavja41j30oc05uwf5.jpg)

插入操作：

![image-20210603215746967](https://tva1.sinaimg.cn/large/008i3skNly1gr5f9qd2a3j30u805ct97.jpg)

成功插入

![image-20210603215806385](https://tva1.sinaimg.cn/large/008i3skNly1gr5fa2heqxj30my0neadg.jpg)

