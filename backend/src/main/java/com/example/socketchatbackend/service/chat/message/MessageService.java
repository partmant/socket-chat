package com.example.socketchatbackend.service.chat.message;

import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import com.example.socketchatbackend.dto.chat.message.ChatMessageRequest;
import com.example.socketchatbackend.dto.chat.message.ChatMessageResponse;

@Service
public class MessageService {

    private final SimpMessagingTemplate messagingTemplate;

    public MessageService(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    public void broadcast(ChatMessageRequest req) {
        ChatMessageResponse res = ChatMessageResponse.from(req);

        messagingTemplate.convertAndSend(
                "/topic/room/" + req.roomId(),
                res
        );
    }
}
