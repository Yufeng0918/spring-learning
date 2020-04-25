package com.bp.spring.ioc.lifecycle;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

public class Chinese implements Person, InitializingBean, DisposableBean {

    private Tool tool;

    public Chinese() {
        System.out.println("Chinese Constructor");
    }

    public void setTool(Tool tool) {
        System.out.println("setTool");

        this.tool = tool;
    }

    @Override
    public void work() {
        System.out.println(this.tool.realWork());
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("afterPropertiesSet");
    }

    @Override
    public void destroy() throws Exception {
        System.out.println("destroy");
    }

    public void init() {
        System.out.println("init");
    }

    public void close() {
        System.out.println("close");
    }

}
