package com.example.socketchatbackend.exception.chat;

public enum ErrorMessages {

    TITLE_NULL("방 제목은 null일 수 없습니다."),
    TITLE_BLANK("방 제목은 비어있거나 공백만으로 구성될 수 없습니다."),
    TITLE_LENGTH_EXCEEDED("방 제목 길이가 허용 범위를 초과했습니다."),
    TITLE_DUPLICATION("중복된 방 제목으로 생성할 수 없습니다."),

    PASSWORD_LENGTH_EXCEEDED("비밀번호 길이가 허용 범위를 초과했습니다."),
    INVALID_PASSWORD("비밀번호가 일치하지 않습니다."),

    MAX_USER_COUNT_EXCEEDED("최대 인원 값이 허용 범위를 초과했습니다."),

    ROOM_NOT_FOUND("요청한 ID의 채팅방을 찾을 수 없습니다.");

    private final String message;

    ErrorMessages(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
