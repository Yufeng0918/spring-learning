package com.bp.spring.aop.s02.aopxml;

/**
 * Created by Yufeng on 1/8/2017.
 */
public class TxtServiceImpl implements TxtService {

    @Override
    public void save(String trxId) {
        System.out.println("save txt " + trxId);
    }
}
