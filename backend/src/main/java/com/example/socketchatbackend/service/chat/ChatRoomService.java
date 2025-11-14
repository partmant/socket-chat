package com.example.socketchatbackend.service.chat;

import com.example.socketchatbackend.doamin.chat.ChatRoom;
import com.example.socketchatbackend.dto.chat.ChatRoomRequest;
import com.example.socketchatbackend.repository.chat.ChatRoomRepository;

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
