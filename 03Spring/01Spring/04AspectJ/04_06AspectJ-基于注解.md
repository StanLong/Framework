# AOP

## 基于注解

@Aspect 声明切面，修饰切面类，从而获得 通知。

通知

- @Before 前置
- @AfterReturning 后置
- @Around 环绕
- @AfterThrowing 抛出异常
- @After 最终

切入点

- @PointCut ，修饰方法 private void xxx(){} 之后通过“方法名”获得切入点引用

**实现案例**

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
	   xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	   xmlns:context="http://www.springframework.org/schema/context"
	   xmlns:aop="http://www.springframework.org/schema/aop"
	   xsi:schemaLocation="http://www.springframework.org/schema/beans
        				   http://www.springframework.org/schema/beans/spring-beans.xsd
        				   http://www.springframework.org/schema/context
        				   http://www.springframework.org/schema/context/spring-context.xsd
        				   http://www.springframework.org/schema/aop
        				   http://www.springframework.org/schema/aop/spring-aop.xsd">
	<!-- 扫描所有的注解类 -->
	<context:component-scan base-package="com.stanlong.aspect"></context:component-scan>
	<context:component-scan base-package="com.stanlong.service"></context:component-scan>

	<!-- 确定AOP注解生效 -->
	<aop:aspectj-autoproxy></aop:aspectj-autoproxy>


</beans>
```

```java
package com.stanlong.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

/**
 * 切面类，含有多个通知
 * @author 矢量
 *
 */
@Component
@Aspect
public class MyAspect{

	@Before("execution(* com.stanlong.service.impl.UserServiceImpl.*(..))")
	public void myBefore(JoinPoint joinPoint){
		System.out.println("前置通知: " + joinPoint.getSignature().getName());
	}

	// 声明公共的切入点表达式，哪个通知类型都可以用
	@Pointcut("execution(* com.stanlong.service.impl.UserServiceImpl.*(..))")
	public void myPointCut(){

	}

	@AfterReturning(value="myPointCut()", returning="ret")
	public void myAfterReturning(JoinPoint joinPoint, Object ret){
		System.out.println("后置通知： " + joinPoint.getSignature().getName() + " -->" + ret);
	}

	@Around(value="myPointCut()")
	public Object myAround(ProceedingJoinPoint proceedingJoinPoint) throws Throwable{
		//前
		System.out.println("环绕通知");
		//手动执行目标方法
		Object obj = proceedingJoinPoint.proceed();
		//后
		System.out.println("环绕通知");
		return obj;
	}

	@AfterThrowing(value="execution(* com.stanlong.service.impl.UserServiceImpl.*(..))", throwing="e")
	public void myAfterThrowing(JoinPoint joinPoint, Throwable e){
		System.out.println("抛出异常通知： " + e.getMessage());
	}

	@After("myPointCut()")
	public void myAfter(JoinPoint joinPoint){
		System.out.println("最终通知");
	}
}
```

```java
package com.stanlong.service;

public interface UserService {
	
	public String addUser();
	
	public void updateUser();
	
	public void deleteUser();

}
```

```java
package com.stanlong.service.impl;

import com.stanlong.service.UserService;
import org.springframework.stereotype.Service;

@Service("userServiceId")
public class UserServiceImpl implements UserService {

	@Override
	public String addUser() {
		System.out.println("s_annotation_aspectj addUser");
		return "StanLong";
	}

	@Override
	public void updateUser() {
		int i = 1/0;
		System.out.println("s_annotation_aspectj updateUser");
	}

	@Override
	public void deleteUser() {
		System.out.println("s_annotation_aspectj deleteUser");
	}
}
```

```java
import com.stanlong.service.UserService;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TestAspectJXml {
	@Test
	public void demo01(){
		String xmlPath = "applicationContext.xml";
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext(xmlPath);
		UserService userService = applicationContext.getBean("userServiceId", UserService.class);
		userService.addUser();
		userService.updateUser();
		userService.deleteUser();
		
	}
}
```

```
-- 控制台打印：

前置通知: addUser
环绕通知
r_xml_aspectj addUser
最终通知
环绕通知
后置通知： addUser -->StanLong
前置通知: updateUser
环绕通知
最终通知
抛出异常通知： / by zero
```





