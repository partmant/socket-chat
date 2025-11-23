package com.example.socketchatbackend.controller.chat.room;

import com.example.socketchatbackend.dto.chat.message.RoomMessageRequest;
import com.example.socketchatbackend.service.chat.room.RoomMessageService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/rooms")
@CrossOrigin(origins = "http://localhost:5173")
public class RoomMessageController {

    private final RoomMessageService roomMessageService;

    public RoomMessageController(RoomMessageService roomMessageService) {
        this.roomMessageService = roomMessageService;
    }

    @PostMapping("/{id}/message")
    public ResponseEntity<Void> sendMessage(@PathVariable Long id, @RequestBody RoomMessageRequest request) {
        RoomMessageRequest validated = new RoomMessageRequest(
                id,
                request.type(),
                request.sender(),
                request.content()
        );

        roomMessageService.sendTalkMessage(validated);
        return ResponseEntity.ok().build();
    }
}
