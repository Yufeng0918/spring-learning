package com.bp.spring.ioc.s01.bean.dao;

import com.bp.spring.ioc.s01.bean.dao.PersonDao;
import com.bp.spring.ioc.s01.bean.entities.Chinese;
import com.bp.spring.ioc.s01.bean.entities.Person;
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
