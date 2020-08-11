package com.bp.spring.autoconfig.dao;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

/**
 * @Auther: daiyu
 * @Date: 24/4/20 14:16
 * @Description:
 */

@Repository("bookDao1")
public class BookDao {

    public int label = 1;
}
