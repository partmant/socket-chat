package com.example.socketchatbackend.service.chat.message;

import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

import com.example.socketchatbackend.dto.chat.message.RoomMessageResponse;

@Component
public class MessageBroadcaster {

    private final SimpMessagingTemplate messagingTemplate;

    public MessageBroadcaster(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    public void broadcast(Long roomId, RoomMessageResponse response) {
        messagingTemplate.convertAndSend("/topic/room/" + roomId, response);
    }
}
