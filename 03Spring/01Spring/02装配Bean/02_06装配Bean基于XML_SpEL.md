# 装配Bean基于XML

## SpEL

对` <property>` 进行统一编程，所有的内容都使用value

` <property name="" value="#{表达式}"> `

`#{123}、#{'jack'} `： 数字、字符串

`#{beanId} `：另一个bean引用

`#{beanId.propName}` ：操作数据

`#{beanId.toString()} `：执行方法

`#{T(类).字段|方法}` ：静态方法或字段

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:p="http://www.springframework.org/schema/p"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
        				   http://www.springframework.org/schema/beans/spring-beans.xsd">
	        				   
	<!-- Spel: Spring EL 表达式
		<property name="" value="#{表达示}">	
		#{123}，#{"abc"} 数字，字符串
		#{beanId} 另一个bean的引用
		#{beanId.propName} 操作数据
		#{beanId.toString} 执行方法
		#{T(类).字段|方法} 静态方法或字段
		# <property name="cname" value="#{customerId.cname?.toUpperCase()}"></property>  ?. 如果对象不为null，将调用方法
	-->
	<bean id="customerId" class="com.stanlong.i_spel.Customer">
		<property name="cname" value="#{customerId.cname.toUpperCase()}"></property>
		<property name="pi" value="#{T(java.lang.Math).PI}"></property>
	</bean>
</beans>
```

```java
package com.stanlong.bean;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Customer {

	private String cname ="lisi";
	private Double pi;
}

```

```java
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TestSpEL {

	@Test
	public void demo01(){
		String xmlPath = "applicationContext.xml";
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext(xmlPath);
		Customer customer = applicationContext.getBean("customerId", Customer.class);
		System.out.println(customer);
	}
}

```

