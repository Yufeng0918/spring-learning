package com.bp.spring.registry.controller;

import com.bp.spring.registry.service.BookService;
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
    private BookService bookService;
}
