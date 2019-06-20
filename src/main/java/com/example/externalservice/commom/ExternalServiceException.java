package com.example.externalservice.commom;

public class ExternalServiceException extends RuntimeException {
    public ExternalServiceException() {
        super();
    }

    public ExternalServiceException(String msg) {
        super(msg);
    }

}
