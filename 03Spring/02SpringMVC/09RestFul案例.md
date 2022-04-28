# RESTful案例

和传统 CRUD 一样，实现对员工信息的增删改查。

## 1、环境搭建

配置pom.xml

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/maven-v4_0_0.xsd">

  <modelVersion>4.0.0</modelVersion>
  <packaging>war</packaging>

  <name>SpringMVC</name>
  <groupId>org.example</groupId>
  <artifactId>SpringMVC</artifactId>
  <version>1.0-SNAPSHOT</version>

  <dependencies>
    <!-- SpringMVC -->
    <dependency>
      <groupId>org.springframework</groupId>
      <artifactId>spring-webmvc</artifactId>
      <version>5.3.19</version>
    </dependency>

    <!-- 日志 -->
    <dependency>
      <groupId>ch.qos.logback</groupId>
      <artifactId>logback-classic</artifactId>
      <version>1.2.3</version>
    </dependency>

    <!-- ServletAPI -->
    <dependency>
      <groupId>javax.servlet</groupId>
      <artifactId>javax.servlet-api</artifactId>
      <version>3.1.0</version>
      <scope>provided</scope> <!-- 表示servlet已由服务器提供 -->
    </dependency>

    <!-- Spring5和Thymeleaf整合包 -->
    <dependency>
      <groupId>org.thymeleaf</groupId>
      <artifactId>thymeleaf-spring5</artifactId>
      <version>3.0.12.RELEASE</version>
    </dependency>

    <dependency>
      <groupId>org.projectlombok</groupId>
      <artifactId>lombok</artifactId>
      <version>RELEASE</version>
      <scope>compile</scope>
    </dependency>
  </dependencies>

</project>
```

配置web.xml

```xml
<?xml version="1.0" encoding="UTF-8"?>
<web-app xmlns="http://xmlns.jcp.org/xml/ns/javaee"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://xmlns.jcp.org/xml/ns/javaee http://xmlns.jcp.org/xml/ns/javaee/web-app_4_0.xsd"
         version="4.0">

    <!--配置springMVC的编码过滤器, 这个要配置在所有过滤器的前面，否则不生效-->
    <filter>
        <filter-name>CharacterEncodingFilter</filter-name>
        <filter-class>org.springframework.web.filter.CharacterEncodingFilter</filter-class>
        <init-param>
            <param-name>encoding</param-name>
            <param-value>UTF-8</param-value>
        </init-param>
        <init-param>
            <param-name>forceResponseEncoding</param-name>
            <param-value>true</param-value>
        </init-param>
    </filter>
    <filter-mapping>
        <filter-name>CharacterEncodingFilter</filter-name>
        <url-pattern>/*</url-pattern>
    </filter-mapping>

    <!--   配置将 POST 请求转换为 DELETE 或 PUT 请求 的过滤器 HiddenHttpMethodFilter   -->
    <filter>
        <filter-name>HiddenHttpMethodFilter</filter-name>
        <filter-class>org.springframework.web.filter.HiddenHttpMethodFilter</filter-class>
    </filter>
    <filter-mapping>
        <filter-name>HiddenHttpMethodFilter</filter-name>
        <url-pattern>/*</url-pattern>
    </filter-mapping>

    <!-- 配置SpringMVC的前端控制器，对浏览器发送的请求统一进行处理 -->
    <servlet>
        <servlet-name>DispatcherServlet</servlet-name>
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
        <servlet-name>DispatcherServlet</servlet-name>
        <!--
            设置springMVC的核心控制器所能处理的请求的请求路径
            /所匹配的请求可以是/login或.html或.js或.css方式的请求路径
            但是/不能匹配.jsp请求路径的请求
        -->
        <url-pattern>/</url-pattern>
    </servlet-mapping>
</web-app>
```

配置springMVC.xml

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:context="http://www.springframework.org/schema/context"
       xmlns:mvc="http://www.springframework.org/schema/mvc"
       xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd http://www.springframework.org/schema/context https://www.springframework.org/schema/context/spring-context.xsd
       http://www.springframework.org/schema/mvc http://www.springframework.org/schema/mvc/spring-mvc.xsd">

    <!-- 自动扫描包, 配置扫描 controller 和 dao 下的包 -->
    <context:component-scan base-package="com.stanlong.controller, com.stanlong.dao"></context:component-scan>

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

    <!--
       配置视图控制器
        path：设置处理的请求地址
        view-name：设置请求地址所对应的视图名称
    -->
    <mvc:view-controller path="/" view-name="index"/> <!-- 配置访问首页 -->
    <mvc:view-controller path="/toAdd" view-name="employee_add"></mvc:view-controller>  <!-- 配置跳转到新增员工页面 -->

    <!--
        处理静态资源，例如html、js、css、jpg。 若只设置该标签，则只能访问静态资源，其他请求则无法访问
        此时必须设置<mvc:annotation-driven/>解决问题
    -->
    <mvc:default-servlet-handler/>

    <!-- 开启mvc注解驱动。使用了 mvc:view-controller 这个标签后必须配置mvc:annotation-driven， 否则会造成所有的@Controller注解无法解析，导致404错误   -->
    <mvc:annotation-driven />

</beans>
```

## 2、准备工作

- 准备实体类

  ```java
  package com.stanlong.bean;
  
  import lombok.Getter;
  import lombok.NonNull;
  import lombok.RequiredArgsConstructor;
  import lombok.Setter;
  
  @Getter
  @Setter
  @RequiredArgsConstructor
  public class Employee {
      @NonNull
      private Integer id;
      @NonNull
      private String lastName;
  
      @NonNull
      private String email;
      //1 male, 0 female
      @NonNull
      private Integer gender;
      public Employee() {
      }
  }
  ```

- 准备dao模拟数据

  ```java
  package com.stanlong.dao;
  
  import com.stanlong.bean.Employee;
  import org.springframework.stereotype.Repository;
  
  import java.util.Collection;
  import java.util.HashMap;
  import java.util.Map;
  
  @Repository
  public class EmployeeDao {
  
      private static Map<Integer, Employee> employees = null;
  
      static{
          employees = new HashMap<Integer, Employee>();
  
          employees.put(1001, new Employee(1001, "E-AA", "aa@163.com", 1));
          employees.put(1002, new Employee(1002, "E-BB", "bb@163.com", 1));
          employees.put(1003, new Employee(1003, "E-CC", "cc@163.com", 0));
          employees.put(1004, new Employee(1004, "E-DD", "dd@163.com", 0));
          employees.put(1005, new Employee(1005, "E-EE", "ee@163.com", 1));
      }
  
      private static Integer initId = 1006;
  
      public void save(Employee employee){
          if(employee.getId() == null){
              employee.setId(initId++);
          }
          employees.put(employee.getId(), employee);
      }
  
      public Collection<Employee> getAll(){
          return employees.values();
      }
  
      public Employee get(Integer id){
          return employees.get(id);
      }
  
      public void delete(Integer id){
          employees.remove(id);
      }
  }
  ```

## 3、功能清单

| 功能                | URL 地址    | 请求方式 |
| ------------------- | ----------- | -------- |
| 访问首页√           | /           | GET      |
| 查询全部数据√       | /employee   | GET      |
| 删除√               | /employee/2 | DELETE   |
| 跳转到添加数据页面√ | /toAdd      | GET      |
| 执行保存√           | /employee   | POST     |
| 跳转到更新数据页面√ | /employee/2 | GET      |
| 执行更新√           | /employee   | PUT      |

- Controller层编写

  ```java
  package com.stanlong.controller;
  
  import com.stanlong.bean.Employee;
  import com.stanlong.dao.EmployeeDao;
  import org.springframework.beans.factory.annotation.Autowired;
  import org.springframework.stereotype.Controller;
  import org.springframework.ui.Model;
  import org.springframework.web.bind.annotation.PathVariable;
  import org.springframework.web.bind.annotation.RequestMapping;
  import org.springframework.web.bind.annotation.RequestMethod;
  
  import java.util.Collection;
  
  @Controller
  public class EmployeeController {
  
      private EmployeeDao employeeDao;
  
      @Autowired // 注入 employeeDao
      public void setEmployeeDao(EmployeeDao employeeDao) {
          this.employeeDao = employeeDao;
      }
  
      // 具体功能：查询所有员工数据
      @RequestMapping(value = "/employee", method = RequestMethod.GET)
      public String queryAll(Model model){
          Collection<Employee> employeeList = employeeDao.getAll();
          model.addAttribute("employeeList", employeeList);
          return "employee_list";
      }
  
      // 根据删除员工
      @RequestMapping(value = "/employee/{id}", method = RequestMethod.DELETE)
      public String deleteById(@PathVariable("id") Integer id){
          employeeDao.delete(id);
          return "redirect:/employee"; // redirect 相当于浏览器重新发送一个新的请求
      }
  
  	// 具体功能：执行保存
      @RequestMapping(value = "/employee", method = RequestMethod.POST)
      public String addEmployee(Employee employee){
          employeeDao.save(employee);
          return "redirect:/employee";
      }
  
      // 根据id查询员工
      @RequestMapping(value = "/employee/{id}", method = RequestMethod.GET)
      public String queryById(@PathVariable("id") Integer id, Model model){
          Employee employee = employeeDao.get(id);
          model.addAttribute("employee", employee);
          return "employee_update";
      }
  
      // 修改员工信息
      @RequestMapping(value = "/employee", method = RequestMethod.PUT)
      public String update(Employee employee){
          employeeDao.save(employee);
          return "redirect:/employee";
      }
  }
  ```

## 4、页面

- **index.html**

```html
<!DOCTYPE html>
<html lang="en" xmlns:th="http://www.thymeleaf.org">
<head>
    <meta charset="UTF-8">
    <title>首页</title>
</head>
<body>
<h1>首页</h1>
<a th:href="@{/employee}">访问员工信息</a>
</body>
</html>
```

- **employee_list.html**

```html
<!DOCTYPE html>
<html lang="en" xmlns:th="http://www.thymeleaf.org">
<head>
    <meta charset="UTF-8">
    <title>员工信息</title>
    <!-- 引入vue.js处理点击事件，目录结构 /webapp/static/js ， 引入静态资源后需要用maven重新打包   -->
    <script type="text/javascript" th:src="@{/static/js/vue-2.4.0.js}"></script>
</head>
<body>

    <table border="1" cellpadding="0" cellspacing="0" style="text-align: center;" id="dataTable">
        <tr>
            <th colspan="5">Employee Info</th>
        </tr>
        <tr>
            <th>id</th>
            <th>lastName</th>
            <th>email</th>
            <th>gender</th>
            <th>options(<a th:href="@{/toAdd}">add</a>)</th>
        </tr>
        <tr th:each="employee : ${employeeList}">
            <td th:text="${employee.id}"></td>
            <td th:text="${employee.lastName}"></td>
            <td th:text="${employee.email}"></td>
            <td th:text="${employee.gender}"></td>
            <td>
                <a class="deleteA" @click="deleteEmployee" th:href="@{'/employee/'+${employee.id}}">delete</a>
                <a th:href="@{'/employee/'+${employee.id}}">update</a> <!-- 修改超链接 -->
            </td>
        </tr>
    </table>


    <!-- 创建处理delete请求方式的表单 -->
    <!-- 作用：通过超链接控制表单的提交，将post请求转换为delete请求 -->
    <form id="delete_form" method="post">
        <!-- HiddenHttpMethodFilter要求：必须传输_method请求参数，并且值为最终的请求方式 -->
        <input type="hidden" name="_method" value="delete"/>
    </form>


    <!-- 删除超链接绑定点击事件 -->
    <!-- 通过vue处理点击事件    -->
    <script type="text/javascript">
        var vue = new Vue({
            el:"#dataTable",
            methods:{
                //event表示当前事件
                deleteEmployee:function (event) {
                    //通过id获取表单标签
                    var delete_form = document.getElementById("delete_form");
                    //将触发事件的超链接的href属性为表单的action属性赋值
                    delete_form.action = event.target.href;
                    //提交表单
                    delete_form.submit();
                    //阻止超链接的默认跳转行为
                    event.preventDefault();
                }
            }
        });
    </script>
</body>
</html>
```

- **employee_add.html**

```html
<!DOCTYPE html>
<html lang="en" xmlns:th="http://www.thymeleaf.org">
<head>
    <meta charset="UTF-8">
    <title>新增员工</title>
</head>
<body>

<form th:action="@{/employee}" method="post">
    lastName:<input type="text" name="lastName"><br>
    email:<input type="text" name="email"><br>
    gender:<input type="radio" name="gender" value="1">male
    <input type="radio" name="gender" value="0">female<br>
    <input type="submit" value="add"><br>
</form>

</body>
</html>
```

- **employee_update.html**

```html
<!DOCTYPE html>
<html lang="en" xmlns:th="http://www.thymeleaf.org">
<head>
    <meta charset="UTF-8">
    <title>修改员工信息</title>
</head>
<body>

<form th:action="@{/employee}" method="post">
    <input type="hidden" name="_method" value="put">
    <input type="hidden" name="id" th:value="${employee.id}">
    lastName:<input type="text" name="lastName" th:value="${employee.lastName}"><br>
    email:<input type="text" name="email" th:value="${employee.email}"><br>
    <!--
        th:field="${employee.gender}"可用于单选框或复选框的回显
        若单选框的value和employee.gender的值一致，则添加checked="checked"属性
    -->
    gender:<input type="radio" name="gender" value="1" th:field="${employee.gender}">male
    <input type="radio" name="gender" value="0" th:field="${employee.gender}">female<br>
    <input type="submit" value="update"><br>
</form>

</body>
</html>
```

