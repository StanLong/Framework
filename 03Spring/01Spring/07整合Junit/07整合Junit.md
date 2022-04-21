# 整合Junit

1. 让Junit通知spring加载配置文件
2. 让spring容器自动进行注入

```java
import com.stanlong.service.AccountService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class TestApp {

	@Autowired // 与Junit整合，不需要在Spring的xml中配置扫描
	private AccountService accountService;

	@Test
	public void demo01(){
//		String xmlpath = "applicationContext.xml"; // 让Junit通知spring加载配置文件, 不需要再手动声明
//		ApplicationContext applicationContext = new ClassPathXmlApplicationContext(xmlpath);
//		AccountService accountService = applicationContext.getBean("accountServiceId", AccountService.class);
		accountService.transfer("lisi", "zhangsan", 500);
	}
}
```

