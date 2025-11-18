package com.example.socketchatbackend.domain.chat.vo;

import static com.example.socketchatbackend.exception.chat.ErrorMessages.*;

public record RoomNickname(String value) {

    public RoomNickname {
        validateNotNull(value);
        validateNotBlank(value);
        validateLength(value);
    }

    private void validateNotNull(String value) {
        if (value == null) {
            throw new IllegalArgumentException(NICKNAME_NULL.getMessage());
        }
    }

    private void validateNotBlank(String value) {
        if (value.isBlank()) {
            throw new IllegalArgumentException(NICKNAME_BLANK.getMessage());
        }
    }

    private void validateLength(String value) {
        if (value.length() < 1 || value.length() > 16) {
            throw new IllegalArgumentException(NICKNAME_LENGTH_EXCEED.getMessage());
        }
    }
}
