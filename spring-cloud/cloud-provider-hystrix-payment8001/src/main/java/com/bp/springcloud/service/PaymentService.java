package com.bp.springcloud.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * @Auther: daiyu
 * @Date: 16/4/20 14:33
 * @Description:
 */
@Service
public class PaymentService {

    public String paymentInfoOk(Integer id) {
        return "Thread name: " + Thread.currentThread().getName() + " paymentOk, id: "  + id;
    }


    @HystrixCommand(fallbackMethod = "paymentInfoTimeoutHandler", commandProperties = {
        @HystrixProperty(name="execution.isolation.thread.timeoutInMilliseconds",value="5000")
    })
    public String paymentInfoTimeout(Integer id) {

        try {
            int waitTime = 3;
            TimeUnit.SECONDS.sleep(waitTime);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return "Thread name: " + Thread.currentThread().getName() + " paymentTimeout, id: "  + id;
    }


    public String paymentInfoTimeoutHandler(Integer id) {
        return "Thread name: " + Thread.currentThread().getName() + " paymentInfoTimeoutHandler, id: "  + id;
    }

}
