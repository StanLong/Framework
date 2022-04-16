# 装配Bean基于XML

## 集合注入

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:p="http://www.springframework.org/schema/p"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
        				   http://www.springframework.org/schema/beans/spring-beans.xsd">
	        				   
	<!--
	集合的注入都是给 <property> 添加子标签
		数组: <array>
		List: <list>
		Set: <set>
		Map: <map>
		Properties: <props> 
	 -->
	<bean id="collDataId" class="com.stanlong.bean.CollData">
		<property name="arrayData">
			<array>
				<value>张三</value>
				<value>李四</value>
				<value>王五</value>
			</array>
		</property>
		
		<property name="listData">
			<list>
				<value>赵六</value>
				<value>陈七</value>
				<value>朱八</value>
			</list>
		</property>
		
		<property name="setData">
			<set>
				<value>曾小贤</value>
				<value>吕子乔</value>
				<value>张伟</value>
			</set>
		</property>
		<property name="mapData">
			<map>
				<entry key="战狼" value="吴京"></entry>
				<entry key="流浪地球" value="郭帆"></entry>
				<entry key="三体" value="刘慈欣"></entry>
			</map>
		</property>
		<property name="propsData">
			<props>
				<prop key="数据代表">肖刘林</prop>
				<prop key="华为接口人">范建斌</prop>
			</props>
		</property>
	</bean>
</beans>
```

```java
package com.stanlong.j_coll;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class CollData {

	private String[] arrayData;
	private List<String> listData;
	private Set<String> setData;
	private Map<String, String> mapData;
	private Properties propsData;
	
}
```

```java
package com.stanlong.j_coll;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TestColl {

	@Test
	public void demo01(){
		String xmlPath = "applicationContext.xml";
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext(xmlPath);
		CollData collData = applicationContext.getBean("collDataId", CollData.class);
		System.out.println(collData);	
	}	
}
```





