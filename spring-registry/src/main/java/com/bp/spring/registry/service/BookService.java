package com.bp.spring.registry.service;

import com.bp.spring.registry.dao.BookDao;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Auther: daiyu
 * @Date: 24/4/20 14:27
 * @Description:
 */
@Service
@Getter
public class BookService {

    @Autowired
    private BookDao bookDao;
}
