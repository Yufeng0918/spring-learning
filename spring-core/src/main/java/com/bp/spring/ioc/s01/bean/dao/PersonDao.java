package com.bp.spring.ioc.s01.bean.dao;

import com.bp.spring.ioc.s01.bean.entities.Person;

/**
 * Created by day68c on 2/3/2017.
 */
public interface PersonDao {

    Person getChineseById(Long id);

}
