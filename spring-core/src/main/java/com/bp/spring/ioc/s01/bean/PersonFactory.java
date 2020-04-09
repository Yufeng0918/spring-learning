package com.bp.spring.ioc.s01.bean;

public class PersonFactory {

    public static Person getPerson(String str) {
        if ("Chinese".equalsIgnoreCase(str)) {
            return new Chinese();
        }
        return new American();
    }
}
