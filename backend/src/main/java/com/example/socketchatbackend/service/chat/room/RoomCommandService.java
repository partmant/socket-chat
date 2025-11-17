package com.example.socketchatbackend.service.chat.room;

import static com.example.socketchatbackend.exception.chat.ErrorMessages.*;

import org.springframework.stereotype.Service;

import com.example.socketchatbackend.domain.chat.Room;
import com.example.socketchatbackend.domain.chat.vo.RoomTitle;
import com.example.socketchatbackend.domain.chat.vo.RoomPassword;
import com.example.socketchatbackend.domain.chat.vo.RoomCapacity;
import com.example.socketchatbackend.dto.chat.room.RoomCreateRequest;
import com.example.socketchatbackend.repository.chat.RoomRepository;

@Service
public class RoomCommandService {

    private final RoomRepository roomRepository;

    public RoomCommandService(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    public Long create(RoomCreateRequest request) {
        RoomTitle title = new RoomTitle(request.title());
        RoomPassword password = new RoomPassword(request.password());
        RoomCapacity capacity = new RoomCapacity(request.maxUserCount());

        validateDuplicateTitle(title);

        Room room = Room.of(title, password, capacity);

        Room saved = roomRepository.save(room);
        return saved.id();
    }

    private void validateDuplicateTitle(RoomTitle title) {
        if (roomRepository.existsByTitle(title)) {
            throw new IllegalArgumentException(TITLE_DUPLICATION.getMessage());
        }
    }
}
