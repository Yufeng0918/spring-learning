package com.bp.spring.aop.jdk;

import org.springframework.stereotype.Component;

/**
 * Created by Yufeng on 1/8/2017.
 */
@Component("txtService")
public class TxtServiceImpl implements TxtService {

    @Override
    public boolean save(String trxId) {
        System.out.println("save txt " + trxId);
        return true;
    }
}
