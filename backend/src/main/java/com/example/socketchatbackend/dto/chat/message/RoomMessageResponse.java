package com.example.socketchatbackend.dto.chat.message;

public record RoomMessageResponse(
        Long roomId,
        MessageType type,
        String sender,
        String content
) {}
