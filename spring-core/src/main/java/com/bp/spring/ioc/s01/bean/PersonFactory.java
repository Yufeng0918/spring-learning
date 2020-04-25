package com.bp.spring.ioc.s01.bean;

import com.bp.spring.ioc.s01.bean.entities.American;
import com.bp.spring.ioc.s01.bean.entities.Chinese;
import com.bp.spring.ioc.s01.bean.entities.Person;

public class PersonFactory {

    public static Person getPerson(String str) {
        if ("Chinese".equalsIgnoreCase(str)) {
            return new Chinese();
        }
        return new American();
    }
}
