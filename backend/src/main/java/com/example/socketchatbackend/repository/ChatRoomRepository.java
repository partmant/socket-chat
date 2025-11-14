package com.example.socketchatbackend.repository;

import com.example.socketchatbackend.doamin.ChatRoom;

import java.util.HashMap;
import java.util.Map;

public class ChatRoomRepository {

    private final Map<Long, ChatRoom> store = new HashMap<>();
    private long sequence = 0L;

    public ChatRoom save(String title, String password, Integer maxUserCount) {
        ChatRoom chatRoom = new ChatRoom(++sequence, title, password, maxUserCount);
        store.put(chatRoom.getId(), chatRoom);
        return chatRoom;
    }
}
