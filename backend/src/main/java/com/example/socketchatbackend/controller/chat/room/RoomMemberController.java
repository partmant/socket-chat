package com.example.socketchatbackend.controller.chat.room;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.socketchatbackend.dto.chat.room.RoomEnterRequest;
import com.example.socketchatbackend.dto.chat.room.RoomExitRequest;
import com.example.socketchatbackend.service.chat.room.RoomEntranceService;
import com.example.socketchatbackend.service.chat.room.RoomExitService;

@RestController
@RequestMapping("/api/rooms")
@CrossOrigin(origins = "http://localhost:5173")
public class RoomMemberController {

    private final RoomEntranceService entranceService;
    private final RoomExitService exitService;

    public RoomMemberController(RoomEntranceService entranceService,
                                RoomExitService exitService) {
        this.entranceService = entranceService;
        this.exitService = exitService;
    }

    @PostMapping("/{id}/enter")
    public ResponseEntity<Void> enter(@PathVariable Long id, @RequestBody RoomEnterRequest request) {
        entranceService.enter(id, request);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{id}/exit")
    public ResponseEntity<Void> exit(@PathVariable Long id, @RequestBody RoomExitRequest request) {
        exitService.exit(id, request);
        return ResponseEntity.ok().build();
    }
}
