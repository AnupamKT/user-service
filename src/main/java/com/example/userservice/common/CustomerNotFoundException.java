package com.example.userservice.common;

public class CustomerNotFoundException extends Exception {
    public CustomerNotFoundException(String msg) {
        super(msg);
    }
}
