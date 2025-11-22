package com.example.socketchatbackend.service.chat.room;

import static com.example.socketchatbackend.exception.chat.ErrorMessages.*;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.socketchatbackend.domain.chat.Room;
import com.example.socketchatbackend.dto.chat.room.RoomInfoResponse;
import com.example.socketchatbackend.repository.chat.RoomRepository;


@Service
public class RoomQueryService {

    private final RoomRepository roomRepository;

    public RoomQueryService(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    public List<RoomInfoResponse> findAll(String keyword) {
        List<Room> rooms = roomRepository.findAll();
        return filterByKeyword(rooms, keyword).stream()
                .map(RoomInfoResponse::from)
                .toList();
    }

    public RoomInfoResponse findById(Long id) {
        Room room = roomRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(ROOM_NOT_FOUND.getMessage()));

        return RoomInfoResponse.from(room);
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
