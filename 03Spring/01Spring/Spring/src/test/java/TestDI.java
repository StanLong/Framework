import com.stanlong.service.BookService;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TestDI {

    //方式一：已注释
//	@Test
//	public void demo01(){
//		BookService bookService = new BookServiceImpl();
//		System.out.println(bookService);
//		bookService.addBook();
//	}

    //方式二：
    @Test
    public void demo02(){
        //从Spring容器中获得
        String xmlPath = "applicationContext.xml";
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext(xmlPath);
        BookService bookService = (BookService)applicationContext.getBean("bookServiceId");
        bookService.addBook();
    }


}
