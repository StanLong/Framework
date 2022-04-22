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
