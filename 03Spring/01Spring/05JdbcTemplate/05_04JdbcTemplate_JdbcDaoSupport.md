# JdbcTemplate

## JDBC模板由Spring注入

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

```java
package com.stanlong.dao;

import com.stanlong.bean.Tb_User;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import java.util.List;

public class TbUserDao extends JdbcDaoSupport{ // 继承 JdbcDaoSupport ， JDBC模板将由Spring注入。 父类方法由final修饰，子类不可覆写

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
		List<Tb_User> userList = (List<Tb_User>)this.getJdbcTemplate().query(sql, rowMapper);
		return userList;
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
		tb_user.setPassword("123");
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

	}
}

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
	<!-- 配置c3p0数据源 -->
	<bean id="dataSourceId" class="com.mchange.v2.c3p0.ComboPooledDataSource">
		<property name="driverClass" value="com.mysql.jdbc.Driver"></property>
		<property name="jdbcUrl" value="jdbc:mysql://localhost:3306/spring_jdbcTemplent"></property>
		<property name="user" value="root"></property>
		<property name="password" value="root"></property>
	</bean>

	<!-- 配置dao
		dao继承 JdbaDaoSupport ,之后只需要注入数据源，底层将自动创建模板
	 -->
	<bean id="tbUserDaoId" class="com.stanlong.dao.TbUserDao">
		<property name="dataSource" ref="dataSourceId"></property>
	</bean>

</beans>
```

