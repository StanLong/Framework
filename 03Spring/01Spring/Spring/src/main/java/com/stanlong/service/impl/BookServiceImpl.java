package com.stanlong.service.impl;

import com.stanlong.dao.BookDao;
import com.stanlong.service.BookService;

public class BookServiceImpl implements BookService {

    //方式一：接口=实现类
    //private BookDao bookDao = new BookDaoImpl();

    //方式二：接口+setter
    private BookDao bookDao;

    public void setBookDao(BookDao bookDao) {
        this.bookDao = bookDao;
    }

    @Override
    public void addBook() {
        this.bookDao.addBook();
    }
}
