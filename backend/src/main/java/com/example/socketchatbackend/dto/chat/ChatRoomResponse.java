package com.example.socketchatbackend.dto.chat;

import com.example.socketchatbackend.domain.chat.ChatRoom;

public record ChatRoomResponse(Long id, String title, boolean hasPassword, int maxUserCount) {

    public static ChatRoomResponse from(ChatRoom room) {
        return new ChatRoomResponse(
                room.id(),
                room.title(),
                room.hasPassword(),
                room.maxUserCount()
        );
    }
}
