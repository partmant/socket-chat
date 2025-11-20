package com.example.socketchatbackend.dto.chat.message;

public record MessageRequest(
        Long roomId,
        MessageType type,
        String sender,
        String content
) {}
