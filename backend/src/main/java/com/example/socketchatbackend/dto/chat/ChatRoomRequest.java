package com.example.socketchatbackend.dto.chat;

import lombok.Getter;

@Getter
public class ChatRoomRequest {

    private final String title;
    private final String password;
    private final Integer maxUserCount;

    public ChatRoomRequest(String title, String password, Integer maxUserCount) {
        this.title = title;
        this.password = password;
        this.maxUserCount = maxUserCount;
    }
}
