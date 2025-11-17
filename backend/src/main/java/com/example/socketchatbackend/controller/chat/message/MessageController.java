package com.example.socketchatbackend.controller.chat.message;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

import com.example.socketchatbackend.dto.chat.message.ChatMessageRequest;
import com.example.socketchatbackend.service.chat.message.MessageService;

@Controller
public class MessageController {

    private final MessageService messageService;

    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    @MessageMapping("/chat.send")
    public void send(ChatMessageRequest request) {
        messageService.broadcast(request);
    }
}
