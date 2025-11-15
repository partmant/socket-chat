package com.example.socketchatbackend.doamin.chat;

import static com.example.socketchatbackend.constraint.chat.ChatConstraints.*;
import static com.example.socketchatbackend.exception.chat.ChatErrorMessages.*;

public class ChatRoom {

    private final Long id;
    private final String title;
    private final String password;
    private final Integer maxUserCount;

    private ChatRoom(Long id, String title, String password, Integer maxUserCount) {
        validateTitle(title);
        validatePassword(password);
        validateMaxUserCount(maxUserCount);

        this.id = id;
        this.title = title;
        this.password = password;
        this.maxUserCount = maxUserCount;
    }

    public static ChatRoom of(Long id, String title, String password, Integer maxUserCount) {
        return new ChatRoom(id, title, password, maxUserCount);
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

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public Integer getMaxUserCount() {
        return maxUserCount;
    }

    public boolean hasPassword() {
        return password != null && !password.isBlank();
    }
}
