package com.example.socketchatbackend.dto.chat;

public record RoomRequest(String title, String password, Integer maxUserCount) {}
