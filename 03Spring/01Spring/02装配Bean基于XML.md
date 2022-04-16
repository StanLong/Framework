# 装配Bean基于XML

## 一、实例化方式

3种bean实例化方式：**默认构造**、**静态工厂**、**实例工厂**

### 默认构造

```xml
<bean id="" class=""> 必须提供默认构造
```

### 静态工厂

静态工厂：用于生成实例对象，所有的方法必须是static

```xml
<bean id="" class="工厂全限定类名" factory-method="静态方法">
```

**实现案例**

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
        				   http://www.springframework.org/schema/beans/spring-beans.xsd">
        				   
	<!-- 将静态工厂创建的实例交给Spring 
		class: 确定静态工厂全限定类名
		factory-method: 确定静态方法名
	-->
	<bean id="bookServiceId" class="com.stanlong.factory.MyBeanFactory" factory-method="createBookService"></bean>
	
</beans>
```

```java
package com.stanlong.service;

public interface BookService {

	public void addBook();
}
```

```java
package com.stanlong.service.impl;

import com.stanlong.service.BookService;

public class BookServiceImpl implements BookService {

	@Override
	public void addBook() {
		System.out.println("c_static_factory addBook()");
	}
}
```

```java
package com.stanlong.factory;

import com.stanlong.service.BookService;
import com.stanlong.service.impl.BookServiceImpl;

public class MyBeanFactory {

	/*
	 * 创建静态工厂
	 */
	public static BookService createBookService(){
		return new BookServiceImpl();
	}
}
```

```java
import com.stanlong.factory.MyBeanFactory;
import com.stanlong.service.BookService;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TestStaticFactory {

	/**
	 * 自定义静态工厂
	 */
	@Test
	public void demo01(){
		 BookService bookService = MyBeanFactory.createBookService();
		 bookService.addBook();
	}
	
	/**
	 * Spring 工厂
	 */
	@Test
	public void demo02(){
		String xmlPath = "applicationContext.xml";
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext(xmlPath);
		BookService bookService = applicationContext.getBean("bookServiceId", BookService.class);
		bookService.addBook();
	}	
}
```

### 实例工厂

实例工厂：必须先有工厂实例对象，通过实例对象创建对象。提供所有的方法都是“非静态”的。

**实现案例**

```java
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
        				   http://www.springframework.org/schema/beans/spring-beans.xsd">
    <!--
    	factory-bean: 确定工厂实例
    	factory-method:确定普通方法 
     -->		   
	<!-- 创建工厂实例 -->
	<bean id="myBeanFactoryId" class="com.stanlong.factory.MyBeanFactory"></bean>
	<!-- 获得 bookService -->
	<bean id="bookServiceId" factory-bean="myBeanFactoryId" factory-method="createBookService"></bean>
</beans>
```

```java
package com.stanlong.d_factory;

public interface BookService {

	public void addBook();
}

```

```java
package com.stanlong.d_factory;

public class BookServiceImpl implements BookService{


	@Override
	public void addBook() {
		System.out.println("d_factory addBook()");
	}
}

```

```java
package com.stanlong.d_factory;

public class MyBeanFactory {

	/*
	 * 创建实例工厂，非静态
	 */
	public BookService createBookService(){
		return new BookServiceImpl();
	}
}
```

```java
package com.stanlong.d_factory;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TestStaticFactory {

	/**
	 * 自定义实例工厂
	 */
	@Test
	public void demo01(){
		//1、创建工厂
		MyBeanFactory myBeanFactory = new MyBeanFactory();
		//2、通过工厂实例，获得对象
		 BookService bookService = myBeanFactory.createBookService(); 
		 bookService.addBook();
	}
	
	/**
	 * Spring 工厂
	 */
	@Test
	public void demo02(){
		String xmlPath = "com/stanlong/d_factory/applicationContext.xml";
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext(xmlPath);
		BookService bookService = applicationContext.getBean("bookServiceId", BookService.class);
		bookService.addBook();
	}	
}
```

## 二、Bean种类

- 普通bean：之前操作的都是普通bean。`<bean id="" class="A">` ，spring直接创建A实例，并返回

- FactoryBean：是一个特殊的bean，具有工厂生成对象能力，只能生成特定的对象。bean必须使用 FactoryBean接口，此接口提供方法 getObject() 用于获得特定bean。

  `<bean id="" class="FB">`  底层源码是先创建FB实例，使用调用getObject()方法，并返回方法的返回值。如：

  ```java
  FB fb = new FB();
  return fb.getObject();
  ```

- BeanFactory 和 FactoryBean 对比？

  BeanFactory：工厂，用于生成任意bean。

  FactoryBean：特殊bean，用于生成另一个特定的bean。例如：ProxyFactoryBean ，此工厂bean用于生产代理。`<bean id="" class="....ProxyFactoryBean">` 获得代理对象实例。AOP使用

## 三、作用域

#### 1）singleton

单例模式，单例，使用 singleton 定义的 Bean 在 Spring 容器中只有一个实例，这也是 Bean 默认的作用域。

#### 2）prototype

原型模式，多例，每次通过 Spring 容器获取 prototype 定义的 Bean 时，容器都将创建一个新的 Bean 实例。

**实现案例**

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
        				   http://www.springframework.org/schema/beans/spring-beans.xsd">
        				   
    <!-- Bean的作用域
    	默认情况 singleton: 单例模式
    	scope = "prototype" : 多例模式
     -->
    <bean id="bookServiceId" class="com.stanlong.e_scope.BookServiceImpl" scope="prototype"></bean>
    
</beans>
```

```java
package com.stanlong.e_scope;

public interface BookService {

	public void addBook();
}
```

```java
package com.stanlong.e_scope;

public class BookServiceImpl implements BookService{

	@Override
	public void addBook() {
		System.out.println("e_scope addBook()");
	}
}
```

```java
package com.stanlong.e_scope;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TestScope {
	
	/**
	 * Bean 的作用域
	 */
	@Test
	public void demo02(){
		String xmlPath = "com/stanlong/e_scope/applicationContext.xml";
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext(xmlPath);
		BookService bookService = applicationContext.getBean("bookServiceId", BookService.class);
		BookService bookService2 = applicationContext.getBean("bookServiceId", BookService.class);
		
		System.out.println(bookService);
		System.out.println(bookService2);
	}	
}
```

#### 3）request

在一次 HTTP 请求中，容器会返回该 Bean 的同一个实例。而对不同的 HTTP 请求，会返回不同的实例，该作用域仅在当前 HTTP Request 内有效。

#### 4）session

在一次 HTTP Session 中，容器会返回该 Bean 的同一个实例。而对不同的 HTTP 请求，会返回不同的实例，该作用域仅在当前 HTTP Session 内有效。

#### 5）global Session

在一个全局的 HTTP Session 中，容器会返回该 Bean 的同一个实例。该作用域仅在使用 portlet context 时有效。

在上述五种作用域中，singleton 和 prototype 是最常用的两种，接下来将对这两种作用域进行详细讲解。

## 四、生命周期

### 1) 初始化和销毁

目标方法执行前或执行后将进行初始化或销毁: `<bean id="" class="" init-method="初始化方法名称" destroy-method="销毁的方法名称">`

### 2) BeanPostProcessor 后处理Bean

- spring 提供一种机制，只要实现此接口BeanPostProcessor，并将实现类提供给spring容器(配置`<bean class="">`)，spring容器将自动执行，在初始化方法前执行before()，在初始化方法后执行after() 。

- Factory hook(勾子) that allows for custom modification of new bean instances, e.g. checking for marker interfaces or wrapping them with proxies.

- spring提供工厂勾子，用于修改实例对象，可以生成代理对象，是AOP底层。

**案例实现**

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
        				   http://www.springframework.org/schema/beans/spring-beans.xsd">
	        				   
	<!-- Bean 的生命周期 -->
	<!--
		 init-method 用于配置初始化方法，准备数据等
		 destroy-method 用于配置销毁方法，清理资源等. 调用销毁方法需要满足两个条件，1、容器必须关闭、2方法必须是单例的
	 -->
	<bean id="bookServiceId" class="com.stanlong.f_lifecycle.BookServiceImpl" init-method="myInit" destroy-method="myDestroy">
	</bean>
	
	<!-- 将后处理实现类注册给Spring -->
	<bean class="com.stanlong.f_lifecycle.MyBeanPostProcessor"></bean>
	   
</beans>
```

```java
package com.stanlong.f_lifecycle;

public interface BookService {

	public void addBook();
}
```

```java
package com.stanlong.f_lifecycle;

public class BookServiceImpl implements BookService{


	@Override
	public void addBook() {
		System.out.println("f_lifecycle addBook()");
	}
	
	public void myInit(){
		System.out.println("初始化方法");
	}
	// 调用销毁方法需要满足两个条件，1、容器必须关闭、2方法必须是单例的
	public void myDestroy(){
		System.out.println("销毁方法");
	}
}

```

```java
package com.stanlong.f_lifecycle;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

public class MyBeanPostProcessor implements BeanPostProcessor {

	/**
	 * BeanPostProcessor 后处理 Bean
	 * Spring 提供一种机制，只要实现此接口 BeanPostProcessor, 并将实现类提供给Spring容器(配置<bean class="">)， Spring容器将自动执行，在初始化方法前执行before
	 * 在初始化方法后执行 after(). 
	 * Spring 提供工厂勾子，用于修改实例对象，可以生成代理对象，是AOP底层
	 */
	@Override
	public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException{
		System.out.println("前方法: " + beanName);
		return bean;
	}
	
	@Override
	public Object postProcessAfterInitialization(final Object bean, String beanName) throws BeansException{
		System.out.println("后方法: " + beanName);
		//生成jdk代理
		return Proxy.newProxyInstance(MyBeanPostProcessor.class.getClassLoader()
					, bean.getClass().getInterfaces()
					, new InvocationHandler() {
						@Override
						public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
							System.out.println("--------------开启事务");
							//执行目标方法
							Object obj = method.invoke(bean, args);
							System.out.println("--------------提交事务");
							return obj;
						}
					});
	}
}
```

```java
package com.stanlong.f_lifecycle;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TestLifeCycle {

	
	/**
	 * Bean 的生命周期
	 */
	@Test
	public void demo01() throws Exception{
		String xmlPath = "com/stanlong/f_lifecycle/applicationContext.xml";
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext(xmlPath);
		BookService bookService = applicationContext.getBean("bookServiceId", BookService.class);
		bookService.addBook();
		
		//调用销毁方法需要满足两个条件，1、容器必须关闭、2方法必须是单例的
		//关闭容器的两种方法：第一种
		applicationContext.getClass().getMethod("close").invoke(applicationContext);
	}
	
	@Test
	public void demo02() throws Exception{
		String xmlPath = "com/stanlong/f_lifecycle/applicationContext.xml";
		ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext(xmlPath);
		BookService bookService = applicationContext.getBean("bookServiceId", BookService.class);
		bookService.addBook();
		
		//调用销毁方法需要满足两个条件，1、容器必须关闭、2方法必须是单例的
		//关闭容器的两种方法：第二种
		applicationContext.close();
	}	
}
```

## 五、属性依赖注入

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
        				   http://www.springframework.org/schema/beans/spring-beans.xsd">
	        				   
	<!-- setter方法注入 
		*普通数据
			<property name="" value""/>
			等效于
			<property name="">
				<value></value>
			</property>
		*引用数据
			<property name="" ref=""/>
			等效于
			<property name="">
				<ref bean="">
			</property>
	-->
	
	<!-- 创建person -->
	<bean id="personId" class="com.stanlong.g_setter.Person">
		<property name="pname" value="stanlong"></property>
		<property name="age">
			<value>28</value>
		</property>
		<property name="homeAddr" ref="homeAddrId"></property>
		<property name="companyAddr">
			<ref bean="companyAddrId"/>
		</property>
	</bean>
	
	<bean id="homeAddrId" class="com.stanlong.g_setter.Address">
		<property name="addr" value="江苏省南京市"></property>
		<property name="tel" value="17512577346"></property>
	</bean>
	<bean id="companyAddrId" class="com.stanlong.g_setter.Address">
		<property name="addr" value="江宁大学城"></property>
		<property name="tel" value="15348247800"></property>
	</bean>
</beans>
```

```java
package com.stanlong.g_setter;

@Setter
@Getter
@ToString
public class Address {

	private String addr;
	private String tel;
}

```

```java
package com.stanlong.g_setter;

@Getter
@Setter
@ToString
public class Person {
	private String pname;
	private Integer age;
	
	private Address homeAddr;
	private Address companyAddr;
}
```

```java
package com.stanlong.g_setter;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TestSetter {

	@Test
	public void demo01(){
		String xmlPath = "com/stanlong/g_setter/applicationContext.xml";
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext(xmlPath);
		Person person = applicationContext.getBean("personId", Person.class);
		System.out.println(person);
	}
}
```



## 六、集合注入

