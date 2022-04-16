# 装配Bean基于XML

## P命名空间

- 对“setter方法注入”进行简化，替换` <property name="属性名">` ，而是在

  ` <bean p:属性名="普通值" p:属性名-ref="引用值">`

- p命名空间使用前提，必须添加命名空间 ` xmlns:p="http://www.springframework.org/schema/p"`

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:p="http://www.springframework.org/schema/p"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
        				   http://www.springframework.org/schema/beans/spring-beans.xsd">
	        				   
	<!-- p命名空间
		引入p命名空间
		xmlns:p="http://www.springframework.org/schema/p"
	-->
	<bean id="personId" class="com.stanlong.bean.Person" 
		p:pname="zhangsan" 
		p:age="18"
		p:homeAddr-ref="homeAddressId"
		p:companyAddr-ref="companyAddressId">
	</bean>
	
	<bean id="homeAddressId" class="com.stanlong.bean.Address"
		p:addr="火星"
		p:tel="911">
	</bean>
	
	<bean id="companyAddressId" class="com.stanlong.bean.Address"
		p:addr="地球"
		p:tel="120"
	></bean>
	
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
package com.stanlong.h_p_name;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TestP {

	@Test
	public void demo01(){
		String xmlPath = "applicationContext.xml";
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext(xmlPath);
		Person person = applicationContext.getBean("personId", Person.class);
		System.out.println(person);
	}
}
```

