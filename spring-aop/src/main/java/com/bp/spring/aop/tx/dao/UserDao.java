package com.bp.spring.aop.tx.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

/**
 * @Auther: daiyu
 * @Date: 26/4/20 21:11
 * @Description:
 */
@Repository
public class UserDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void insert() {
        String sql = "INSERT INTO tbl_user (username, age) VALUES (?, ?);";
        jdbcTemplate.update(sql, UUID.randomUUID().toString().substring(0, 5), 20);
    }
}
