import com.stanlong.service.UserService;
import com.stanlong.service.impl.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TestIoC {
    @Test
    public void demo01() {
        // 以前的写法
        System.out.println("new 创建对象");
        UserService userService = new UserServiceImpl();
        userService.addUser();
    }

    @Test
    public void demo02() {
        // 新的写法
        // 1、获得Spring容器
        System.out.println("Spring IOC 创建对象");
        String xmlPath = "applicationContext.xml";
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext(xmlPath);
        // 2、由Spring 容器创建对象
        UserService userService = (UserService) applicationContext.getBean("userServiceId");
        userService.addUser();
    }
}
