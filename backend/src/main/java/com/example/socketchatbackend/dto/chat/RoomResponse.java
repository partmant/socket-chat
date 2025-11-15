package com.example.socketchatbackend.dto.chat;

import com.example.socketchatbackend.domain.chat.Room;

public record RoomResponse(Long id, String title, boolean hasPassword, int maxUserCount) {

    public static RoomResponse from(Room room) {
        return new RoomResponse(
                room.id(),
                room.title(),
                room.hasPassword(),
                room.maxUserCount()
        );
    }
}
