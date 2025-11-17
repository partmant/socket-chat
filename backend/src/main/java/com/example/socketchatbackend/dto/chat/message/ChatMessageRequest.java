package com.example.socketchatbackend.dto.chat.message;

public record ChatMessageRequest(
        Long roomId,
        MessageType type,
        String sender,
        String content
) {}
