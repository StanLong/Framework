# JdbcTemplate

## 依赖DAO

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

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
	   xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	   xsi:schemaLocation="http://www.springframework.org/schema/beans
        				   http://www.springframework.org/schema/beans/spring-beans.xsd">
	<!-- 配置dbcp数据源 -->
	<bean id="dataSourceId" class="org.apache.commons.dbcp.BasicDataSource">
		<property name="driverClassName" value="com.mysql.jdbc.Driver"></property>
		<property name="url" value="jdbc:mysql://localhost:3306/spring_jdbcTemplent"></property>
		<property name="username" value="root"></property>
		<property name="password" value="root"></property>
	</bean>

	<!-- 配置目标类， 引入 jdbcTemplate	-->
	<bean id="tbUserDaoId" class="com.stanlong.dao.TbUserDao">
		<property name="jdbcTemplate" ref="jdbcTempletId"></property>
	</bean>

	<!-- jdbcTemplate	-->
	<bean id="jdbcTempletId" class="org.springframework.jdbc.core.JdbcTemplate">
		<property name="dataSource" ref="dataSourceId"></property>
	</bean>
</beans>
```

```java
import com.stanlong.bean.Tb_User;
import com.stanlong.dao.TbUserDao;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.List;

public class TestDBCP {

	@Test
	public void demo01() {
		Tb_User tb_user = new Tb_User();
		tb_user.setUsername("wangwuwu");
		tb_user.setPassword("123456");
		tb_user.setId(3);
		
		String xmlPath = "applicationContext.xml";
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext(xmlPath);
		
		//获得目标类
		TbUserDao tbUserDao = applicationContext.getBean("tbUserDaoId", TbUserDao.class);
		tbUserDao.updateUser(tb_user);

		List<Tb_User> userList = tbUserDao.queryAll();
		for(Tb_User user : userList){
			System.out.println(user);
		}		
	}
}
```

