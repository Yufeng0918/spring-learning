package com.bp.spring.ioc.s02.property;

public class ConcreteToolA implements Tool {

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public void realWork() {
        System.out.println(this.name);
        System.out.println("from ConcreteToolA");
    }

}
