package com.bp.spring.ioc.s01.bean.service;

import com.bp.spring.ioc.s01.bean.entities.Person;

/**
 * Created by day68c on 2/3/2017.
 */
public interface PersonService {

    boolean registerPerson(Person person);

    Person getChineseById(Long id);
}
