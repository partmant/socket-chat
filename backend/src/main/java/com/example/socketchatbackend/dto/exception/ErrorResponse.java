package com.example.socketchatbackend.dto.exception;

public record ErrorResponse(
        String errorCode,
        String message,
        int status
) {}
