package com.example.socketchatbackend.controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class TestChatController {

    @MessageMapping("/test")
    @SendTo("/topic/test")
    public String test(String message) {
        return "echo: " + message;
    }
}
