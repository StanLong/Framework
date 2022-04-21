# 装配Bean基于XML

## 属性依赖注入

有两种方式，通过方法构造方法注入，通过Setter方法注入。

### 构造方法注入

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
        				   http://www.springframework.org/schema/beans/spring-beans.xsd">

	<!-- 构造方法注入
			<constructor-arg> 用于配置构造方法
				name:参数的名称
				value:设置普通数据
				ref: 引用数据，一般是另一个bean的id值
				index: 参数的索引号， 从0开始 可以替换 name
				type: 确定参数类型
			方式一：使用名称name
				<constructor-arg name="username" value="张三"></constructor-arg>
				<constructor-arg name="age" value="10"></constructor-arg>
			方式二: 使用index和类型type
	-->
	<bean id="userId" class="com.stanlong.bean.User">
		<constructor-arg index="0" value="张三" type="java.lang.String"></constructor-arg>
		<constructor-arg index="1" value="10" type="java.lang.Integer"></constructor-arg>
	</bean>
	
</beans>
```

```java
package com.stanlong.bean;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class User {
    private Integer uid;
    private String username;
    private Integer age;

    public User(String username, Integer age){
        super();
        this.username = username;
        this.age = age;
    }

    public User(Integer uid, String username){
        super();
        this.uid = uid;
        this.username = username;
    }
}
```

```java
import com.stanlong.bean.User;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TestCons {

    @Test
    public void demo01(){
        String xmlPath = "applicationContext.xml";
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext(xmlPath);
        User user = (User) applicationContext.getBean("userId");
        System.out.println(user);

    }
}
```

### Setter方法注入

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
package com.stanlong.bean;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Address {
    private String addr;
    private String tel;
}
```

```java
package com.stanlong.bean;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

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
import com.stanlong.bean.Person;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TestSetter {
    @Test
    public void demo01(){
        String xmlPath = "applicationContext.xml";
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext(xmlPath);
        Person person = applicationContext.getBean("personId", Person.class);
        System.out.println(person);
    }
}
```

