package com.example.socketchatbackend.service.chat;

import static com.example.socketchatbackend.exception.chat.ChatErrorMessages.*;

import java.util.List;

import com.example.socketchatbackend.doamin.chat.ChatRoom;
import com.example.socketchatbackend.dto.chat.ChatRoomInfoResponse;
import com.example.socketchatbackend.dto.chat.ChatRoomRequest;
import com.example.socketchatbackend.repository.chat.ChatRoomRepository;

public class ChatRoomService {

    private final ChatRoomRepository chatRoomRepository;

    public ChatRoomService(ChatRoomRepository chatRoomRepository) {
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

    public List<ChatRoomInfoResponse> findAll(String keyword) {
        List<ChatRoom> rooms = chatRoomRepository.findAll();
        return filterByKeyword(rooms, keyword).stream()
                .map(ChatRoomInfoResponse::from)
                .toList();
    }

    private List<ChatRoom> filterByKeyword(List<ChatRoom> rooms, String keyword) {
        if (keyword == null || keyword.isBlank()) return rooms;

        return rooms.stream()
                .filter(room -> room.title().contains(keyword))
                .toList();
    }

    public ChatRoomInfoResponse findById(Long id) {
        ChatRoom room = chatRoomRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(ROOM_NOT_FOUND.getMessage()));

        return ChatRoomInfoResponse.from(room);
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
