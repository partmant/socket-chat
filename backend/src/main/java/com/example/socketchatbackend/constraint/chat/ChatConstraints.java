package com.example.socketchatbackend.constraint.chat;

public final class ChatConstraints {
    public static final int MAX_TITLE_LENGTH = 50;
    public static final int MIN_PASSWORD_LENGTH = 4;
    public static final int MAX_PASSWORD_LENGTH = 10;
    public static final int MIN_ALLOWED_USER_COUNT = 1;
    public static final int MAX_ALLOWED_USER_COUNT = 10;

    private ChatConstraints() {
    }
}
