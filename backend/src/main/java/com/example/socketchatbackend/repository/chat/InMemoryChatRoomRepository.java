package com.example.socketchatbackend.repository.chat;

import java.util.*;

import com.example.socketchatbackend.doamin.chat.ChatRoom;

public class InMemoryChatRoomRepository implements ChatRoomRepository {

    private final Map<Long, ChatRoom> store = new HashMap<>();
    private long sequence = 0L;

    @Override
    public ChatRoom save(String title, String password, Integer maxUserCount) {
        ChatRoom room = ChatRoom.of(++sequence, title, password, maxUserCount);
        store.put(room.getId(), room);
        return room;
    }

    @Override
    public Optional<ChatRoom> findById(Long id) {
        return Optional.ofNullable(store.get(id));
    }

    @Override
    public List<ChatRoom> findAll() {
        return new ArrayList<>(store.values());
    }
}
