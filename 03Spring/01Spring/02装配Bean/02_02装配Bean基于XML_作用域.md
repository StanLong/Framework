# 装配Bean基于XML

## 作用域

### 1）singleton

单例模式，单例，使用 singleton 定义的 Bean 在 Spring 容器中只有一个实例，这也是 Bean 默认的作用域。

### 2）prototype

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

### 3）request

在一次 HTTP 请求中，容器会返回该 Bean 的同一个实例。而对不同的 HTTP 请求，会返回不同的实例，该作用域仅在当前 HTTP Request 内有效。

### 4）session

在一次 HTTP Session 中，容器会返回该 Bean 的同一个实例。而对不同的 HTTP 请求，会返回不同的实例，该作用域仅在当前 HTTP Session 内有效。

### 5）global Session

在一个全局的 HTTP Session 中，容器会返回该 Bean 的同一个实例。该作用域仅在使用 portlet context 时有效。

在上述五种作用域中，singleton 和 prototype 是最常用的两种，接下来将对这两种作用域进行详细讲解。
