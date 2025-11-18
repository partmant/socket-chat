package com.example.socketchatbackend.constraint.chat;

public final class RoomConstraints {
    public static final int MAX_TITLE_LENGTH = 50;

    public static final int MIN_PASSWORD_LENGTH = 4;
    public static final int MAX_PASSWORD_LENGTH = 10;

    public static final int MIN_ALLOWED_CAPACITY = 1;
    public static final int MAX_ALLOWED_CAPACITY = 10;

    public static final int MIN_NICKNAME_LENGTH = 1;
    public static final int MAX_NICKNAME_LENGTH = 16;

    private RoomConstraints() {
    }
}
