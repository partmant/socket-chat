package com.example.socketchatbackend.dto.chat.room;

import com.example.socketchatbackend.domain.chat.Room;

public record RoomInfoResponse(
        Long id,
        String title,
        boolean hasPassword,
        int currentUserCount,
        int maxUserCount
) {

    public static RoomInfoResponse of(Room room, int currentUsers) {
        return new RoomInfoResponse(
                room.id(),
                room.title().value(),
                room.hasPassword(),
                currentUsers,
                room.capacity().value()
        );
    }
}
