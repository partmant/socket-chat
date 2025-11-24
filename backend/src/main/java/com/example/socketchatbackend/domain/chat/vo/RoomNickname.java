package com.example.socketchatbackend.domain.chat.vo;

import com.example.socketchatbackend.exception.CustomException;
import com.example.socketchatbackend.exception.ErrorCode;

public record RoomNickname(String value) {

    public RoomNickname {
        validateNotNull(value);
        validateNotBlank(value);
        validateLength(value);
    }

    private void validateNotNull(String value) {
        if (value == null) {
            throw new CustomException(ErrorCode.NICKNAME_NULL);
        }
    }

    private void validateNotBlank(String value) {
        if (value.isBlank()) {
            throw new CustomException(ErrorCode.NICKNAME_BLANK);
        }
    }

    private void validateLength(String value) {
        if (value.length() < 1 || value.length() > 16) {
            throw new CustomException(ErrorCode.NICKNAME_LENGTH_EXCEED);
        }
    }
}
