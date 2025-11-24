package com.example.socketchatbackend.service.chat.room;

import static com.example.socketchatbackend.exception.chat.ErrorMessages.*;

import com.example.socketchatbackend.domain.chat.vo.RoomNickname;
import com.example.socketchatbackend.dto.chat.message.RoomMessageRequest;
import com.example.socketchatbackend.dto.chat.message.MessageType;
import com.example.socketchatbackend.service.chat.message.MessageService;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import com.example.socketchatbackend.domain.chat.Room;
import com.example.socketchatbackend.dto.chat.room.RoomEnterRequest;
import com.example.socketchatbackend.repository.chat.RoomRepository;
import com.example.socketchatbackend.repository.chat.RoomMemberRepository;

@Service
public class RoomEntranceService {

    private final RoomRepository roomRepository;
    private final RoomMemberRepository memberRepository;
    private final MessageService messageService;
    private final SimpMessagingTemplate messagingTemplate;

    public RoomEntranceService(RoomRepository roomRepository,
                               RoomMemberRepository memberRepository,
                               MessageService messageService,
                               SimpMessagingTemplate messagingTemplate) {
        this.roomRepository = roomRepository;
        this.memberRepository = memberRepository;
        this.messageService = messageService;
        this.messagingTemplate = messagingTemplate;
    }

    public void enter(Long roomId, RoomEnterRequest request) {
        // 1. 방 존재 + 비밀번호 검증
        Room room = verifyRoomAccessAndGetRoom(roomId, request.password());

        // 2. 닉네임 검증
        RoomNickname nickname = new RoomNickname(request.nickname());

        // 3. 중복 / 정원 초과 체크
        checkRoomEntrancePreconditions(room, nickname);

        // 4. 저장소에 입장 처리
        memberRepository.add(roomId, nickname.value());

        // 5. 방 목록 갱신
        messagingTemplate.convertAndSend("/topic/rooms", "update");

        // 6. 입장 WebSocket 메시지 전송
        RoomMessageRequest enterRequest = new RoomMessageRequest(
                roomId,
                MessageType.ENTER,
                nickname.value(),
                null
        );
        messageService.broadcast(enterRequest);
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
