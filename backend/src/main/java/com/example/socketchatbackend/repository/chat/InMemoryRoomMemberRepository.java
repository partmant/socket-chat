package com.example.socketchatbackend.repository.chat;

import java.util.*;

import org.springframework.stereotype.Repository;

@Repository
public class InMemoryRoomMemberRepository implements RoomMemberRepository {

    private final Map<Long, Set<String>> store = new HashMap<>();

    @Override
    public int count(Long roomId) {
        return store.getOrDefault(roomId, Collections.emptySet()).size();
    }

    @Override
    public boolean exists(Long roomId, String nickname) {
        return store.getOrDefault(roomId, Collections.emptySet()).contains(nickname);
    }

    @Override
    public void add(Long roomId, String nickname) {
        store.computeIfAbsent(roomId, id -> new HashSet<>()).add(nickname);
    }

    @Override
    public void remove(Long roomId, String nickname) {
        Set<String> users = store.get(roomId);
        if (users == null) return;

        users.remove(nickname);
        if (users.isEmpty()) {
            store.remove(roomId);
        }
    }

    @Override
    public List<String> findAll(Long roomId) {
        return new ArrayList<>(store.getOrDefault(roomId, Collections.emptySet()));
    }
}
