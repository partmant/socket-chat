package com.example.socketchatbackend.dto.chat;

import com.example.socketchatbackend.doamin.chat.ChatRoom;

public record ChatRoomInfoResponse(Long id, String title, boolean hasPassword, int maxUserCount) {

    public static ChatRoomInfoResponse from(ChatRoom room) {
        return new ChatRoomInfoResponse(
                room.id(),
                room.title(),
                room.hasPassword(),
                room.maxUserCount()
        );
    }
}
