package com.example.socketchatbackend.dto.chat.room;

public record RoomEnterRequest(
        String nickname,
        String password
) {}
