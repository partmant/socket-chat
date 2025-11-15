package com.example.socketchatbackend.domain.chat;

import static com.example.socketchatbackend.constraint.chat.RoomConstraints.*;
import static com.example.socketchatbackend.exception.chat.ErrorMessages.*;

public record Room(Long id, String title, String password, Integer maxUserCount) {

    public Room {
        validateTitle(title);
        validatePassword(password);
        validateMaxUserCount(maxUserCount);
    }

    public static Room of(String title, String password, Integer maxUserCount) {
        return new Room(null, title, password, maxUserCount);
    }

    public Room withId(Long id) {
        return new Room(id, this.title, this.password, this.maxUserCount);
    }

    private void validateTitle(String title) {
        validateTitleNotNull(title);
        validateTitleNotBlank(title);
        validateTitleLength(title);
    }

    private void validateTitleNotNull(String title) {
        if (title == null) {
            throw new IllegalArgumentException(TITLE_NULL.getMessage());
        }
    }

    private void validateTitleNotBlank(String title) {
        if (title.isBlank()) {
            throw new IllegalArgumentException(TITLE_BLANK.getMessage());
        }
    }

    private void validateTitleLength(String title) {
        if (title.length() > MAX_TITLE_LENGTH) {
            throw new IllegalArgumentException(TITLE_LENGTH_EXCEEDED.getMessage());
        }
    }

    private void validatePassword(String password) {
        if (password == null) {
            return;
        }
        if (password.length() < MIN_PASSWORD_LENGTH || password.length() > MAX_PASSWORD_LENGTH) {
            throw new IllegalArgumentException(PASSWORD_LENGTH_EXCEEDED.getMessage());
        }
    }

    private void validateMaxUserCount(Integer maxUserCount) {
        if (maxUserCount == null ||
                maxUserCount < MIN_ALLOWED_USER_COUNT ||
                maxUserCount > MAX_ALLOWED_USER_COUNT) {
            throw new IllegalArgumentException(MAX_USER_COUNT_EXCEEDED.getMessage());
        }
    }

    public boolean hasPassword() {
        return password != null && !password.isBlank();
    }
}
