package com.bp.spring.ioc.dao;

import com.bp.spring.ioc.entities.Person;

/**
 * Created by day68c on 2/3/2017.
 */
public interface PersonDao {

    Person getChineseById(Long id);

}
