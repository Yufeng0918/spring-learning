package com.bp.spring.autoconfig.controller;

import com.bp.spring.autoconfig.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

/**
 * @Auther: daiyu
 * @Date: 24/4/20 14:16
 * @Description:
 */
@Controller
public class BookController {

    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    private BookService bookService;

    public BookService getBookService() {
        return bookService;
    }
}
