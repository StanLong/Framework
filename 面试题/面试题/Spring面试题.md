# Spring 面试题

**1.谈谈自己对于Spring IOC和AOP的理解**

IOC（Inversion Of Controll，控制反转）是一种设计思想，就是将原本在程序中手动创建对象的控制权，交由给Spring框架来管理。

AOP（Aspect-Oriented Programming，面向切面编程）能够将那些与业务无关，却为业务模块所共同调用的逻辑或责任（例如事务处理、日志管理、权限控制等）封装起来，便于减少系统的重复代码，降低模块间的耦合度，并有利于未来的可扩展性和可维护性。

**2.Spring中的bean的作用域有哪些？**

1.singleton：唯一bean实例，Spring中的bean默认都是单例的。

2.prototype：每次请求都会创建一个新的bean实例。

3.request：每一次HTTP请求都会产生一个新的bean，该bean仅在当前HTTP request内有效。

4.session：每一次HTTP请求都会产生一个新的bean，该bean仅在当前HTTP session内有效。

5.global-session：全局session作用域，仅仅在基于Portlet的Web应用中才有意义，Spring5中已经没有了。Portlet是能够生成语义代码（例如HTML）片段的小型Java Web插件。它们基于Portlet容器，可以像Servlet一样处理HTTP请求。但是与Servlet不同，每个Portlet都有不同的会话。

