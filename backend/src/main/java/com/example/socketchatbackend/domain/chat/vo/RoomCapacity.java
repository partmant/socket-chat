package com.example.socketchatbackend.domain.chat.vo;

import static com.example.socketchatbackend.constraint.chat.RoomConstraints.*;
import static com.example.socketchatbackend.exception.chat.ErrorMessages.*;

public record RoomCapacity(int value) {

    public RoomCapacity {
        validateRange(value);
    }

    private void validateRange(int value) {
        if (value < MIN_ALLOWED_CAPACITY || value > MAX_ALLOWED_CAPACITY) {
            throw new IllegalArgumentException(CAPACITY_EXCEEDED.getMessage());
        }
    }

    public boolean canAccept(int currentUserCount) {
        return currentUserCount < value;
    }

    public boolean isFull(int currentUserCount) {
        return currentUserCount >= value;
    }
}
