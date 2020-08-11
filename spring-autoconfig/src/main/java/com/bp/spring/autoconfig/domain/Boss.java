package com.bp.spring.autoconfig.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Boss {


    private Car car;

    public Boss(Car car){
        this.car = car;
        System.out.println("Boss...有参构造器");
    }



    public Car getCar() {
        return car;
    }

    @Autowired
    public void setCar(Car car) {
        this.car = car;
    }

    @Override
    public String toString() {
        return "Boss [car=" + car + "]";
    }
}

