package com.example.socketchatbackend.service.chat.message;

import org.springframework.stereotype.Component;

import com.example.socketchatbackend.dto.chat.message.RoomMessageRequest;
import com.example.socketchatbackend.dto.chat.message.RoomMessageResponse;
import com.example.socketchatbackend.dto.chat.message.MessageType;
import com.example.socketchatbackend.util.ChatConstants;

@Component
public class MessageFactory {

    public RoomMessageResponse createEnterMessage(RoomMessageRequest req) {
        String nickname = verifySender(req.sender());

        return new RoomMessageResponse(
                req.roomId(),
                MessageType.ENTER,
                ChatConstants.SYSTEM_SENDER,
                String.format(ChatConstants.ENTER_MESSAGE_FORMAT, nickname)
        );
    }

    public RoomMessageResponse createExitMessage(RoomMessageRequest req) {
        String nickname = verifySender(req.sender());

        return new RoomMessageResponse(
                req.roomId(),
                MessageType.EXIT,
                ChatConstants.SYSTEM_SENDER,
                String.format(ChatConstants.QUIT_MESSAGE_FORMAT, nickname)
        );
    }

    public RoomMessageResponse createTalkMessage(RoomMessageRequest req) {
        String sanitizedContent = sanitizeContent(req.content());
        String verifiedSender = verifySender(req.sender());

        return new RoomMessageResponse(
                req.roomId(),
                MessageType.TALK,
                verifiedSender,
                sanitizedContent
        );
    }

    private String verifySender(String sender) {
        return sender;
    }

    private String sanitizeContent(String content) {
        return content;
    }
}
