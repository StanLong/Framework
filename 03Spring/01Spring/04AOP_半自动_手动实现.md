# AOP

## 半自动

### JDK代理

 JDK动态代理 对“装饰者”设计模式 简化。使用前提：必须有接口

- 目标类：接口 + 实现类
- 切面类：用于存通知 MyAspect
- 工厂类：编写工厂生成代理

```java
package com.stanlong.service;

public interface UserService {
	
	public void addUser();
	
	public void updateUser();
	
	public void deleteUser();
}
```

```java
package com.stanlong.service.impl;

import com.stanlong.service.UserService;

public class UserServiceImpl implements UserService {

	@Override
	public void addUser() {
		System.out.println("n_aop_jdk addUser");
	}

	@Override
	public void updateUser() {
		System.out.println("n_aop_jdk updateUser");
	}

	@Override
	public void deleteUser() {
		System.out.println("n_aop_jdk deleteUser");
	}
}
```

```java
package com.stanlong.aspect;

public class MyAspect {

	public void before(){
		System.out.println("鸡首");
	}
	
	public void after(){
		System.out.println("牛后");
	}
}
```

```java
package com.stanlong.aspect;

import com.stanlong.service.UserService;
import com.stanlong.service.impl.UserServiceImpl;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class MyBeanFactory {
	
	public static UserService createUserService(){
		//1、目标类
		UserService userService = new UserServiceImpl();
		//2、切面类
		MyAspect myAspect = new MyAspect();
		//3、代理类：将目标类（切入点）和切面类（通知）结合--》切面
		/**
		 * 参数1：loader，类加载器，动态代理类，运行时创建，任何类都需要类加载器并将其加载到内存
		 * 		一般情况下： 当前类.class.getClassLoader();
		 * 				目标类.class.getClassLoader();
		 * 参数2：Class[] interfaces:代理类需要实现的所有接口
		 * 		方式1：目标类.getClass().getInterfaces() 注意：只能获得自己的接口，不能获得父类接口
		 * 		方式2：new Class[]{UserService.class} 例如：jdbc驱动
		 * 参数3：h： InvocationHandler 处理类，接口，必须进行实现类一般采用匿名内部类的方式
		 * 		提供 invoke 方法，代理类的每一个方法执行时，都将调用一次 invoke
		 * 		参数 : Object proxy :代理对象
		 * 		参数：Method method：代理对象当前执行方法的描述对象（反射）
		 * 			执行方法名：method.getName()
		 * 			执行方法：method.invoke(对象，实际参数)
		 * 		参数：Object[] args 方法实际参数
		 */
		UserService proxyService = (UserService)Proxy.newProxyInstance(MyBeanFactory.class.getClassLoader()
				, userService.getClass().getInterfaces()
				, new InvocationHandler() {
					
					@Override
					public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
						//前执行
						myAspect.before();
						
						//执行目标类的方法
						Object obj = method.invoke(userService, args);
						
						//后执行
						myAspect.after();
						return obj;
					}
				});
		return proxyService;
	}
}
```

```java
import com.stanlong.aspect.MyBeanFactory;
import com.stanlong.service.UserService;
import org.junit.Test;

public class TestAopJdk {
    
	/**
	 * 测试
	 */
	@Test
	public void demo01(){
		UserService userService = MyBeanFactory.createUserService();
		userService.addUser();
		userService.updateUser();
		userService.deleteUser();
	}
}
```

### cglib代理

- 没有接口，只有实现类。
- 采用字节码增强框架cglib，在运行时创建目标类的子类，从而对目标类进行增强。

```java
package com.stanlong.aspect;

public class MyAspect {

	public void before(){
		System.out.println("鸡首2");
	}
	
	public void after(){
		System.out.println("牛后2");
	}
}
```

```java
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
```

```java
package com.stanlong.service.impl;

public class UserServiceImpl {

	public void addUser() {
		System.out.println("o_cglib addUser");
	}

	public void updateUser() {
		System.out.println("o_cglib updateUser");
	}

	public void deleteUser() {
		System.out.println("o_cglib deleteUser");
	}
}
```

```java
import com.stanlong.aspect.MyBeanFactory;
import com.stanlong.service.impl.UserServiceImpl;
import org.junit.Test;

public class TestCglib {
	/**
	 * 测试
	 */
	@Test
	public void demo01(){
		UserServiceImpl userServiceImpl = MyBeanFactory.createUserService();
		userServiceImpl.addUser();
		userServiceImpl.updateUser();
		userServiceImpl.deleteUser();
	}
}
```

