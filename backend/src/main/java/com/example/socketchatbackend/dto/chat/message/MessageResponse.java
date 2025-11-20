package com.example.socketchatbackend.dto.chat.message;

public record MessageResponse(
        Long roomId,
        MessageType type,
        String sender,
        String content
) {

    public static MessageResponse from(MessageRequest req) {
        return new MessageResponse(
                req.roomId(),
                req.type(),
                req.sender(),
                req.content()
        );
    }
}
