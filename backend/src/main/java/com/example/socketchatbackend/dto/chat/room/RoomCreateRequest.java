package com.example.socketchatbackend.dto.chat.room;

public record RoomCreateRequest(
        String title,
        String password,
        Integer maxUserCount
) {}
