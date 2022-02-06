# Hibernate 面试题

**1. 简述hibernate运行原理或者工作原理**

hibernate里面提供了3个核心接口 
Configuration、SessionFactory、Session 
1、hibernate启动的时候利用Configuration读取xml配置文件 
2、通过配置文件创建SessionFactory对象，初始化hibernate基本信息 
3、获取session然后调用CRUD方法进行数据操作，hibernate会把我们的数据进行三种状态的划分，然后根据状态进行管理我们的数据，对应的发送SQL进行数据操作 
4、关闭session，如果有事务的情况下，需要手动获取事务并开启，然后事务结束后提交事务。 
5、在提交事务的时候，去验证我们的快照里面的数据和缓存数据是否一致，如果不一致，发送SQL进行修改

**2.hibernate的get方法和load方法的区别**

1、get和load都是利用主键策略查询数据， 
2、get默认不使用懒加载机制，load默认要使用懒加载机制，所谓的懒加载就是我们这个数据如果不使用，hibernate就不发送SQL到数据库查询数据。 
3、当查询数据库不存在的数据的时候，get方法返回null，load方法抛出空指针异常， 
原因是因为，load方法采用的动态代理的方式实现的，我们使用load方法的时候，hibernate会创建一个该实体的代理对象，该代理只保存了该对象的ID，当我们访问该实体对象其他属性，hibernate就发送SQL查询数据封装到代理对象，然后在利用代理对象返回给我们实际的数据

**3.hibernate的数据三种状态**

hibernate把他所管理的数据划分为三种状态 
瞬时的（刚new出来的数据–内存有，数据库没有） 
持久的 （从数据查询的，或者刚保存到数据库，session没关闭的， 数据库有，内存也有） 
游离的 、脱管的（数据库有，内存没有） 

**4. 简述hibernate的缓存机制**

hibernate分为2级缓存 
一级缓存又叫session缓存，又叫事务级缓存，生命周期从事务开始到事务结束，一级缓存是hibernate自带的，暴力使用，当我们一创建session就已有这个缓存了。数据库就会自动往缓存存放， 
二级缓存是hibernate提供的一组开放的接口方式实现的，都是通过整合第三方的缓存框架来实现的，二级缓存又叫sessionFactory的缓存，可以跨session访问。常用的EHcache、OScache，这个需要一些配置。

当我们每次 查询数据的时候，首先是到一级缓存查看是否存在该对象，如果有直接返回，如果没有就去二级缓存进行查看，如果有直接返回，如果没有在发送SQL到数据库查询数据， 
当SQL发送查询回该数据的时候，hibernate会把该对象以主键为标记的形式存储到二级缓存和一级缓存，如果返回的是集合，会把集合打散然后以主键的形式存储到缓存。一级缓存和二级缓存只针对以ID查询的方式生效，get、load方法

**5. 简述hibernate中getCurrentSession和openSession区别**

getCurrentSession和openSession都是通过H的工厂去获取数据库的会话对象， 
1、getCurrentSession会绑定当前线程，而openSession不会，因为我们把hibernate交给我们的spring来管理之后，我们是有事务配置，这个有事务的线程就会绑定当前的工厂里面的每一个session，而openSession是创建一个新session。 
2、getCurrentSession事务是有spring来控制的，而openSession需要我们手动开启和手动提交事务， 
3、getCurrentSession是不需要我们手动关闭的，因为工厂会自己管理，而openSession需要我们手动关闭。 
4、而getCurrentSession需要我们手动设置 绑定事务的机制，有三种设置方式，jdbc本地的Thread、JTA、第三种是spring提供的事务管理机制org.springframework.orm.hibernate4.SpringSessionContext，而且srping默认使用该种事务管理机制

**6.hibernate的三种检索策略优缺点**

- 立即检索：

  - 优点： 对应用程序完全透明，不管对象处于持久化状态，还是游离状态，应用程序都可以方便的从一个对象导航到与它关联的对象；
  - 缺点： 1.select语句太多；2.可能会加载应用程序不需要访问的对象白白浪费许多内存空间；
  
  - 立即检索:lazy=false；


- 延迟检索：
  - 优点： 由应用程序决定需要加载哪些对象，可以避免可执行多余的select语句，以及避免加载应用程序不需要访问的对象。因此能提高检索性能，并且能节省内存空间；
  - 缺点： 应用程序如果希望访问游离状态代理类实例，必须保证他在持久化状态时已经被初始化；
  - 延迟加载：lazy=true；

- 迫切左外连接检索：
  - 优点： 1对应用程序完全透明，不管对象处于持久化状态，还是游离状态，应用程序都可以方便地冲一个对象导航到与它关联的对象。2使用了外连接，select语句数目少；
  - 缺点： 1 可能会加载应用程序不需要访问的对象，白白浪费许多内存空间；2复杂的数据库表连接也会影响检索性能；
  - 预先抓取： fetch=“join”；

**7.如何优化Hibernate**

- 数据库设计调整
- HQL优化
- API的正确使用(如根据不同的业务类型选用不同的集合及查询API)
- 主配置参数(日志，查询缓存，fetch_size, batch_size等)
- 映射文件优化(ID生成策略，二级缓存，延迟加载，关联优化)
- 一级缓存的管理

**8.Hibernate中inverse的作用**

inverse属性默认是false,就是说关系的两端都来维护关系。

**9.主键生成策略有哪些**

- 主键的自动生成策略
  - identity 自增长(mysql,db2)
  - sequence 自增长(序列)， oracle中自增长是以序列方法实现
  - native 自增长
  - increment 自增长
- 指定主键生成策略为 （  手动指定主键的值  ）
  - assigned
- 指定主键生成策略为UUID生成的值
  - uuid







