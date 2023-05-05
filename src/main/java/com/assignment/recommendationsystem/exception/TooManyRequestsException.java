package com.assignment.recommendationsystem.exception;

public class TooManyRequestsException extends RuntimeException {
    public TooManyRequestsException(String message) {
        super(message);
    }
    public TooManyRequestsException(String ipAddress, int requestLimit) {
        super(String.format("Too many requests from IP address %s. Request limit is %d", ipAddress, requestLimit));
    }
}


