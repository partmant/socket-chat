package com.example.socketchatbackend.dto.chat.room;

import com.example.socketchatbackend.domain.chat.Room;

public record RoomInfoResponse(
        Long id,
        String title,
        boolean hasPassword,
        int capacity
) {

    public static RoomInfoResponse from(Room room) {
        return new RoomInfoResponse(
                room.id(),
                room.title().value(),
                room.hasPassword(),
                room.capacity().value()
        );
    }
}
