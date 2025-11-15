package com.example.socketchatbackend.service.chat;

import static com.example.socketchatbackend.exception.chat.ErrorMessages.*;

import com.example.socketchatbackend.domain.chat.Room;
import com.example.socketchatbackend.dto.chat.RoomRequest;
import com.example.socketchatbackend.repository.chat.RoomRepository;

public class RoomCommandService {

    private final RoomRepository roomRepository;

    public RoomCommandService(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    public Long create(RoomRequest request) {
        validateDuplicateTitle(request.title());

        Room room = Room.of(
                request.title(),
                request.password(),
                request.maxUserCount()
        );

        Room saved = roomRepository.save(room);
        return saved.id();
    }

    private void validateDuplicateTitle(String title) {
        if (roomRepository.existsByTitle(title)) {
            throw new IllegalArgumentException(TITLE_DUPLICATION.getMessage());
        }
    }
}
