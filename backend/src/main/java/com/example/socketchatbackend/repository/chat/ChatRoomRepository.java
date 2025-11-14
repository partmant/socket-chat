package com.example.socketchatbackend.repository.chat;

import java.util.HashMap;
import java.util.Map;

import com.example.socketchatbackend.doamin.chat.ChatRoom;

public class ChatRoomRepository {

    private final Map<Long, ChatRoom> store = new HashMap<>();
    private long sequence = 0L;

    public ChatRoom save(String title, String password, Integer maxUserCount) {
        ChatRoom chatRoom = new ChatRoom(++sequence, title, password, maxUserCount);
        store.put(chatRoom.getId(), chatRoom);
        return chatRoom;
    }
}
