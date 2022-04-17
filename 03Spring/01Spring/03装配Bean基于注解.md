# 装配Bean基于注解

## @Component

- @Component取代 ` <bean class=""> `
- @Component("id") 取代  `<bean id="" class=""> `

**实现案例**

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
	   xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	   xmlns:context="http://www.springframework.org/schema/context"
	   xmlns:p="http://www.springframework.org/schema/p"
	   xsi:schemaLocation="http://www.springframework.org/schema/beans
        				   http://www.springframework.org/schema/beans/spring-beans.xsd
        				   http://www.springframework.org/schema/context
        				   http://www.springframework.org/schema/context/spring-context.xsd">

	<!-- Spring 注解 -->
	<!-- 引入Spring 注解
		xmlns:context="http://www.springframework.org/schema/context"
		xsi:schemaLocation="http://www.springframework.org/schema/context http://www.springframework.org/schema/context/spring-context.xsd"
	-->

	<!-- 开启注解扫描,扫描含有注释的类 -->
	<context:component-scan base-package="com.stanlong.service"></context:component-scan>
</beans>
```

```java
package com.stanlong.service;

public interface UserService {

	public void addUser();
}
```

```java
package com.stanlong.service.impl;

import com.stanlong.service.UserService;
import org.springframework.stereotype.Component;

@Component("userServiceId")
public class UserServiceImpl implements UserService{
    @Override
    public void addUser() {
        System.out.println("g_annotation.a_ioc add user");
    }
}
```

```java
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TestAnnotation {

	@Test
	public void demo01(){
		String xmlPath = "applicationContext.xml";
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext(xmlPath);
		UserService userService = applicationContext.getBean("userServiceId", UserService.class);
		userService.addUser();
	}
}
```

## web开发

web开发提供3个@Component注解的衍生注解（功能一样）取代`<bean class="">`

@Repository ：dao层

@Service：service层

@Controller：web层

## 依赖注入

可以给私有字段设置，也可以给setter方法设置

普通值：@Value("")

引用值：

- 方式1：按照【类型】注入 

  @Autowired

- 方式2：按照【名称】注入1

  @Autowired

  @Qualifier("名称")

- 方式3：按照【名称】注入2

  @Resource("名称")

## 生命周期

- 初始化：@PostConstruct
- 销毁：@PreDestroy

## 作用域

- @Scope("prototype") 多例

**案例实现**

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
	   xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	   xmlns:context="http://www.springframework.org/schema/context"
	   xmlns:p="http://www.springframework.org/schema/p"
	   xsi:schemaLocation="http://www.springframework.org/schema/beans
        				   http://www.springframework.org/schema/beans/spring-beans.xsd
        				   http://www.springframework.org/schema/context
        				   http://www.springframework.org/schema/context/spring-context.xsd">

	<!-- 开启注解扫描,扫描含有注释的类 -->
	<context:component-scan base-package="com.stanlong.dao"></context:component-scan>
	<context:component-scan base-package="com.stanlong.service"></context:component-scan>
	<context:component-scan base-package="com.stanlong.action"></context:component-scan>
</beans>
```

```java
package com.stanlong.action;

import com.stanlong.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller("studentActionId")
// @Scope("prototype") 多例
public class StudentAction {


	private StudentService studentService;

	@Autowired  // Setter 方法注入
	private void setStudentService(StudentService studentService){
		this.studentService = studentService;
	}

	public void execute(){
		studentService.addStudent();
	}
}
```

```java
package com.stanlong.service;

public interface StudentService {
	
	public void addStudent();
}
```

```java
package com.stanlong.service.impl;

import com.stanlong.dao.StudentDao;
import com.stanlong.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

@Service
public class StudentServiceImpl implements StudentService {

	private StudentDao studentDao;
	
	@Autowired
	@Qualifier("studentDaoId")
	public void setStudentDao(StudentDao studentDao) {
		this.studentDao = studentDao;
	}
	
	@Override
	public void addStudent() {
		studentDao.addStudent();
	}

	@PostConstruct // 初始化注解
	public void myInit(){
		System.out.println("初始化方法");
	}

	@PreDestroy // 销毁注解
	public void myDestroy(){
		System.out.println("销毁方法");
	}	
}
```

```java
package com.stanlong.dao;

public interface StudentDao {

	public void addStudent();
}
```

```java
package com.stanlong.dao.impl;

import com.stanlong.dao.StudentDao;
import org.springframework.stereotype.Repository;

@Repository("studentDaoId")
public class StudentDaoImpl implements StudentDao {

	@Override
	public void addStudent() {
		System.out.println("l_web_annotation addStudent()");
	}
}
```

```java
import com.stanlong.action.StudentAction;
import org.junit.jupiter.api.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TestWebAnnotation {

	@Test
	public void demo01(){
		String xmlPath = "applicationContext.xml";
		ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext(xmlPath);
		StudentAction studentAction = applicationContext.getBean("studentActionId", StudentAction.class);

		studentAction.execute();
		/* 验证 @Scope("prototype")
		StudentAction studentAction2 = applicationContext.getBean("studentActionId", StudentAction.class);
		System.out.println(studentAction1);
		System.out.println(studentAction2);*/
		applicationContext.close();

	}
}

// 注意 ClassPathXmlApplicationContext 有close方法，而ApplicationContext没有close方法
```

