package com.example.socketchatbackend.domain.chat.vo;

import com.example.socketchatbackend.exception.CustomException;
import com.example.socketchatbackend.exception.ErrorCode;

import static com.example.socketchatbackend.constraint.chat.room.RoomConstraints.*;

public record RoomCapacity(int value) {

    public RoomCapacity {
        validateRange(value);
    }

    private void validateRange(int value) {
        if (value < MIN_ALLOWED_CAPACITY || value > MAX_ALLOWED_CAPACITY) {
            throw new CustomException(ErrorCode.CAPACITY_EXCEEDED);
        }
    }

    public boolean canAccept(int currentUserCount) {
        return currentUserCount < value;
    }

    public boolean isFull(int currentUserCount) {
        return currentUserCount >= value;
    }
}
