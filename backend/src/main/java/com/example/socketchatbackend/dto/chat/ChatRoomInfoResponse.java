package com.example.socketchatbackend.dto.chat;

import lombok.Getter;

import com.example.socketchatbackend.doamin.chat.ChatRoom;

@Getter
public class ChatRoomInfoResponse {

    private final Long id;
    private final String title;
    private final boolean hasPassword;
    private final int maxUserCount;

    private ChatRoomInfoResponse(Long id, String title, boolean hasPassword, int maxUserCount) {
        this.id = id;
        this.title = title;
        this.hasPassword = hasPassword;
        this.maxUserCount = maxUserCount;
    }

    public static ChatRoomInfoResponse from(ChatRoom room) {
        return new ChatRoomInfoResponse(
                room.getId(),
                room.getTitle(),
                room.hasPassword(),
                room.getMaxUserCount()
        );
    }
}
