package com.example.socketchatbackend.service.chat.room;

import static com.example.socketchatbackend.exception.chat.ErrorMessages.*;

import java.util.List;

import com.example.socketchatbackend.domain.chat.Room;
import com.example.socketchatbackend.dto.chat.room.RoomResponse;
import com.example.socketchatbackend.repository.chat.RoomRepository;

public class RoomQueryService {

    private final RoomRepository roomRepository;

    public RoomQueryService(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    public List<RoomResponse> findAll(String keyword) {
        List<Room> rooms = roomRepository.findAll();
        return filterByKeyword(rooms, keyword).stream()
                .map(RoomResponse::from)
                .toList();
    }

    public RoomResponse findById(Long id) {
        Room room = roomRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(ROOM_NOT_FOUND.getMessage()));

        return RoomResponse.from(room);
    }

    private List<Room> filterByKeyword(List<Room> rooms, String keyword) {
        if (keyword == null || keyword.isBlank()) {
            return rooms;
        }
        return rooms.stream()
                .filter(room -> room.title().value().contains(keyword))
                .toList();
    }
}
