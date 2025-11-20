package com.example.socketchatbackend.service.chat.message;

import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import com.example.socketchatbackend.dto.chat.message.MessageRequest;
import com.example.socketchatbackend.dto.chat.message.MessageResponse;

@Service
public class MessageService {

    private final SimpMessagingTemplate messagingTemplate;
    private final MessageFactory messageFactory;

    public MessageService(SimpMessagingTemplate messagingTemplate,
                          MessageFactory messageFactory) {
        this.messagingTemplate = messagingTemplate;
        this.messageFactory = messageFactory;
    }

    public void broadcast(MessageRequest req) {
        MessageResponse res = switch (req.type()) {
            case ENTER -> messageFactory.createEnterMessage(req);
            case EXIT -> messageFactory.createExitMessage(req);
            case TALK -> messageFactory.createTalkMessage(req);
        };

        messagingTemplate.convertAndSend("/topic/room/" + req.roomId(), res);
    }
}
