package com.stanlong.interceptor;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component // 加注解在springmvc中引用
public class FirstInterceptor implements HandlerInterceptor {

    // preHandle 在控制器方法执行前执行, false 表示拦截， true 表示放行
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println("preHandle 在控制器方法执行前执行");
        return true;
    }

    // postHandle 在控制器方法执行后执行
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        System.out.println("postHandle 在控制器方法执行后执行");
    }

    // afterCompletion 在视图渲染后执行
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        System.out.println("afterCompletion 在视图渲染后执行");
    }
}
