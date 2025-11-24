package com.example.socketchatbackend.domain.chat.vo;

import com.example.socketchatbackend.exception.CustomException;
import com.example.socketchatbackend.exception.ErrorCode;

import static com.example.socketchatbackend.constraint.chat.room.RoomConstraints.*;

public record RoomTitle(String value) {

    public RoomTitle {
        validateNotNull(value);
        validateNotBlank(value);
        validateLength(value);
    }

    private void validateNotNull(String title) {
        if (title == null) {
            throw new CustomException(ErrorCode.TITLE_NULL);
        }
    }

    private void validateNotBlank(String title) {
        if (title.isBlank()) {
            throw new CustomException(ErrorCode.TITLE_BLANK);
        }
    }

    private void validateLength(String title) {
        if (title.length() > MAX_TITLE_LENGTH) {
            throw new CustomException(ErrorCode.TITLE_LENGTH_EXCEEDED);
        }
    }
}
