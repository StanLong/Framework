package com.stanlong.factory;

import com.stanlong.service.BookService;
import com.stanlong.service.impl.BookServiceImpl;

public class MyBeanFactory {

	/*
	 * 创建静态工厂
	 */
	public static BookService createBookService(){
		return new BookServiceImpl();
	}
}
