package com.example.socketchatbackend.dto.chat.message;

import com.example.socketchatbackend.util.ChatConstants;

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

    public static ChatMessageResponse createEnterMessage(ChatMessageRequest req) {
        return new ChatMessageResponse(
                req.roomId(),
                MessageType.ENTER,
                ChatConstants.SYSTEM_SENDER,
                String.format(ChatConstants.ENTER_MESSAGE_FORMAT, req.sender())
        );
    }

    public static ChatMessageResponse createQuitMessage(ChatMessageRequest req) {
        return new ChatMessageResponse(
                req.roomId(),
                MessageType.QUIT,
                ChatConstants.SYSTEM_SENDER,
                String.format(ChatConstants.QUIT_MESSAGE_FORMAT, req.sender())
        );
    }
}
