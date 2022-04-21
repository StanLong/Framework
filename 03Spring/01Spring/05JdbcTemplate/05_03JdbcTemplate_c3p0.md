# JdbcTemplate

## C3P0

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
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.util.List;

public class TbUserDao {

	private JdbcTemplate jdbcTemplate;
	
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
	public void updateUser(Tb_User tb_user){
		String sql = "update tb_user set username = ?, password = ? where id=?";
		Object args[] = {tb_user.getUsername(), tb_user.getPassword(), tb_user.getId()};
		jdbcTemplate.update(sql, args);
	}

	public List<Tb_User> queryAll(){
		String sql = "select * from tb_user";
		RowMapper<Tb_User> rowMapper = new BeanPropertyRowMapper<>(Tb_User.class);
		List<Tb_User> userList = jdbcTemplate.query(sql, rowMapper);
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
		tb_user.setPassword("32221");
		tb_user.setId(3);
		
		String xmlPath = "applicationContext.xml";
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext(xmlPath);
		
		//获得目标类
		TbUserDao tbUserDao = applicationContext.getBean("tbUserDaoId", TbUserDao.class);
		tbUserDao.updateUser(tb_user);
		
		List<Tb_User> userList = tbUserDao.queryAll();
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
		<property name="driverClass" value="com.mysql.cj.jdbc.Driver"></property>
		<property name="jdbcUrl" value="jdbc:mysql://localhost:3306/spring_jdbcTemplent"></property>
		<property name="user" value="root"></property>
		<property name="password" value="root"></property>
	</bean>

	<!-- 创建模板，需要引入数据源	-->
	<bean id="jdbcTempletId" class="org.springframework.jdbc.core.JdbcTemplate">
		<property name="dataSource" ref="dataSourceId"></property>
	</bean>
	<!-- 配置dao	-->
	<bean id="tbUserDaoId" class="com.stanlong.dao.TbUserDao">
		<property name="jdbcTemplate" ref="jdbcTempletId"></property>
	</bean>

</beans>
```

