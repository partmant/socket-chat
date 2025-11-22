package com.example.socketchatbackend.dto.chat.message;

import lombok.Builder;

@Builder
public record RoomMessageRequest(
        Long roomId,
        MessageType type,
        String sender,
        String content
) {}
