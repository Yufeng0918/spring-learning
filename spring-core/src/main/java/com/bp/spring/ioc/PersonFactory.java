package com.bp.spring.ioc;

import com.bp.spring.ioc.entities.American;
import com.bp.spring.ioc.entities.Chinese;
import com.bp.spring.ioc.entities.Person;

public class PersonFactory {

    public static Person getPerson(String str) {
        if ("Chinese".equalsIgnoreCase(str)) {
            return new Chinese();
        }
        return new American();
    }
}
