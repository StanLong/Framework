# AOP

## 什么是AOP

* 在软件业，AOP为Aspect Oriented Programming的缩写，意为：面向切面编程，通过预编译方式和运行期动态代理实现程序功能的统一维护的一种技术。AOP是OOP（面向对象编程）的延续，是软件开发中的一个热点，也是Spring框架中的一个重要内容，是函数式编程的一种衍生范型。利用AOP可以对业务逻辑的各个部分进行隔离，从而使得业务逻辑各部分之间的耦合度降低，提高程序的可重用性，同时提高了开发的效率。

- AOP采取横向抽取机制，取代了传统纵向继承体系重复性代码
- 经典应用：事务管理、性能监视、安全检查、缓存 、日志等
- Spring AOP使用纯Java实现，不需要专门的编译过程和类加载器，在运行期通过代理方式向目标类织入增强代码
- AspectJ是一个基于Java语言的AOP框架，Spring2.0开始，Spring AOP引入对Aspect的支持，AspectJ扩展了Java语言，提供了一个专门的编译器，在编译时提供横向代码的织入

## AOP实现原理

* aop底层将采用代理机制进行实现。
* 接口 + 实现类：spring采用 jdk 的动态代理Proxy。
* 实现类：spring 采用 cglib字节码增强。

## AOP术语

1. target目标类：需要被代理的类。例如：UserService

2. Joinpoint连接点：所谓连接点是指那些可能被拦截到的方法。例如：所有的方法

3.  PointCut切入点：已经被增强的连接点。例如：addUser()

4. advice通知/增强，增强代码。例如：after、before

5. Weaving织入：是指把增强advice应用到目标对象target来创建新的代理对象proxy的过程.

6. proxy代理类

7. Aspect切面：是切入点pointcut和通知advice的结合

   一个线是一个特殊的面。

   一个切入点和一个通知，组成成一个特殊的面。

![](https://cdn.jsdelivr.net/gh/StanLong/Framework/03Spring/doc/02.png)

