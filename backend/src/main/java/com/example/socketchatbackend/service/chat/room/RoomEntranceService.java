package com.example.socketchatbackend.service.chat.room;

import static com.example.socketchatbackend.exception.chat.ErrorMessages.*;

import com.example.socketchatbackend.domain.chat.vo.RoomNickname;
import org.springframework.stereotype.Service;

import com.example.socketchatbackend.domain.chat.Room;
import com.example.socketchatbackend.dto.chat.room.RoomEnterRequest;
import com.example.socketchatbackend.repository.chat.RoomRepository;
import com.example.socketchatbackend.repository.chat.RoomMemberRepository;

@Service
public class RoomEntranceService {

    private final RoomRepository roomRepository;
    private final RoomMemberRepository memberRepository;

    public RoomEntranceService(RoomRepository roomRepository,
                               RoomMemberRepository memberRepository) {
        this.roomRepository = roomRepository;
        this.memberRepository = memberRepository;
    }

    public void enter(Long roomId, RoomEnterRequest request) {
        Room room = verifyRoomAccessAndGetRoom(roomId, request.password());

        RoomNickname nickname = new RoomNickname(request.nickname());

        checkRoomEntrancePreconditions(room, nickname);

        memberRepository.add(roomId, nickname.value());
    }

    public Room verifyRoomAccessAndGetRoom(Long id, String password) {
        Room room = roomRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(ROOM_NOT_FOUND.getMessage()));

        room.verifyPassword(password);
        return room;
    }

    private void checkRoomEntrancePreconditions(Room room, RoomNickname nickname) {
        Long roomId = room.id();

        if (memberRepository.exists(roomId, nickname.value())) {
            throw new IllegalArgumentException(NICKNAME_DUPLICATION.getMessage());
        }

        int current = memberRepository.count(roomId);
        if (current >= room.capacity().value()) {
            throw new IllegalArgumentException(ROOM_FULL.getMessage());
        }
    }
}
