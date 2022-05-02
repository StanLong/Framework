## 58、嵌入式Servlet容器-【源码分析】切换web服务器与定制化

- 默认支持的WebServer

  - `Tomcat`, `Jetty`, or `Undertow`。
  - `ServletWebServerApplicationContext `容器启动寻找`ServletWebServerFactory` 并引导创建服务器。

- 原理

  - SpringBoot应用启动发现当前是Web应用，web场景包-导入tomcat。
  - web应用会创建一个web版的IOC容器 `ServletWebServerApplicationContext` 。
  - `ServletWebServerApplicationContext`  启动的时候寻找 `ServletWebServerFactory` （Servlet 的web服务器工厂——>Servlet 的web服务器）。
  - SpringBoot底层默认有很多的WebServer工厂（`ServletWebServerFactoryConfiguration`内创建Bean），如：
    - `TomcatServletWebServerFactory`
    - `JettyServletWebServerFactory`
    - `UndertowServletWebServerFactory`
  - 底层直接会有一个自动配置类`ServletWebServerFactoryAutoConfiguration`。
  - `ServletWebServerFactoryAutoConfiguration`导入了`ServletWebServerFactoryConfiguration`（配置类）。
  - `ServletWebServerFactoryConfiguration  `根据动态判断系统中到底导入了那个Web服务器的包。（默认是web-starter导入tomcat包），容器中就有 `TomcatServletWebServerFactory`
  - `TomcatServletWebServerFactory `创建出Tomcat服务器并启动；`TomcatWebServer` 的构造器拥有初始化方法initialize——`this.tomcat.start();`
  - 内嵌服务器，与以前手动把启动服务器相比，改成现在使用代码启动（tomcat核心jar包存在）。



Spring Boot默认使用Tomcat服务器，若需更改其他服务器，则修改工程pom.xml：

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-web</artifactId>
    <exclusions>
        <exclusion>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-tomcat</artifactId>
        </exclusion>
    </exclusions>
</dependency>

<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-jetty</artifactId>
</dependency>

```

[官方文档 - Use Another Web Server](https://docs.spring.io/spring-boot/docs/2.4.2/reference/htmlsingle/#howto-use-another-web-server)

### 定制Servlet容器

- 实现`WebServerFactoryCustomizer<ConfigurableServletWebServerFactory>` 

- - 把配置文件的值和`ServletWebServerFactory`进行绑定

- 修改配置文件 `server.xxx`
- 直接自定义 `ConfigurableServletWebServerFactory`

`xxxxxCustomizer`：定制化器，可以改变xxxx的默认规则

```java
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.boot.web.servlet.server.ConfigurableServletWebServerFactory;
import org.springframework.stereotype.Component;

@Component
public class CustomizationBean implements WebServerFactoryCustomizer<ConfigurableServletWebServerFactory> {

    @Override
    public void customize(ConfigurableServletWebServerFactory server) {
        server.setPort(9000);
    }

}
```



## 59、定制化原理-SpringBoot定制化组件的几种方式（小结）

### 定制化的常见方式 

- 修改配置文件

- `xxxxxCustomizer`

- 编写自定义的配置类  `xxxConfiguration` + `@Bean`替换、增加容器中默认组件，视图解析器

- Web应用 编写一个配置类实现 `WebMvcConfigurer` 即可定制化web功能 + `@Bean`给容器中再扩展一些组件

```java
@Configuration
public class AdminWebConfig implements WebMvcConfigurer{
}
```

- `@EnableWebMvc` + `WebMvcConfigurer` — `@Bean`  可以全面接管SpringMVC，所有规则全部自己重新配置； 实现定制和扩展功能（**高级功能，初学者退避三舍**）。
  - 原理：
    1. `WebMvcAutoConfiguration`默认的SpringMVC的自动配置功能类，如静态资源、欢迎页等。
    2. 一旦使用 `@EnableWebMvc` ，会`@Import(DelegatingWebMvcConfiguration.class)`。
    3. `DelegatingWebMvcConfiguration`的作用，只保证SpringMVC最基本的使用
       - 把所有系统中的`WebMvcConfigurer`拿过来，所有功能的定制都是这些`WebMvcConfigurer`合起来一起生效。
       - 自动配置了一些非常底层的组件，如`RequestMappingHandlerMapping`，这些组件依赖的组件都是从容器中获取如。
       - `public class DelegatingWebMvcConfiguration extends WebMvcConfigurationSupport`。
    4. `WebMvcAutoConfiguration`里面的配置要能生效必须  `@ConditionalOnMissingBean(WebMvcConfigurationSupport.class)`。
    5. @EnableWebMvc 导致了WebMvcAutoConfiguration  没有生效。



### 原理分析套路

场景starter - `xxxxAutoConfiguration` - 导入xxx组件 - 绑定`xxxProperties` - 绑定配置文件项。