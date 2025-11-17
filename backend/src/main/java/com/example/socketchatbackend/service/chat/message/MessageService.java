package com.example.socketchatbackend.service.chat.message;

import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import com.example.socketchatbackend.dto.chat.message.ChatMessageRequest;
import com.example.socketchatbackend.dto.chat.message.ChatMessageResponse;
import com.example.socketchatbackend.dto.chat.message.MessageType;
import com.example.socketchatbackend.util.ChatConstants;

@Service
public class MessageService {

    private final SimpMessagingTemplate messagingTemplate;

    public MessageService(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    public void broadcast(ChatMessageRequest req) {
        ChatMessageResponse res = switch (req.type()) {
            case ENTER -> createEnterMessage(req);
            case QUIT -> createQuitMessage(req);
            case TALK -> ChatMessageResponse.from(req);
        };

        messagingTemplate.convertAndSend("/topic/room/" + req.roomId(), res);
    }

    private ChatMessageResponse createEnterMessage(ChatMessageRequest req) {
        return new ChatMessageResponse(
                req.roomId(),
                MessageType.ENTER,
                ChatConstants.SYSTEM_SENDER,
                String.format(ChatConstants.ENTER_MESSAGE_FORMAT, req.sender())
        );
    }

    private ChatMessageResponse createQuitMessage(ChatMessageRequest req) {
        return new ChatMessageResponse(
                req.roomId(),
                MessageType.QUIT,
                ChatConstants.SYSTEM_SENDER,
                String.format(ChatConstants.QUIT_MESSAGE_FORMAT, req.sender())
        );
    }
}
