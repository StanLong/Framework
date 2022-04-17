import com.stanlong.aspect.MyBeanFactory;
import com.stanlong.service.impl.UserServiceImpl;
import org.junit.Test;

public class TestCglib {
	/**
	 * CGLIB 字节码增强
	 * 	采用字节码增强框架 cglib,在运行时，创建目标类的子类，从而对目标类进行增强
	 * 	导入jar包
	 * 		核心包： cglib-3.2.4.jar
	 * 		依赖包：asm-3.3.1.jar
	 * 		Spring-core 已经整合以上两个包
	 */
	@Test
	public void demo01(){
		UserServiceImpl userServiceImpl = MyBeanFactory.createUserService();
		userServiceImpl.addUser();
		userServiceImpl.updateUser();
		userServiceImpl.deleteUser();
	}

}
