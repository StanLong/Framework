# 装配Bean基于XML

## 生命周期

### 1) 初始化和销毁

目标方法执行前或执行后将进行初始化或销毁: `<bean id="" class="" init-method="初始化方法名称" destroy-method="销毁的方法名称">`

### 2) BeanPostProcessor 后处理Bean

- spring 提供一种机制，只要实现此接口BeanPostProcessor，并将实现类提供给spring容器(配置`<bean class="">`)，spring容器将自动执行，在初始化方法前执行before()，在初始化方法后执行after() 。
- Factory hook(勾子) that allows for custom modification of new bean instances, e.g. checking for marker interfaces or wrapping them with proxies.
- spring提供工厂勾子，用于修改实例对象，可以生成代理对象，是AOP底层。
-  问题1：后处理bean作用某一个目标类，还是所有目标类？， 所有
- 问题2：如何只作用一个？ 通过参数 `beanName` 进行控制

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
	<bean id="bookServiceId" class="com.stanlong.service.impl.BookServiceImpl" init-method="myInit" destroy-method="myDestroy">
	</bean>

	<!-- 将后处理实现类注册给Spring -->
	<bean class="com.stanlong.processor.MyBeanPostProcessor"></bean>
	
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
package com.stanlong.processor;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.cglib.proxy.InvocationHandler;
import org.springframework.cglib.proxy.Proxy;

import java.lang.reflect.Method;


/**
 * BeanPostProcessor 后处理 Bean
 * Spring 提供一种机制，只要实现此接口 BeanPostProcessor, 并将实现类提供给Spring容器(配置<bean class="">)， Spring容器将自动执行，在初始化方法前执行before
 * 在初始化方法后执行 after().
 * Spring 提供工厂勾子，用于修改实例对象，可以生成代理对象，是AOP底层
 */
public class MyBeanPostProcessor implements BeanPostProcessor {

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
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
import com.stanlong.service.BookService;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TestLifeCycle {
    /**
     * Bean 的生命周期
     */
    @Test
    public void demo01() throws Exception{
        String xmlPath = "applicationContext.xml";
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext(xmlPath);
        BookService bookService = applicationContext.getBean("bookServiceId", BookService.class);
        bookService.addBook();

        //调用销毁方法需要满足两个条件，1、容器必须关闭、2方法必须是单例的
        //关闭容器的两种方法：第一种
        applicationContext.getClass().getMethod("close").invoke(applicationContext);
    }

    @Test
    public void demo02() throws Exception{
        String xmlPath = "applicationContext.xml";
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext(xmlPath);
        BookService bookService = applicationContext.getBean("bookServiceId", BookService.class);
        bookService.addBook();

        //调用销毁方法需要满足两个条件，1、容器必须关闭、2方法必须是单例的
        //关闭容器的两种方法：第二种
        applicationContext.close();
    }
}
```

```
控制台打印：

前方法: bookServiceId
初始化方法
后方法: bookServiceId
--------------开启事务
f_lifecycle addBook()
--------------提交事务
销毁方法
```

