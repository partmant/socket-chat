package com.example.socketchatbackend.repository.chat;

import java.util.List;
import java.util.Optional;

import com.example.socketchatbackend.domain.chat.Room;
import com.example.socketchatbackend.domain.chat.vo.RoomTitle;

public interface RoomRepository {
    Room save(Room room);
    void delete(Long id);
    Optional<Room> findById(Long id);
    List<Room> findAll();
    boolean existsByTitle(RoomTitle title);
}
