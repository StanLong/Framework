# 事务

1. 配置事务管理器，将并事务管理器交予spring
2. 在目标类或目标方法添加注解即可 @Transactional

**转账案例**

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

	
	<!-- 1 配置事务管理器，将并事务管理器交予spring -->
	<!-- 4.1 事务管理器 -->
	<bean id="txManagerId" class="org.springframework.jdbc.datasource.DataSourceTransactionManager">
		<property name="dataSource" ref="dataSourceId"></property>
	</bean>
	<!-- 4.2 将配置管理器交于Spring
		* tansaction-manager 配置事务管理器
		* proxy-target-class
			true: 底层强制使用cglib代理
	 -->
	<tx:annotation-driven transaction-manager="txManagerId" proxy-target-class="true"/>
</beans>
```



```java
package com.stanlong.service.impl;

import com.stanlong.dao.AccountDao;
import com.stanlong.service.AccountService;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

// 2 在目标类或目标方法添加注解即可 @Transactional
@Transactional(propagation=Propagation.REQUIRED, isolation=Isolation.DEFAULT)
public class AccountServiceImpl implements AccountService {

	private AccountDao accountDao;

	public void setAccountDao(AccountDao accountDao) {
		this.accountDao = accountDao;
	}

	@Override
	public void transfer(String outer, String inner, Integer money) {
		accountDao.out(outer, money);
		//模拟断电
		int i = 1/0;
		accountDao.in(inner, money);
	}
}
```





