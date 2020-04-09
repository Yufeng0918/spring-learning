package com.bp.spring.ioc.s04.lifecycle;

public class ConcreteTool implements Tool {

    public ConcreteTool() {
        System.out.println("ConcreteTool");
    }

    @Override
    public String realWork() {
        return "realWork() is invoked";
    }

}
