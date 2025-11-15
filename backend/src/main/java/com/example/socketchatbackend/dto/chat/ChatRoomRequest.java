package com.example.socketchatbackend.dto.chat;

public record ChatRoomRequest(String title, String password, Integer maxUserCount) {}
