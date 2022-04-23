# 整合web

**tomcat启动时就加载配置文件的三种方案**

1. `servlet --> init(ServletConfig) --> <load-on-startup>`
2. `filter --> init(FilterConfig) --> web.xml注册过滤器自动调用初始化`
3. `listener --> ServletContextListener --> servletContext对象监听【】`

spring采用的是第三种。spring提供监听器 `ContextLoaderListener --> web.xml <listener><listener-class>....`

如果只配置监听器，默认加载xml位置：`/WEB-INF/applicationContext.xml`

**web.xml**

```xml
<?xml version="1.0" encoding="UTF-8"?>
<web-app xmlns="http://xmlns.jcp.org/xml/ns/javaee"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://xmlns.jcp.org/xml/ns/javaee http://xmlns.jcp.org/xml/ns/javaee/web-app_4_0.xsd"
         version="4.0">
    <display-name>SpringWeb</display-name>
    <!-- 1. 配置Spring监听器，默认加载xml位置是在/WEB-INF/applicationContext.xml -->
    <listener>
        <listener-class>org.springframework.web.context.ContextLoaderListener</listener-class>
    </listener>

    <!-- 2. 确定配置文件位置，通过系统初始化参数 -->
    <context-param>
        <param-name>contextConfigLocation</param-name>
        <param-value>classpath:applicationContext.xml</param-value>
    </context-param>

    <servlet>
        <servlet-name>HelloServlet</servlet-name>
        <servlet-class>com.stanlong.servlet.HelloServlet</servlet-class>
    </servlet>
    <servlet-mapping>
        <servlet-name>HelloServlet</servlet-name>
        <url-pattern>/HelloServlet</url-pattern>
    </servlet-mapping>
</web-app>
```

**index.jsp**

```jsp
<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>HelloSpring</title>
</head>
<body>
	<a href="${pageContext.request.contextPath}/HelloServlet">Spring整合Web</a>
</body>
</html>
```

```java
package com.stanlong.servlet;

import com.stanlong.service.AccountService;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class HelloServlet extends HttpServlet{

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //从applicationContex作用域获得Spring容器
        //方式1： 手动从作用域获取 ApplicationContext applicationContext = (ApplicationContext) this.getServletContext().getAttribute(WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE);
        //方式2： 通过工具获取
        ApplicationContext applicationContext =
                WebApplicationContextUtils.getWebApplicationContext(this.getServletContext());

        //操作
        AccountService accountService = applicationContext.getBean("accountServiceId", AccountService.class);
        accountService.transfer("lisi", "zhangsan", 1000);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
```

说明：

- tomcat 版本 apache-tomcat-9.0.30
- 解析错误：`$%7BpageContext.request.contextPath%7D` `https://blog.csdn.net/qq_43493747/article/details/118682557`
- tomcat9启动时中文乱码 `https://blog.csdn.net/weixin_45816407/article/details/106061489`
- 报类`javax.servlet.http.HttpServlet` 找不到 `https://www.cnblogs.com/L-Wirepuller/p/10838405.html`

