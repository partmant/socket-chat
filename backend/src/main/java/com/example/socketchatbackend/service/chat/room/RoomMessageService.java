package com.example.socketchatbackend.service.chat.room;

import static com.example.socketchatbackend.constraint.chat.message.MessageConstraints.MAX_TALK_MESSAGE_LENGTH;
import static com.example.socketchatbackend.exception.chat.ErrorMessages.MESSAGE_LENGTH_EXCEEDED;

import com.example.socketchatbackend.dto.chat.message.MessageType;
import org.springframework.stereotype.Service;

import com.example.socketchatbackend.dto.chat.message.RoomMessageRequest;
import com.example.socketchatbackend.dto.chat.message.RoomMessageResponse;
import com.example.socketchatbackend.service.chat.message.MessageBroadcaster;
import com.example.socketchatbackend.service.chat.message.MessageFactory;

@Service
public class RoomMessageService {

    private final RoomValidationService validationService;
    private final MessageBroadcaster broadcaster;
    private final MessageFactory messageFactory;

    public RoomMessageService(RoomValidationService validationService,
                              MessageBroadcaster broadcaster,
                              MessageFactory messageFactory) {
        this.validationService = validationService;
        this.broadcaster = broadcaster;
        this.messageFactory = messageFactory;
    }

    public void sendTalkMessage(RoomMessageRequest request) {
        validateMessageRequest(request);

        RoomMessageRequest talkRequest = new RoomMessageRequest(
                request.roomId(),
                MessageType.TALK,
                request.sender(),
                request.content()
        );

        RoomMessageResponse response = messageFactory.createTalkMessage(talkRequest);
        broadcaster.broadcast(talkRequest.roomId(), response);
    }

    private void validateMessageRequest(RoomMessageRequest request) {
        validationService.validateRoomExists(request.roomId());
        validationService.validateMemberExists(request.roomId(), request.sender());

        if (request.content() != null && request.content().length() > MAX_TALK_MESSAGE_LENGTH) {
            throw new IllegalArgumentException(MESSAGE_LENGTH_EXCEEDED.getMessage());
        }
    }
}
