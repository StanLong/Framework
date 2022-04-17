import com.stanlong.action.StudentAction;
import org.junit.jupiter.api.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TestWebAnnotation {

	@Test
	public void demo01(){
		String xmlPath = "applicationContext.xml";
		ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext(xmlPath);
		StudentAction studentAction = applicationContext.getBean("studentActionId", StudentAction.class);

		studentAction.execute();
		/* 验证 @Scope("prototype")
		StudentAction studentAction2 = applicationContext.getBean("studentActionId", StudentAction.class);
		System.out.println(studentAction1);
		System.out.println(studentAction2);*/
		applicationContext.close();

	}
}
