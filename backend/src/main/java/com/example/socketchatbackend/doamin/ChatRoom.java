package com.example.socketchatbackend.doamin;

public class ChatRoom {

    private static final int MAX_TITLE_LENGTH = 50;
    private static final int MIN_PASSWORD_LENGTH = 4;
    private static final int MAX_PASSWORD_LENGTH = 10;
    private static final int MIN_ALLOWED_USER_COUNT = 1;
    private static final int MAX_ALLOWED_USER_COUNT = 10;

    private final Long id;
    private final String title;
    private final String password;
    private final Integer maxUserCount;

    public ChatRoom(Long id, String title, String password, Integer maxUserCount) {
        validateTitle(title);
        validatePassword(password);
        validateMaxUserCount(maxUserCount);

        this.id = id;
        this.title = title;
        this.password = password;
        this.maxUserCount = maxUserCount;
    }

    private void validateTitle(String title) {
        if (title == null || title.isBlank()) {
            throw new IllegalArgumentException("제목 값이 유효하지 않습니다.");
        }
        if (title.length() > MAX_TITLE_LENGTH) {
            throw new IllegalArgumentException("제목 길이가 유효하지 않습니다.");
        }
    }

    private void validatePassword(String password) {
        if (password == null) {
            return;
        }
        if (password.length() < MIN_PASSWORD_LENGTH || password.length() > MAX_PASSWORD_LENGTH) {
            throw new IllegalArgumentException("비밀번호 길이가 유효하지 않습니다.");
        }
    }

    private void validateMaxUserCount(Integer maxUserCount) {
        if (maxUserCount == null ||
                maxUserCount < MIN_ALLOWED_USER_COUNT ||
                maxUserCount > MAX_ALLOWED_USER_COUNT) {
            throw new IllegalArgumentException("최대 인원 값이 유효하지 않습니다.");
        }
    }

    public Long getId() {
        return id;
    }
}
