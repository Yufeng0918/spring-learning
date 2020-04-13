package com.bp.springboot.exception;

/**
 * @Auther: daiyu
 * @Date: 13/4/20 22:39
 * @Description:
 */
public class UserNotExistsException extends RuntimeException {

    public UserNotExistsException() {
        super("User does not exists");
    }
}

