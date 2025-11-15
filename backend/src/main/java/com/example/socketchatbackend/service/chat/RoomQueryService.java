package com.example.socketchatbackend.service.chat;

import static com.example.socketchatbackend.exception.chat.ChatErrorMessages.*;

import java.util.List;

import com.example.socketchatbackend.domain.chat.ChatRoom;
import com.example.socketchatbackend.dto.chat.ChatRoomResponse;
import com.example.socketchatbackend.repository.chat.ChatRoomRepository;

public class RoomQueryService {

    private final ChatRoomRepository chatRoomRepository;

    public RoomQueryService(ChatRoomRepository chatRoomRepository) {
        this.chatRoomRepository = chatRoomRepository;
    }

    public List<ChatRoomResponse> findAll(String keyword) {
        List<ChatRoom> rooms = chatRoomRepository.findAll();
        return filterByKeyword(rooms, keyword).stream()
                .map(ChatRoomResponse::from)
                .toList();
    }

    public ChatRoomResponse findById(Long id) {
        ChatRoom room = chatRoomRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(ROOM_NOT_FOUND.getMessage()));

        return ChatRoomResponse.from(room);
    }

    private List<ChatRoom> filterByKeyword(List<ChatRoom> rooms, String keyword) {
        if (keyword == null || keyword.isBlank()) {
            return rooms;
        }
        return rooms.stream()
                .filter(room -> room.title().contains(keyword))
                .toList();
    }
}
