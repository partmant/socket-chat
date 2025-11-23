package com.example.socketchatbackend.controller.chat.websocket;

import com.example.socketchatbackend.dto.chat.message.MessageType;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

import com.example.socketchatbackend.dto.chat.message.RoomMessageRequest;
import com.example.socketchatbackend.service.chat.room.RoomMessageService;

@Controller
public class WebSocketMessageController {

    private final RoomMessageService roomMessageService;

    public WebSocketMessageController(RoomMessageService roomMessageService) {
        this.roomMessageService = roomMessageService;
    }

    @MessageMapping("/chat.send")
    public void send(RoomMessageRequest request) {
        if (request.type() != MessageType.TALK) {   // TALK 타입만 처리
            return;
        }

        roomMessageService.sendTalkMessage(request);
    }
}
