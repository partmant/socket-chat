package com.example.socketchatbackend.dto.chat.message;

public record ChatMessageResponse(
        Long roomId,
        MessageType type,
        String sender,
        String content
) {
    public static ChatMessageResponse from(ChatMessageRequest req) {
        return new ChatMessageResponse(
                req.roomId(),
                req.type(),
                req.sender(),
                req.content()
        );
    }
}
