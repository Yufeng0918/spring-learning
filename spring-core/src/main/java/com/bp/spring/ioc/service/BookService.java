package com.bp.spring.ioc.service;

import com.bp.spring.ioc.dao.BookDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Auther: daiyu
 * @Date: 24/4/20 14:27
 * @Description:
 */
@Service
public class BookService {

    @Autowired
    private BookDao bookDao;
}