package com.bp.spring.ioc.property;

public class Chinese implements Person {

    private Tool tool;

    public Tool getTool() {
        return tool;
    }

    public void setTool(Tool tool) {
        this.tool = tool;
    }

    @Override
    public void work() {
        tool.realWork();
    }

}