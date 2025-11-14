package com.example.socketchatbackend.service;

import com.example.socketchatbackend.doamin.ChatRoom;
import com.example.socketchatbackend.dto.ChatRoomRequest;
import com.example.socketchatbackend.repository.ChatRoomRepository;

public class ChatRoomService {

    private final ChatRoomRepository chatRoomRepository;

    public ChatRoomService(ChatRoomRepository chatRoomRepository) {
        this.chatRoomRepository = chatRoomRepository;
    }

    public Long create(ChatRoomRequest request) {
        ChatRoom chatRoom = chatRoomRepository.save(
                request.getTitle(),
                request.getPassword(),
                request.getMaxUserCount()
        );

        return chatRoom.getId();
    }
}
