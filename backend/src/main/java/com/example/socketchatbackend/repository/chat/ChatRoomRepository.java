package com.example.socketchatbackend.repository.chat;

import java.util.List;
import java.util.Optional;

import com.example.socketchatbackend.doamin.chat.ChatRoom;

public interface ChatRoomRepository {

    ChatRoom save(ChatRoom chatRoom);

    Optional<ChatRoom> findById(Long id);

    List<ChatRoom> findAll();

    boolean existsByTitle(String title);
}
