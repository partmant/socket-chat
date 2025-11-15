package com.example.socketchatbackend.service.chat;

import static com.example.socketchatbackend.exception.chat.ChatErrorMessages.*;

import com.example.socketchatbackend.domain.chat.ChatRoom;
import com.example.socketchatbackend.dto.chat.ChatRoomRequest;
import com.example.socketchatbackend.repository.chat.ChatRoomRepository;

public class RoomCommandService {

    private final ChatRoomRepository chatRoomRepository;

    public RoomCommandService(ChatRoomRepository chatRoomRepository) {
        this.chatRoomRepository = chatRoomRepository;
    }

    public Long create(ChatRoomRequest request) {
        validateDuplicateTitle(request.title());

        ChatRoom chatRoom = ChatRoom.of(
                request.title(),
                request.password(),
                request.maxUserCount()
        );

        ChatRoom saved = chatRoomRepository.save(chatRoom);
        return saved.id();
    }

    private void validateDuplicateTitle(String title) {
        if (chatRoomRepository.existsByTitle(title)) {
            throw new IllegalArgumentException(TITLE_DUPLICATION.getMessage());
        }
    }
}
