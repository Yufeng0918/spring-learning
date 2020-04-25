package com.bp.spring.ioc.service;

import com.bp.spring.ioc.entities.Person;

/**
 * Created by day68c on 2/3/2017.
 */
public interface PersonService {

    boolean registerPerson(Person person);

    Person getChineseById(Long id);
}
