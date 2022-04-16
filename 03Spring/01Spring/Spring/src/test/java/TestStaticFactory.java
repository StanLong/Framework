import com.stanlong.factory.MyBeanFactory;
import com.stanlong.service.BookService;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Arrays;

public class TestStaticFactory {

	/**
	 * 自定义静态工厂
	 */
	@Test
	public void demo01(){
		 BookService bookService = MyBeanFactory.createBookService();
		 bookService.addBook();
	}
	
	/**
	 * Spring 工厂
	 */
	@Test
	public void demo02(){
		String xmlPath = "applicationContext.xml";
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext(xmlPath);
		BookService bookService = applicationContext.getBean("bookServiceId", BookService.class);
		bookService.addBook();
	}

	@Test
	public void demo03(){
		int[] nums = {13, 312, 343};
		int[] res = new int[nums.length];
		for(int i=0; i< nums.length; i++){
			for(int j=i+1; j< nums.length; j++){
				String data = nums[i]+ "" + nums[j];
				if(data.compareTo(nums[j]+"")<=0 && (res[i]+"").compareTo(nums[j]+"")<0){
					// data = "";
					res[i] =  nums[j];
				}
			}
		}
		System.out.println(Arrays.toString(res));
	}
	
	
}
