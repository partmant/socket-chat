package com.example.socketchatbackend.controller.chat.websocket;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

import com.example.socketchatbackend.dto.chat.message.RoomMessageRequest;
import com.example.socketchatbackend.service.chat.message.MessageService;

@Controller
public class WebSocketMessageController {

    private final MessageService messageService;

    public WebSocketMessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    @MessageMapping("/chat.send")
    public void send(RoomMessageRequest request) {
        messageService.broadcast(request);
    }
}
