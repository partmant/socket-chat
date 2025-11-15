package com.example.socketchatbackend.service.chat;

import static com.example.socketchatbackend.exception.chat.ChatErrorMessages.TITLE_DUPLICATION;

import com.example.socketchatbackend.doamin.chat.ChatRoom;
import com.example.socketchatbackend.dto.chat.ChatRoomInfoResponse;
import com.example.socketchatbackend.dto.chat.ChatRoomRequest;
import com.example.socketchatbackend.repository.chat.ChatRoomRepository;

import java.util.List;

public class ChatRoomService {

    private final ChatRoomRepository chatRoomRepository;

    public ChatRoomService(ChatRoomRepository chatRoomRepository) {
        this.chatRoomRepository = chatRoomRepository;
    }

    public Long create(ChatRoomRequest request) {
        if (chatRoomRepository.existsByTitle(request.title())) {
            throw new IllegalArgumentException(TITLE_DUPLICATION.getMessage());
        }

        ChatRoom chatRoom = chatRoomRepository.save(
                request.title(),
                request.password(),
                request.maxUserCount()
        );

        return chatRoom.id();
    }

    public List<ChatRoomInfoResponse> findAll(String keyword) {
        List<ChatRoom> rooms = chatRoomRepository.findAll();

        return rooms.stream()
                .filter(room -> isKeywordMatch(room, keyword))
                .map(ChatRoomInfoResponse::from)
                .toList();
    }

    private boolean isKeywordMatch(ChatRoom room, String keyword) {
        if (keyword == null || keyword.isBlank()) {
            return true;
        }
        return room.title().contains(keyword);
    }
}
