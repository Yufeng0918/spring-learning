package com.bp.spring.autoconfig.service;

import com.bp.spring.autoconfig.dao.BookDao;
import com.bp.spring.autoconfig.dao.SlotDao;
import com.bp.spring.autoconfig.dao.TagDao;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.inject.Inject;

/**
 * @Auther: daiyu
 * @Date: 24/4/20 14:27
 * @Description:
 */
@Service
@Getter
public class BookService {

    @Qualifier("bookDao1")
    @Autowired(required = false)
    private BookDao bookDao;

    @Resource
    private TagDao tagDao;

    @Inject
    private SlotDao slotDao;
}
