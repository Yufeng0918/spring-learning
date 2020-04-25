package com.bp.spring.ioc.collection;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

public class Chinese implements Person {

    private List list = new ArrayList();

    private Map map = new HashMap();

    private Set set = new HashSet();

    private Properties properties = new Properties();

    public void setList(List list) {
        this.list = list;
    }

    public void setMap(Map map) {
        this.map = map;
    }

    public void setSet(Set set) {
        this.set = set;
    }

    public void setProperties(Properties properties) {
        this.properties = properties;
    }

    @Override
    public void test() {
        System.out.println(this.list);
        System.out.println(this.map);
        System.out.println(this.properties);
        System.out.println(this.set);
    }

}
