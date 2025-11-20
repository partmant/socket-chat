package com.example.socketchatbackend.service.chat.message;

import org.springframework.stereotype.Component;

import com.example.socketchatbackend.dto.chat.message.MessageRequest;
import com.example.socketchatbackend.dto.chat.message.MessageResponse;
import com.example.socketchatbackend.dto.chat.message.MessageType;
import com.example.socketchatbackend.util.ChatConstants;

@Component
public class MessageFactory {

    public MessageResponse createEnterMessage(MessageRequest req) {
        return new MessageResponse(
                req.roomId(),
                MessageType.ENTER,
                ChatConstants.SYSTEM_SENDER,
                String.format(ChatConstants.ENTER_MESSAGE_FORMAT, req.sender())
        );
    }

    public MessageResponse createExitMessage(MessageRequest req) {
        return new MessageResponse(
                req.roomId(),
                MessageType.EXIT,
                ChatConstants.SYSTEM_SENDER,
                String.format(ChatConstants.QUIT_MESSAGE_FORMAT, req.sender())
        );
    }

    public MessageResponse createTalkMessage(MessageRequest req) {
        return MessageResponse.from(req);
    }
}
