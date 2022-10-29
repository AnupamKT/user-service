package com.example.userservice.common;

public class InvalidCustomerRequestException extends Exception {
    public InvalidCustomerRequestException(String msg) {
        super(msg);
    }
}
