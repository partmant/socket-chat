package com.example.socketchatbackend.service.chat.message;

import org.springframework.stereotype.Service;

import com.example.socketchatbackend.dto.chat.message.MessageRequest;
import com.example.socketchatbackend.dto.chat.message.MessageResponse;
import com.example.socketchatbackend.dto.chat.message.MessageType;

@Service
public class MessageService {

    private final MessageFactory messageFactory;
    private final MessageBroadcaster messageBroadcaster;

    public MessageService(MessageFactory messageFactory,
                          MessageBroadcaster messageBroadcaster) {
        this.messageFactory = messageFactory;
        this.messageBroadcaster = messageBroadcaster;
    }

    public void broadcast(MessageRequest req) {
        MessageResponse res = toResponse(req);
        messageBroadcaster.broadcast(req.roomId(), res);
    }

    private MessageResponse toResponse(MessageRequest req) {
        return switch (req.type()) {
            case MessageType.ENTER -> messageFactory.createEnterMessage(req);
            case MessageType.EXIT  -> messageFactory.createExitMessage(req);
            case MessageType.TALK  -> messageFactory.createTalkMessage(req);
        };
    }
}
