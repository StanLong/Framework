# AOP

## Aspect

- AspectJ是一个基于Java语言的AOP框架
- Spring2.0以后新增了对AspectJ切点表达式支持
- @AspectJ 是AspectJ1.5新增功能，通过JDK5注解技术，允许直接在Bean类中定义切面，新版本Spring框架，建议使用AspectJ方式来开发AOP
- 主要用途：自定义开发

## 切入点表达式

1. execution() 用于描述方法 【掌握】

   语法：execution(修饰符 返回值 包.类.方法名(参数) throws异常)

   - 修饰符，一般省略
     - public 公共方法
     - `*` 任意方法
   - 返回值，不能省略
     - void 返回没有值
     - String 返回值字符串
     - `*`  返回值任意
   - 包，[省略]
     - `com.itheima.crm` 固定包
     - `com.itheima.crm.*.service `crm包下面子包任意 （例如：com.itheima.crm.staff.service）
     - `com.itheima.crm..` crm包下面的所有子包（含自己）
     - `com.itheima.crm.*.service..` crm包下面任意子包，固定目录service，service目录任意包
   - 类，[省略]
     - UserServiceImpl 指定类
     - `*Impl` 以Impl结尾
     - `User*` 以User开头
     - `*` 任意类
   - 方法名，不能省略
     - addUser 固定方法
     - add* 以add开头
     - *Do 以Do结尾
     - `*` 任意方法
   - (参数)
     - () 无参
     - (int) 一个整型
     - (int ,int) 两个
     - (..) 参数任意
   - throws ,可省略，一般不写。

**使用案例1** `execution(* com.itheima.crm.*.service..*.*(..))`

**使用案例2** 

```
<aop:pointcut expression="execution(* com.itheima.*WithCommit.*(..)) || execution(* com.itheima.*Service.*(..))" id="myPointCut"/>
```

2. within:匹配包或子包中的方法(了解)
   - within(com.itheima.aop..*)
3. this:匹配实现接口的代理对象中的方法(了解)
   - this(com.itheima.aop.user.UserDAO)
4. target:匹配实现接口的目标对象中的方法(了解)
   - target(com.itheima.aop.user.UserDAO)
5. args:匹配参数格式符合标准的方法(了解)
   - args(int,int)
6. bean(id) 对指定的bean所有的方法(了解)
   - bean('userServiceId')

## AspectJ 通知类型

- aop联盟定义通知类型，具有特性接口，必须实现，从而确定方法名称。
- aspectj 通知类型，只定义类型名称。已经方法格式。
- 个数：6种，知道5种，掌握1中。

**before**:前置通知(应用：各种校验)

- 在方法执行前执行，如果通知抛出异常，阻止方法运行

**afterReturning**:后置通知(应用：常规数据处理)

- 方法正常返回后执行，如果方法中抛出异常，通知无法执行。必须在方法执行后才执行，所以可以获得方法的返回值。

**around**:环绕通知(应用：十分强大，可以做任何事情)

- 方法执行前后分别执行，可以阻止方法的执行。必须手动执行目标方法

**afterThrowing**:抛出异常通知(应用：包装异常信息)

- 方法抛出异常后执行，如果方法没有抛出异常，无法执行

**after**:最终通知(应用：清理现场)

- 方法执行完毕后执行，无论方法中是否出现异常

**环绕**

```java
try{ 
    //前置：before //手动执行目标方法 //后置：afterRetruning 
} catch(){ 
    //抛出异常 afterThrowing 
} finally{ 
    //最终 after 
}
```

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

	<!-- 创建目标类 -->
	<bean id="userServiceId" class="com.stanlong.service.impl.UserServiceImpl"></bean>

	<!-- 创建切面类 -->
	<bean id="myAspectId" class="com.stanlong.aspect.MyAspect"></bean>

	<!-- AOP编程
		<aop:aspect> 将切面类声明成了切面，从而获得通知
			ref 切面类的引用
		<<aop:pointcut> 声明切入点表达示，所有的通知都可以使用
			execution 切入点表达示
			id：名称(貌似是任意起的)，用来其他通知引用
	 -->
	<aop:config>
		<aop:aspect ref="myAspectId">
			<aop:pointcut expression="execution(* com.stanlong.service.impl.UserServiceImpl.*(..))" id="myPointCut"/>
			<!-- 前置通知
				method : 通知及方法名
				pointcut: 切入点表达示，此表达示只能在当前的通知使用
				pointcut-ref : 切入点的引用，可以与其他通知共享切入点
				通知方法的格式： public void myBefore(JoinPoint joinPoint)
					参数：org.aspectj.lang.JoinPoint 用于描述连接点（目标方法），可以获得当前目标方法的方法名
			-->
			<aop:before method="myBefore" pointcut-ref="myPointCut"/>


			<!-- 后置通知 : 目标方法后执行，获得目标方法的返回值
				通知方法格式：public void myAfterReturning(JoinPoint joinPoint, Object ret)
					参数1： 连接点描述
					参数2：类型Object, 参数名由 returning="ret" 配置的
			-->
			<aop:after-returning method="myAfterReturning" pointcut-ref="myPointCut" returning="ret"/>


			<!-- 环绕通知
				通知方法格式：public Object myAround(ProceedingJoinPoint proceedingJoinPoint) throws Throwable
				返回值类型： Object
				方法名：任意
				参数：org.aspectj.lang.ProceedingJoinPoint
				抛出异常
				执行目标方法 Object obj = proceedingJoinPoint.proceed();
			-->
			<aop:around method="myAround" pointcut-ref="myPointCut"/>


			<!-- 抛出异常
				通知方法格式：public void myAfterThrowing(JoinPoint joinPoint, Throwable e)
					参数1：描述连接点对象
					参数2：获得异常信息， 类型Throwable, 参数名由 throwing="e" 配置
			-->
			<aop:after-throwing method="myAfterThrowing" pointcut-ref="myPointCut" throwing="e"/>

			<!-- 最终通知 -->
			<aop:after method="myAfter" pointcut-ref="myPointCut"/>
		</aop:aspect>
	</aop:config>
</beans>
```

```java
package com.stanlong.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
/**
 * 切面类，含有多个通知
 * @author 矢量
 *
 */
public class MyAspect{

	public void myBefore(JoinPoint joinPoint){
		System.out.println("前置通知: " + joinPoint.getSignature().getName());
	}

	public void myAfterReturning(JoinPoint joinPoint, Object ret){
		System.out.println("后置通知： " + joinPoint.getSignature().getName() + " -->" + ret); // ret 后置通知的返回值
	}

	public Object myAround(ProceedingJoinPoint proceedingJoinPoint) throws Throwable{
		//前
		System.out.println("环绕通知");
		//手动执行目标方法
		Object obj = proceedingJoinPoint.proceed();
		//后
		System.out.println("环绕通知");
		return obj;
	}

	public void myAfterThrowing(JoinPoint joinPoint, Throwable e){
		System.out.println("抛出异常通知： " + e.getMessage());
	}

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

public class UserServiceImpl implements UserService {

	@Override
	public String addUser() {
		System.out.println("r_xml_aspectj addUser");
		return "StanLong";
	}

	@Override
	public void updateUser() {
		int i = 1/0;
		System.out.println("r_xml_aspectj updateUser");
	}

	@Override
	public void deleteUser() {
		System.out.println("r_xml_aspectj deleteUser");
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





