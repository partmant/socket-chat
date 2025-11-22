package com.example.socketchatbackend.domain.chat.vo;

import static com.example.socketchatbackend.constraint.chat.room.RoomConstraints.*;
import static com.example.socketchatbackend.exception.chat.ErrorMessages.*;

public record RoomPassword(String password) {

    public RoomPassword {
        validatePassword(password);
    }

    private void validatePassword(String password) {
        if (password == null) {
            return;
        }

        validateLength(password);
    }

    private void validateLength(String password) {
        if (password.length() < MIN_PASSWORD_LENGTH || password.length() > MAX_PASSWORD_LENGTH) {
            throw new IllegalArgumentException(PASSWORD_LENGTH_EXCEEDED.getMessage());
        }
    }

    public boolean matches(String input) {
        if (!exists()) {
            return false;
        }
        if (input == null) {
            return false;
        }
        return password.equals(input);
    }

    public boolean exists() {
        return password != null && !password.isBlank();
    }
}
