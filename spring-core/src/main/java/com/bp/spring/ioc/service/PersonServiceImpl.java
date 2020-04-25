package com.bp.spring.ioc.service;

import com.bp.spring.ioc.dao.PersonDao;
import com.bp.spring.ioc.entities.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by day68c on 2/3/2017.
 */

@Component
public class PersonServiceImpl implements PersonService {

    @Autowired
    private PersonDao personDao;


    @Override
    public boolean registerPerson(Person person) {
        return true;
    }


    @Override
    public Person getChineseById(Long id) {
        return personDao.getChineseById(id);
    }
}
