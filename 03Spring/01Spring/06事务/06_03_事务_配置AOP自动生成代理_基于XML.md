# 事务

在spring xml 配置aop 自动生成代理，进行事务的管理

1. 配置管理器
2. 配置事务详情
3. 配置aop

**转账案例**

```sql
-- 数据准备
create database spring_transaction default charset utf8 COLLATE utf8_general_ci;;
use spring_transaction;
create table account(
    id int(11) primary key auto_increment,
    username varchar(255),
    money int(11)
);

insert into account(username, money) values('zhangsan', 10000);
insert into account(username, money) values('lisi', 8000);
```

```java
package com.stanlong.dao;

public interface AccountDao {

	/**
	 * 汇款
	 * @param outer
	 * @param money
	 */
	public void out(String outer, Integer money);
	
	/**
	 * 收款
	 * @param inner
	 * @param money
	 */
	public void in(String inner, Integer money);
}
```

```java
package com.stanlong.dao.impl;

import com.stanlong.dao.AccountDao;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

public class AccountDaoImpl extends JdbcDaoSupport implements AccountDao {

	@Override
	public void out(String outer, Integer money) {
		this.getJdbcTemplate().update("update account set money=money-? where username=?", money ,outer);
	}

	@Override
	public void in(String inner, Integer money) {
		this.getJdbcTemplate().update("update account set money = money+? where username=?", money, inner);
	}
}
```

```java
package com.stanlong.service;

public interface AccountService {

	public void transfer(String outer, String inner, Integer money);
}
```

```java
package com.stanlong.service.impl;

import com.stanlong.dao.AccountDao;
import com.stanlong.service.AccountService;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

public class AccountServiceImpl implements AccountService {

	private AccountDao accountDao;

	// 注入DAO
	public void setAccountDao(AccountDao accountDao) {
		this.accountDao = accountDao;
	}
	
	//注入Spring事务模板
	private TransactionTemplate transactionTemplate;
	
	public void setTransactionTemplate(TransactionTemplate transactionTemplate) {
		this.transactionTemplate = transactionTemplate;
	}
	
	@Override
	public void transfer(String outer, String inner, Integer money) {
		transactionTemplate.execute(new TransactionCallbackWithoutResult() {

			protected void doInTransactionWithoutResult(TransactionStatus arg0) {
				accountDao.out(outer, money);
				//模拟断电
				int i = 1/0;
				accountDao.in(inner, money);
			}
		});
	}
}
```

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
	   xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	   xmlns:context="http://www.springframework.org/schema/context"
	   xmlns:aop="http://www.springframework.org/schema/aop"
	   xmlns:tx="http://www.springframework.org/schema/tx"
	   xsi:schemaLocation="http://www.springframework.org/schema/beans
        				   http://www.springframework.org/schema/beans/spring-beans.xsd
        				   http://www.springframework.org/schema/context
        				   http://www.springframework.org/schema/context/spring-context.xsd
        				   http://www.springframework.org/schema/aop
        				   http://www.springframework.org/schema/aop/spring-aop.xsd
        				   http://www.springframework.org/schema/tx
        				   http://www.springframework.org/schema/tx/spring-tx.xsd">
	<!-- 1、datesource -->
	<bean id="dataSourceId" class="com.mchange.v2.c3p0.ComboPooledDataSource">
		<property name="driverClass" value="com.mysql.jdbc.Driver"></property>
		<property name="jdbcUrl" value="jdbc:mysql://localhost:3306/spring_transaction"></property>
		<property name="user" value="root"></property>
		<property name="password" value="root"></property>
	</bean>

	<!-- 2、dao -->
	<bean id="accountDaoId" class="com.stanlong.dao.impl.AccountDaoImpl">
		<property name="dataSource" ref="dataSourceId"></property>
	</bean>

	<!-- 3、service -->
	<bean id="accountServiceId" class="com.stanlong.service.impl.AccountServiceImpl">
		<property name="accountDao" ref="accountDaoId"></property>
	</bean>

	<!-- 4、事务管理
		4.1  事务管理器
		4.2  事务详情：在aop筛选的基础上对方法确定使用什么样的事务，例如AC读写，B只读
			<tx:attributes> 用于配置事务详情
			<tx:method name=""/> 具体配置
				propagation 传播行为
				isolation 隔离级别
		4.3 AOP编程
	-->
	<!--  事务管理器 -->
	<bean id="txManagerId" class="org.springframework.jdbc.datasource.DataSourceTransactionManager">
		<property name="dataSource" ref="dataSourceId"></property>
	</bean>

	<!-- 事务详情：在aop筛选的基础上对方法确定使用什么样的事务，例如AC读写，B只读
			<tx:attributes> 用于配置事务详情
			<tx:method name=""/> 具体配置
				propagation 传播行为:  REQUIRED:必须的
				isolation 隔离级别 -->
	<tx:advice id="txAdviceId" transaction-manager="txManagerId">
		<tx:attributes>
			<tx:method name="transfer" propagation="REQUIRED" isolation="DEFAULT"/>
		</tx:attributes>
	</tx:advice>

	<!-- AOP编程 -->
	<aop:config>
		<aop:advisor advice-ref="txAdviceId" pointcut="execution(* com.stanlong.service.impl.AccountServiceImpl.*(..))"/>
	</aop:config>

</beans>
```

```java
package com.stanlong.z_factory_transaction;

import com.stanlong.service.AccountService;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TestApp {

	@Test
	public void demo01(){
		String xmlpath = "applicationContext.xml";
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext(xmlpath);
		AccountService accountService = applicationContext.getBean("accountServiceId", AccountService.class);
		accountService.transfer("lisi", "zhangsan", 1000);

	}
}

```

