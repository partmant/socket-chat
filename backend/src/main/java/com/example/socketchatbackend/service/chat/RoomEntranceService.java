package com.example.socketchatbackend.service.chat;

import static com.example.socketchatbackend.exception.chat.ChatErrorMessages.*;

import com.example.socketchatbackend.domain.chat.ChatRoom;
import com.example.socketchatbackend.repository.chat.ChatRoomRepository;

public class RoomEntranceService {

    private final ChatRoomRepository chatRoomRepository;

    public RoomEntranceService(ChatRoomRepository chatRoomRepository) {
        this.chatRoomRepository = chatRoomRepository;
    }

    public void validateEnter(Long id, String password) {
        ChatRoom room = chatRoomRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(ROOM_NOT_FOUND.getMessage()));

        if (!room.hasPassword()) return;

        if (password == null || !room.password().equals(password)) {
            throw new IllegalArgumentException(INVALID_PASSWORD.getMessage());
        }
    }
}
