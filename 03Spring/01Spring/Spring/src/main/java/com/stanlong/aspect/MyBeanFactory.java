package com.stanlong.aspect;

import com.stanlong.service.impl.UserServiceImpl;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

public class MyBeanFactory {

	public static UserServiceImpl createUserService() {
		//1、目标类
		UserServiceImpl userServiceImpl = new UserServiceImpl();
		//2、切面类
		MyAspect myAspect = new MyAspect();
		//3、代理类：采用 cglib，底层创建目标类的子类
		//3.1核心类
		Enhancer enhancer = new Enhancer();
		//3.2确定父类
		enhancer.setSuperclass(userServiceImpl.getClass());
		//3.3 设置回调函数  MethodInterceptor 接口相当于 InvocationHandler 接口
		/**
		 * intercept 等效于jdk代理的 invoke
		 * 参数1、参数2、参数3与invoke的参数含义一样
		 * 参数4：MethodProxy 执行代理类的父类 等价于执行代理类
		 */
		enhancer.setCallback(new MethodInterceptor() {

			@Override
			public Object intercept(Object proxy, Method method, Object[] args, MethodProxy arg3) throws Throwable {
				//前
				myAspect.before();
				//目标类方法
				Object obj = method.invoke(userServiceImpl, args);
				//后
				myAspect.after();

				return obj;
			}
		});
		//3.4 创建代理
		UserServiceImpl proxyService = (UserServiceImpl) enhancer.create();
		return proxyService;
	}
}
