package com.example.socketchatbackend.service.chat.room;

import static com.example.socketchatbackend.exception.chat.ErrorMessages.*;

import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import com.example.socketchatbackend.domain.chat.vo.RoomNickname;
import com.example.socketchatbackend.dto.chat.room.RoomExitRequest;
import com.example.socketchatbackend.dto.chat.message.RoomMessageRequest;
import com.example.socketchatbackend.dto.chat.message.MessageType;
import com.example.socketchatbackend.repository.chat.RoomMemberRepository;
import com.example.socketchatbackend.repository.chat.RoomRepository;
import com.example.socketchatbackend.service.chat.message.MessageService;

@Service
public class RoomExitService {

    private final RoomRepository roomRepository;
    private final RoomMemberRepository memberRepository;
    private final MessageService messageService;
    private final SimpMessagingTemplate messagingTemplate;

    public RoomExitService(RoomRepository roomRepository,
                           RoomMemberRepository memberRepository,
                           MessageService messageService,
                           SimpMessagingTemplate messagingTemplate) {
        this.roomRepository = roomRepository;
        this.memberRepository = memberRepository;
        this.messageService = messageService;
        this.messagingTemplate = messagingTemplate;
    }

    public void exit(Long roomId, RoomExitRequest request) {
        RoomNickname nickname = new RoomNickname(request.nickname());

        validateRoomExists(roomId);
        validateMemberExists(roomId, nickname);

        RoomMessageRequest exitRequest = new RoomMessageRequest(
                roomId,
                MessageType.EXIT,
                nickname.value(),
                null
        );

        messageService.broadcast(exitRequest);

        memberRepository.remove(roomId, nickname.value());

        messagingTemplate.convertAndSend("/topic/rooms", "update");
    }

    private void validateRoomExists(Long roomId) {
        roomRepository.findById(roomId)
                .orElseThrow(() -> new IllegalArgumentException(ROOM_NOT_FOUND.getMessage()));
    }

    private void validateMemberExists(Long roomId, RoomNickname nickname) {
        if (!memberRepository.exists(roomId, nickname.value())) {
            throw new IllegalArgumentException(NICKNAME_NOT_FOUND.getMessage());
        }
    }
}
