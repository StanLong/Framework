import com.stanlong.bean.CollData;
import org.junit.jupiter.api.Test;
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
