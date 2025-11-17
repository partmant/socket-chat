package com.example.socketchatbackend.service.chat.room;

import static com.example.socketchatbackend.exception.chat.ErrorMessages.ROOM_NOT_FOUND;

import com.example.socketchatbackend.domain.chat.Room;
import com.example.socketchatbackend.repository.chat.RoomRepository;

public class RoomEntranceService {

    private final RoomRepository roomRepository;

    public RoomEntranceService(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    public void validateEnter(Long id, String password) {
        Room room = roomRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(ROOM_NOT_FOUND.getMessage()));

        room.verifyPassword(password);
    }
}
