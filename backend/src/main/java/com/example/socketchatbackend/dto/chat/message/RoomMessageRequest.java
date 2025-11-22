package com.example.socketchatbackend.dto.chat.message;

public record RoomMessageRequest(
        Long roomId,
        MessageType type,
        String sender,
        String content
) {}
