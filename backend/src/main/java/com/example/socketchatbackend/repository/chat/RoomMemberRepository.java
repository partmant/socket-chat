package com.example.socketchatbackend.repository.chat;

import java.util.List;

public interface RoomMemberRepository {
    int count(Long roomId);
    boolean exists(Long roomId, String nickname);
    void add(Long roomId, String nickname);
    void remove(Long roomId, String nickname);
    List<String> findAll(Long roomId);
}
