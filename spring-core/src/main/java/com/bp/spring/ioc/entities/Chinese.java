package com.bp.spring.ioc.entities;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;

@Data
public class Chinese implements Person {

    @Value("SG")
    private String name;

    @Value("#{20 + 5}")
    private int age;

    @Value("${person.nickName}")
    private String nickName;

    public Chinese() {
        System.out.println("chinese constructor");
    }

    public Chinese(String name, int age, String nickName) {
        this.name = name;
        this.age = age;
        this.nickName = nickName;
    }


    @Override
    public String sayGoodbye(String name) {
        return name + " Goodbye";
    }

    @Override
    public String sayHello(String name) {
        return name + " Hello";
    }

}
