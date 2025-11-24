package com.example.socketchatbackend.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.example.socketchatbackend.dto.exception.ErrorResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ErrorResponse> handleCustomException(CustomException e) {
        ErrorCode code = e.getErrorCode();
        ErrorResponse body = new ErrorResponse(
                code.code(),
                code.message(),
                code.status().value()
        );
        return ResponseEntity.status(code.status()).body(body);
    }

    // 예기치 못한 오류
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception e) {
        e.printStackTrace();
        return ResponseEntity.internalServerError().body(
                new ErrorResponse("INTERNAL_SERVER_ERROR", e.getMessage(), 500)
        );
    }
}
