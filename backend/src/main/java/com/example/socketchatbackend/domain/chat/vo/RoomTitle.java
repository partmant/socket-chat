package com.example.socketchatbackend.domain.chat.vo;

import static com.example.socketchatbackend.constraint.chat.RoomConstraints.*;
import static com.example.socketchatbackend.exception.chat.ErrorMessages.*;

public record RoomTitle(String value) {

    public RoomTitle {
        validateNotNull(value);
        validateNotBlank(value);
        validateLength(value);
    }

    private void validateNotNull(String title) {
        if (title == null) {
            throw new IllegalArgumentException(TITLE_NULL.getMessage());
        }
    }

    private void validateNotBlank(String title) {
        if (title.isBlank()) {
            throw new IllegalArgumentException(TITLE_BLANK.getMessage());
        }
    }

    private void validateLength(String title) {
        if (title.length() > MAX_TITLE_LENGTH) {
            throw new IllegalArgumentException(TITLE_LENGTH_EXCEEDED.getMessage());
        }
    }
}
