package com.example.socketchatbackend.repository.chat;

import java.util.*;

import com.example.socketchatbackend.doamin.chat.ChatRoom;

public class InMemoryChatRoomRepository implements ChatRoomRepository {

    private final Map<Long, ChatRoom> store = new HashMap<>();
    private long sequence = 0L;

    @Override
    public ChatRoom save(ChatRoom chatRoom) {
        long newId = ++sequence;
        ChatRoom savedRoom = chatRoom.withId(newId);

        store.put(newId, savedRoom);

        return savedRoom;
    }

    @Override
    public Optional<ChatRoom> findById(Long id) {
        return Optional.ofNullable(store.get(id));
    }

    @Override
    public List<ChatRoom> findAll() {
        return new ArrayList<>(store.values());
    }

    @Override
    public boolean existsByTitle(String title) {
        return store.values().stream()
                .anyMatch(room -> room.title().equals(title));
    }
}
