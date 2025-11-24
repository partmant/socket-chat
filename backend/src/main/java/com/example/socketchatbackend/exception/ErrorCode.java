package com.example.socketchatbackend.exception;

import org.springframework.http.HttpStatus;

public enum ErrorCode {

    // 방 제목
    TITLE_NULL("TITLE_NULL", "방 제목은 null일 수 없습니다.", HttpStatus.BAD_REQUEST),
    TITLE_BLANK("TITLE_BLANK", "방 제목은 비어있거나 공백만으로 구성될 수 없습니다.", HttpStatus.BAD_REQUEST),
    TITLE_LENGTH_EXCEEDED("TITLE_LENGTH_EXCEEDED", "방 제목 길이가 허용 범위를 초과했습니다.", HttpStatus.BAD_REQUEST),
    TITLE_DUPLICATION("TITLE_DUPLICATION", "이미 사용 중인 방 제목입니다.", HttpStatus.CONFLICT),

    // 비밀번호
    PASSWORD_LENGTH_EXCEEDED("PASSWORD_LENGTH_EXCEEDED", "비밀번호 길이가 허용 범위를 초과했습니다.", HttpStatus.BAD_REQUEST),
    PASSWORD_INCORRECT("PASSWORD_INCORRECT", "비밀번호가 일치하지 않습니다.", HttpStatus.UNAUTHORIZED),
    PASSWORD_BLANK("PASSWORD_BLANK", "비밀번호는 비어있을 수 없습니다.", HttpStatus.BAD_REQUEST),

    // 정원
    CAPACITY_EXCEEDED("CAPACITY_EXCEEDED", "최대 인원 값이 허용 범위를 초과했습니다.", HttpStatus.BAD_REQUEST),
    CAPACITY_NULL("CAPACITY_NULL", "허용 인원 수는 null일 수 없습니다.", HttpStatus.BAD_REQUEST),
    ROOM_FULL("ROOM_FULL", "방의 현재 정원이 가득 찼습니다.", HttpStatus.FORBIDDEN),

    // 닉네임
    NICKNAME_BLANK("NICKNAME_BLANK", "닉네임은 비어있을 수 없습니다.", HttpStatus.BAD_REQUEST),
    NICKNAME_NULL("NICKNAME_NULL", "닉네임은 null일 수 없습니다.", HttpStatus.BAD_REQUEST),
    NICKNAME_LENGTH_EXCEED("NICKNAME_LENGTH_EXCEED", "닉네임 길이는 1~16자 이내여야 합니다.", HttpStatus.BAD_REQUEST),
    NICKNAME_DUPLICATION("NICKNAME_DUPLICATION", "이미 사용 중인 닉네임입니다.", HttpStatus.CONFLICT),
    NICKNAME_NOT_FOUND("NICKNAME_NOT_FOUND", "해당 닉네임은 방에 존재하지 않습니다.", HttpStatus.NOT_FOUND),

    // 방
    ROOM_NOT_FOUND("ROOM_NOT_FOUND", "요청한 ID의 채팅방을 찾을 수 없습니다.", HttpStatus.NOT_FOUND),

    // 메시지
    MESSAGE_LENGTH_EXCEEDED("MESSAGE_LENGTH_EXCEEDED", "메시지의 길이는 200자 이하여야 합니다.", HttpStatus.BAD_REQUEST),
    MESSAGE_CONTENT_EMPTY("MESSAGE_CONTENT_EMPTY", "메시지는 비어있을 수 없습니다.", HttpStatus.BAD_REQUEST);

    private final String code;
    private final String message;
    private final HttpStatus status;

    ErrorCode(String code, String message, HttpStatus status) {
        this.code = code;
        this.message = message;
        this.status = status;
    }

    public String code() {
        return code;
    }

    public String message() {
        return message;
    }

    public HttpStatus status() {
        return status;
    }
}
