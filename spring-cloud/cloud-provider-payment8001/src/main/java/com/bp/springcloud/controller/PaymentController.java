package com.bp.springcloud.controller;

import com.bp.springcloud.entities.CommonResult;
import com.bp.springcloud.entities.Payment;
import com.bp.springcloud.service.PaymentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Auther: daiyu
 * @Date: 15/4/20 11:18
 * @Description:
 */
@RestController
@Slf4j
public class PaymentController {

    @Autowired
    private PaymentService paymentService;


    @PostMapping(value = "/payment/create")
    public CommonResult create(@RequestBody Payment payment) {
        int result = paymentService.create(payment);
        log.info("payment create result ", result);

        if (result > 0) {
            return new CommonResult(200, "success", result);
        }

        return new CommonResult(444, "failure", null);
    }


    @GetMapping(value = "/payment/get/{id}")
    public CommonResult<Payment> getPaymentById(@PathVariable("id") Long id) {

        Payment result = paymentService.getPaymentById(id);
        log.info("payment query result is: ", result);

        if (result != null) {
            return new CommonResult(200, "success", result);
        }

        return new CommonResult(444, "failure", null);
    }
}
