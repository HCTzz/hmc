package com.ctt.exception;

/**
 * @Description
 * @auther HHF
 * @create 2020-05-24 下午 3:19
 */
public class AuthException extends RuntimeException {

    private String message;

    public AuthException(String message){
        super(message);
        this.message = message;
    }

}
