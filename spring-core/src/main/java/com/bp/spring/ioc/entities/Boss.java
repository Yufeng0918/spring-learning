package com.bp.spring.ioc.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @Auther: daiyu
 * @Date: 25/4/20 21:46
 * @Description:
 */

@Getter
@ToString
@Component
@NoArgsConstructor
public class Boss {

    private Car car;

    @Autowired
    public Boss(Car car) {
        this.car = car;
    }

//    @Autowired
    public void setCar(Car car) {
        this.car = car;
    }
}
