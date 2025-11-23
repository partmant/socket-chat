package com.example.socketchatbackend.domain.chat.vo;

import static com.example.socketchatbackend.constraint.chat.room.RoomConstraints.*;
import static com.example.socketchatbackend.exception.chat.ErrorMessages.*;

public final class RoomPassword {

    // 비밀번호 없음 상태
    private static final RoomPassword NONE = new RoomPassword("");

    private final String value;

    private RoomPassword(String value) {
        this.value = value;
    }

    public static RoomPassword of(String raw) {
        if (raw == null || raw.isBlank()) {
            throw new IllegalArgumentException(PASSWORD_BLANK.getMessage());
        }

        // 길이 검증
        if (raw.length() < MIN_PASSWORD_LENGTH || raw.length() > MAX_PASSWORD_LENGTH) {
            throw new IllegalArgumentException(PASSWORD_LENGTH_EXCEEDED.getMessage());
        }

        return new RoomPassword(raw);
    }

    public static RoomPassword none() {
        return NONE;
    }

    public boolean matches(String input) {
        if (!exists()) {
            return false;
        }
        if (input == null || input.isBlank()) {
            return false;
        }

        return value.equals(input);
    }

    // 비밀번호 설정 확인 및 유효한 비밀번호 확인
    public boolean exists() {
        return !value.isBlank();
    }

    public String value() {
        return value;
    }
}
