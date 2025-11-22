package com.example.socketchatbackend.exception.chat;

public enum ErrorMessages {

    TITLE_NULL("방 제목은 null일 수 없습니다."),
    TITLE_BLANK("방 제목은 비어있거나 공백만으로 구성될 수 없습니다."),
    TITLE_LENGTH_EXCEEDED("방 제목 길이가 허용 범위를 초과했습니다."),
    TITLE_DUPLICATION("중복된 방 제목으로 생성할 수 없습니다."),

    PASSWORD_LENGTH_EXCEEDED("비밀번호 길이가 허용 범위를 초과했습니다."),
    PASSWORD_INCORRECT("비밀번호가 일치하지 않습니다."),
    PASSWORD_NULL("비밀번호는 null일 수 없습니다."),

    CAPACITY_EXCEEDED("최대 인원 값이 허용 범위를 초과했습니다."),
    CAPACITY_NULL("허용 인원 수는 null일 수 없습니다."),
    ROOM_FULL("방의 현재 정원이 가득 찼습니다."),

    NICKNAME_BLANK("닉네임은 비어있을 수 없습니다."),
    NICKNAME_NULL("닉네임은 null일 수 없습니다."),
    NICKNAME_LENGTH_EXCEED("닉네임 길이는 1~16자 이내여야 합니다."),
    NICKNAME_DUPLICATION("같은 이름의 사용자가 존재합니다."),
    NICKNAME_NOT_FOUND("해당 닉네임은 방에 존재하지 않습니다."),

    ROOM_NOT_FOUND("요청한 ID의 채팅방을 찾을 수 없습니다."),

    MESSAGE_LENGTH_EXCEEDED("메시지의 길이는 200자 이하여야 합니다.");

    private final String message;

    ErrorMessages(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
