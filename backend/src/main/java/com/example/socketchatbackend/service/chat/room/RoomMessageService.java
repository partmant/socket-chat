package com.example.socketchatbackend.service.chat.room;

import static com.example.socketchatbackend.constraint.chat.message.MessageConstraints.MAX_TALK_MESSAGE_LENGTH;
import static com.example.socketchatbackend.exception.chat.ErrorMessages.MESSAGE_LENGTH_EXCEEDED;

import org.springframework.stereotype.Service;

import com.example.socketchatbackend.dto.chat.message.RoomMessageRequest;
import com.example.socketchatbackend.dto.chat.message.RoomMessageResponse;
import com.example.socketchatbackend.service.chat.message.MessageBroadcaster;

@Service
public class RoomMessageService {

    private final RoomValidationService validationService;
    private final MessageBroadcaster broadcaster;

    public RoomMessageService(RoomValidationService validationService,
                              MessageBroadcaster broadcaster) {
        this.validationService = validationService;
        this.broadcaster = broadcaster;
    }

    public void sendTalkMessage(RoomMessageRequest request) {
        validateMessageRequest(request);

        // 빈 문자열 입력 시 송수신 X
        if (request.content() == null || request.content().isBlank()) {
            return;
        }

        RoomMessageResponse response = RoomMessageResponse.from(request);
        broadcaster.broadcast(request.roomId(), response);
    }

    private void validateMessageRequest(RoomMessageRequest request) {
        validationService.validateRoomExists(request.roomId());
        validationService.validateMemberExists(request.roomId(), request.sender());


        if (request.content() != null && request.content().length() > MAX_TALK_MESSAGE_LENGTH) {
            throw new IllegalArgumentException(MESSAGE_LENGTH_EXCEEDED.getMessage());
        }
    }
}
