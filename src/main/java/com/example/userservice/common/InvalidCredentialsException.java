package com.example.userservice.common;

public class InvalidCredentialsException extends Exception {

    public InvalidCredentialsException(String msg) {
        super(msg);
    }
}
