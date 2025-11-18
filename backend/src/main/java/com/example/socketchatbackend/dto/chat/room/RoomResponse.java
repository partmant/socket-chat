package com.example.socketchatbackend.dto.chat.room;

import com.example.socketchatbackend.domain.chat.Room;

public record RoomResponse(
        Long id,
        String title,
        boolean hasPassword,
        int capacity
) {

    public static RoomResponse from(Room room) {
        return new RoomResponse(
                room.id(),
                room.title().value(),
                room.hasPassword(),
                room.capacity().value()
        );
    }
}
