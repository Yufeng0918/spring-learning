package com.bp.spring.ioc.dao;

import com.bp.spring.ioc.entities.Chinese;
import com.bp.spring.ioc.entities.Person;
import org.springframework.stereotype.Repository;

/**
 * Created by day68c on 2/3/2017.
 */

@Repository
public class PersonDaoImpl implements PersonDao {

    @Override
    public Person getChineseById(Long id) {
        return new Chinese();
    }

}
