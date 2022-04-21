# JdbcTemplate

## 直接使用API

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
import com.stanlong.bean.Tb_User;
import org.apache.commons.dbcp.BasicDataSource;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.util.List;

public class TestJdbcApi {
    @Test
    public void demo01(){
        //创建连接池
        BasicDataSource basicDataSource = new BasicDataSource();

        //基本四项
        basicDataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        basicDataSource.setUrl("jdbc:mysql://localhost:3306/spring_jdbcTemplent");
        basicDataSource.setUsername("root");
        basicDataSource.setPassword("root");

        //创建模板
        JdbcTemplate jdbcTemplate = new JdbcTemplate();
        jdbcTemplate.setDataSource(basicDataSource);

        //通过API操作
        jdbcTemplate.update("insert into tb_user(username, password) values (?,?);", "wangwu", "789");

        String sql = "select * from tb_user";
        RowMapper<Tb_User> rowMapper = new BeanPropertyRowMapper<>(Tb_User.class);
        List<Tb_User> userList = jdbcTemplate.query(sql, rowMapper);

        for(Tb_User user : userList){
            System.out.println(user);
        }
    }
}
```

