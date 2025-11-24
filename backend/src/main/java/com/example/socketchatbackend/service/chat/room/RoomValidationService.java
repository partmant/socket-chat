package com.example.socketchatbackend.service.chat.room;

import org.springframework.stereotype.Service;

import com.example.socketchatbackend.exception.CustomException;
import com.example.socketchatbackend.exception.ErrorCode;
import com.example.socketchatbackend.repository.chat.RoomMemberRepository;
import com.example.socketchatbackend.repository.chat.RoomRepository;

@Service
public class RoomValidationService {

    private final RoomRepository roomRepository;
    private final RoomMemberRepository memberRepository;

    public RoomValidationService(RoomRepository roomRepository,
                                 RoomMemberRepository memberRepository) {
        this.roomRepository = roomRepository;
        this.memberRepository = memberRepository;
    }

    public void validateRoomExists(Long roomId) {
        roomRepository.findById(roomId)
                .orElseThrow(
                        () -> new CustomException(ErrorCode.ROOM_NOT_FOUND)
                );
    }

    public void validateMemberExists(Long roomId, String nickname) {
        if (!memberRepository.exists(roomId, nickname)) {
            throw new CustomException(ErrorCode.NICKNAME_NOT_FOUND);
        }
    }
}
