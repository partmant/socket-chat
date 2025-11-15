package com.example.socketchatbackend.repository.chat;

import java.util.*;

import com.example.socketchatbackend.domain.chat.Room;

public class InMemoryRoomRepository implements RoomRepository {

    private final Map<Long, Room> store = new HashMap<>();
    private long sequence = 0L;

    @Override
    public Room save(Room room) {
        long newId = ++sequence;
        Room savedRoom = room.withId(newId);

        store.put(newId, savedRoom);

        return savedRoom;
    }

    @Override
    public Optional<Room> findById(Long id) {
        return Optional.ofNullable(store.get(id));
    }

    @Override
    public List<Room> findAll() {
        return new ArrayList<>(store.values());
    }

    @Override
    public boolean existsByTitle(String title) {
        return store.values().stream()
                .anyMatch(room -> room.title().equals(title));
    }
}
