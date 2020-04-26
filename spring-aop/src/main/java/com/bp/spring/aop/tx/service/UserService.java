package com.bp.spring.aop.tx.service;

import com.bp.spring.aop.tx.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Auther: daiyu
 * @Date: 26/4/20 21:11
 * @Description:
 */

@Service
public class UserService {

    @Autowired
    private UserDao userDao;

    @Transactional
    public void insertUser(){
        userDao.insert();
    }
}
