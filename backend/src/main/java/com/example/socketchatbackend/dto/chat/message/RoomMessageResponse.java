package com.example.socketchatbackend.dto.chat.message;

public record RoomMessageResponse(
        Long roomId,
        MessageType type,
        String sender,
        String content
) {

    public static RoomMessageResponse from(RoomMessageRequest req) {
        return new RoomMessageResponse(
                req.roomId(),
                req.type(),
                req.sender(),
                req.content()
        );
    }
}
