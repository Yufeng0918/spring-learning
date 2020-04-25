package com.bp.spring.aop.proxyfactorybean;

import java.sql.SQLException;

public class Dog {

    public void remove() {
        throw new RuntimeException("Unable to remove");
    }

    public void save() throws SQLException {
        throw new SQLException("Unable to save");
    }
}
