# 入门案例 HelloWorld

## 一、配置web.xml

注册SpringMVC的前端控制器DispatcherServlet

- 方式1：默认配置方式

  此配置作用下，SpringMVC的配置文件默认位于WEB-INF下，默认名称为`\<servlet-name>-servlet.xml`，例如，以下配置所对应`SpringMVC`的配置文件位于`WEB-INF`下，文件名为`springMVC-servlet.xml`

  ```xml
  <!-- 配置SpringMVC的前端控制器，对浏览器发送的请求统一进行处理 -->
  <servlet>
      <servlet-name>springMVC</servlet-name>
      <servlet-class>org.springframework.web.servlet.DispatcherServlet</servlet-class>
  </servlet>
  <servlet-mapping>
      <servlet-name>springMVC</servlet-name>
      <!--
          设置springMVC的核心控制器所能处理的请求的请求路径
          /所匹配的请求可以是/login或.html或.js或.css方式的请求路径
          但是/不能匹配.jsp请求路径的请求
      -->
      <url-pattern>/</url-pattern>
  </servlet-mapping>
  ```

- 方式二：扩展配置方式（采用）

  可通过`init-param`标签设置`SpringMVC`配置文件的位置和名称，通过`load-on-startup`标签设置SpringMVC前端控制器DispatcherServlet的初始化时间

  ```xml
  <?xml version="1.0" encoding="UTF-8"?>
  <web-app xmlns="http://xmlns.jcp.org/xml/ns/javaee"
           xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
           xsi:schemaLocation="http://xmlns.jcp.org/xml/ns/javaee http://xmlns.jcp.org/xml/ns/javaee/web-app_4_0.xsd"
           version="4.0">
  
      <!-- 配置SpringMVC的前端控制器，对浏览器发送的请求统一进行处理 -->
      <servlet>
          <servlet-name>springMVC</servlet-name>
          <servlet-class>org.springframework.web.servlet.DispatcherServlet</servlet-class>
          <!-- 通过初始化参数指定SpringMVC配置文件的位置和名称 -->
          <init-param>
              <!-- contextConfigLocation为固定值 -->
              <param-name>contextConfigLocation</param-name>
              <!-- 使用classpath:表示从类路径查找配置文件，例如maven工程中的src/main/resources -->
              <param-value>classpath:springMVC.xml</param-value>
          </init-param>
          <!--
               作为框架的核心组件，在启动过程中有大量的初始化操作要做
              而这些操作放在第一次请求时才执行会严重影响访问速度
              因此需要通过此标签将启动控制DispatcherServlet的初始化时间提前到服务器启动时
          -->
          <load-on-startup>1</load-on-startup>
      </servlet>
      <servlet-mapping>
          <servlet-name>springMVC</servlet-name>
          <!--
              设置springMVC的核心控制器所能处理的请求的请求路径
              /所匹配的请求可以是/login或.html或.js或.css方式的请求路径
              但是/不能匹配.jsp请求路径的请求
          -->
          <url-pattern>/</url-pattern>
      </servlet-mapping>
  
  </web-app>
  ```

  > 注：
  >
  > \<url-pattern>标签中使用/和/*的区别：
  >
  > /所匹配的请求可以是/login或.html或.js或.css方式的请求路径，但是/不能匹配.jsp请求路径的请求
  >
  > 因此就可以避免在访问jsp页面时，该请求被DispatcherServlet处理，从而找不到相应的页面
  >
  > /*则能够匹配所有请求，例如在使用过滤器时，若需要对所有请求进行过滤，就需要使用/\*的写法

## 二、创建请求控制器

由于前端控制器对浏览器发送的请求进行了统一的处理，但是具体的请求有不同的处理过程，因此需要创建处理具体请求的类，即请求控制器

请求控制器中每一个处理请求的方法成为控制器方法

因为SpringMVC的控制器由一个POJO（普通的Java类）担任，因此需要通过@Controller注解将其标识为一个控制层组件，交给Spring的IoC容器管理，此时SpringMVC才能够识别控制器的存在

```java
package com.stanlong.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/** 
 * 在请求控制器中创建处理请求的方法
 */
@Controller
public class HelloController {
    // @RequestMapping注解：处理请求和控制器方法之间的映射关系
    // @RequestMapping注解的value属性可以通过请求地址匹配请求，/表示的当前工程的上下文路径
    // localhost:8080/SpringMVC/

    @RequestMapping("/") // "/" --> /WEB-INF/html/index.html, 由 thymeleaf 模板拼接视图的前后缀
    public String index() {
        //设置视图名称
        return "index";
    }

    @RequestMapping("/hello")
    public String HelloWorld() {
        return "target";
    }
}
```

## 三、创建springMVC的配置文件

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:context="http://www.springframework.org/schema/context"
       xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd http://www.springframework.org/schema/context https://www.springframework.org/schema/context/spring-context.xsd">

    <!-- 自动扫描包 -->
    <context:component-scan base-package="com.stanlong.controller"></context:component-scan>

    <!-- 配置Thymeleaf视图解析器 -->
    <bean id="viewResolver" class="org.thymeleaf.spring5.view.ThymeleafViewResolver">
        <property name="order" value="1"/>
        <property name="characterEncoding" value="UTF-8"/>
        <property name="templateEngine">
            <bean class="org.thymeleaf.spring5.SpringTemplateEngine">
                <property name="templateResolver">
                    <bean class="org.thymeleaf.spring5.templateresolver.SpringResourceTemplateResolver">

                        <!-- 视图前缀 -->
                        <property name="prefix" value="/WEB-INF/html/"/>

                        <!-- 视图后缀 -->
                        <property name="suffix" value=".html"/>
                        <property name="templateMode" value="HTML5"/>
                        <property name="characterEncoding" value="UTF-8" />
                    </bean>
                </property>
            </bean>
        </property>
    </bean>
</beans>
```

## 五、测试HelloWorld

- **index.html**

  ```jsp
  <!DOCTYPE html>
  <html lang="en" xmlns:th="http://www.thymeleaf.org">
  <head>
      <meta charset="UTF-8">
      <title>首页</title>
  </head>
  <body>
      <h1>首页</h1>
      <a th:href="@{/hello}">HelloWorld</a><br/> <!-- 发送请求 -->
  </body>
  </html>
  ```

- **target.html**

  ```html
  <!DOCTYPE html>
  <html lang="en" xmlns:th="http://www.thymeleaf.org">
  <head>
      <meta charset="UTF-8">
      <title>首页</title>
  </head>
  <body>
  <h1>Hello SpringMVC</h1>
  </body>
  </html>
  ```

## 六、总结

- 浏览器发送请求，若请求地址符合前端控制器的url-pattern，该请求就会被前端控制器DispatcherServlet处理。
- 前端控制器会读取SpringMVC的核心配置文件。通过扫描组件找到控制器，将请求地址和控制器中`@RequestMapping`注解的value属性值进行匹配，若匹配成功，该注解所标识的控制器方法就是处理请求的方法。
- 处理请求的方法需要返回一个字符串类型的视图名称，该视图名称会被视图解析器解析，加上前缀和后缀组成视图的路径，通过Thymeleaf对视图进行渲染，最终转发到视图所对应页面

