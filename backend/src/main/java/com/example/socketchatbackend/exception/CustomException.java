package com.example.socketchatbackend.exception;

public class CustomException extends RuntimeException {

    private final ErrorCode errorCode;

    public CustomException(ErrorCode code) {
        super(code.message());
        this.errorCode = code;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }
}
