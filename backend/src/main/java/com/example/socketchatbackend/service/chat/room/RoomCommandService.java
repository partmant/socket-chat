package com.example.socketchatbackend.service.chat.room;

import static com.example.socketchatbackend.exception.chat.ErrorMessages.*;

import org.springframework.messaging.simp.SimpMessagingTemplate;
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
    private final SimpMessagingTemplate messagingTemplate;

    public RoomCommandService(RoomRepository roomRepository,
                              SimpMessagingTemplate messagingTemplate) {
        this.roomRepository = roomRepository;
        this.messagingTemplate = messagingTemplate;
    }

    public Long create(RoomCreateRequest request) {
        RoomTitle title = new RoomTitle(request.title());
        RoomPassword password = createRoomPassword(request.password());
        RoomCapacity capacity = new RoomCapacity(request.maxUserCount());

        validateDuplicateTitle(title);

        Room room = Room.of(title, password, capacity);

        Room saved = roomRepository.save(room);

        messagingTemplate.convertAndSend("/topic/rooms", "update");

        return saved.id();
    }

    private RoomPassword createRoomPassword(String rawPassword) {
        // 비밀번호 설정 X
        if (rawPassword == null || rawPassword.isBlank()) {
            return RoomPassword.none();
        }

        // 비밀번호 설정 O
        return RoomPassword.of(rawPassword);
    }

    private void validateDuplicateTitle(RoomTitle title) {
        if (roomRepository.existsByTitle(title)) {
            throw new IllegalArgumentException(TITLE_DUPLICATION.getMessage());
        }
    }
}
