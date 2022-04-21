# JdbcTemplate

## 从Properties文件中读取配置信息

```sql
create database spring_jdbcTemplent default charset utf8 COLLATE utf8_general_ci;
use spring_jdbcTemplent;
create table tb_user(
    id int(11) primary key auto_increment,
    username varchar(255),
    password varchar(36)
);
```

```java
package com.stanlong.bean;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class Tb_User {

	private Integer id;
	private String username;
	private String password;
}
```

jdbcInfo.properties

```properties
jdbc.driverClass=com.mysql.jdbc.Driver
jdbc.jdbcUrl=jdbc:mysql://localhost:3306/spring_jdbcTemplent
jdbc.user=root
jdbc.password=root
```

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
	   xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	   xmlns:context="http://www.springframework.org/schema/context"
	   xmlns:aop="http://www.springframework.org/schema/aop"
	   xsi:schemaLocation="http://www.springframework.org/schema/beans
        				   http://www.springframework.org/schema/beans/spring-beans.xsd
        				   http://www.springframework.org/schema/context
        				   http://www.springframework.org/schema/context/spring-context.xsd
        				   http://www.springframework.org/schema/aop
        				   http://www.springframework.org/schema/aop/spring-aop.xsd">

	<!-- 加载配置文件
		classpath 表示src下
	 -->
	<context:property-placeholder location="classpath:jdbcInfo.properties"/>
	<!-- 配置c3p0数据源 -->
	<bean id="dataSourceId" class="com.mchange.v2.c3p0.ComboPooledDataSource">
		<property name="driverClass" value="${jdbc.driverClass}"></property>
		<property name="jdbcUrl" value="${jdbc.jdbcUrl}"></property>
		<property name="user" value="${jdbc.user}"></property>
		<property name="password" value="${jdbc.password}"></property>
	</bean>

	<!-- 配置dao
		dao继承 JdbaDaoSupport ,之后只需要注入数据源，底层将自动创建模板
	 -->
	<bean id="tbUserDaoId" class="com.stanlong.dao.TbUserDao">
		<property name="dataSource" ref="dataSourceId"></property>
	</bean>
</beans>
```

```java
package com.stanlong.dao;

import com.stanlong.bean.Tb_User;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import java.util.List;

public class TbUserDao extends JdbcDaoSupport{

	//更改用户
	public void updateUser(Tb_User tb_user){
		String sql = "update tb_user set username = ?, password = ? where id=?";
		Object args[] = {tb_user.getUsername(), tb_user.getPassword(), tb_user.getId()};
		this.getJdbcTemplate().update(sql, args);
	}

	//查询所有用户
	public List<Tb_User> findAll(){
		String sql = "select * from tb_user";
		RowMapper<Tb_User> rowMapper = new BeanPropertyRowMapper<Tb_User>(Tb_User.class);
		return (List<Tb_User>)this.getJdbcTemplate().query(sql, rowMapper);
	}

	//根据id查询
	public Tb_User findUserById(int i){
		String sql = "select * from tb_user where id = ?";
		//RowMapper<Tb_User> rowMapper = new BeanPropertyRowMapper<Tb_User>(Tb_User.class);
		RowMapper<Tb_User> rowMapper = new BeanPropertyRowMapper<Tb_User>(Tb_User.class);
		return this.getJdbcTemplate().queryForObject(sql, rowMapper, i);
	}
}
```

```java
import com.stanlong.bean.Tb_User;
import com.stanlong.dao.TbUserDao;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.List;

public class TestC3P0 {

	@Test
	public void demo01() {
		Tb_User tb_user = new Tb_User();
		tb_user.setUsername("zhangsansan");
		tb_user.setPassword("32221");
		tb_user.setId(3);

		String xmlPath = "applicationContext.xml";
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext(xmlPath);

		//获得目标类
		TbUserDao tbUserDao = applicationContext.getBean("tbUserDaoId", TbUserDao.class);
		//更改用户
		tbUserDao.updateUser(tb_user);
		//查询用户
		List<Tb_User> userList = tbUserDao.findAll();
		for(Tb_User t_user : userList){
			System.out.println(t_user);
		}
		//根据id查询用户
		Tb_User tb_user2 = tbUserDao.findUserById(3);
		System.out.println(tb_user2);

	}
}
```



