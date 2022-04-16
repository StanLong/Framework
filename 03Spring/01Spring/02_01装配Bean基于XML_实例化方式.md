# 装配Bean基于XML

## 实例化方式

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




