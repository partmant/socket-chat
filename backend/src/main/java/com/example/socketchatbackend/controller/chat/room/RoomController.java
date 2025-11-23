package com.example.socketchatbackend.controller.chat.room;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.socketchatbackend.dto.chat.room.RoomCreateRequest;
import com.example.socketchatbackend.dto.chat.room.RoomInfoResponse;
import com.example.socketchatbackend.service.chat.room.RoomCommandService;
import com.example.socketchatbackend.service.chat.room.RoomQueryService;

@RestController
@RequestMapping("/api/rooms")
@CrossOrigin(origins = "http://localhost:5173")
public class RoomController {

    private final RoomCommandService commandService;
    private final RoomQueryService queryService;

    public RoomController(RoomCommandService commandService,
                          RoomQueryService queryService) {
        this.commandService = commandService;
        this.queryService = queryService;

    }

    // 방 생성
    @PostMapping
    public ResponseEntity<Long> create(@RequestBody RoomCreateRequest request) {
        Long id = commandService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(id);
    }

    // 방 목록 조회
    @GetMapping
    public ResponseEntity<List<RoomInfoResponse>> findAll(@RequestParam(required = false) String keyword) {
        List<RoomInfoResponse> responses = queryService.findAll(keyword);
        return ResponseEntity.ok(responses);
    }

    // 방 단건 조회
    @GetMapping("/{id}")
    public ResponseEntity<RoomInfoResponse> findById(@PathVariable Long id) {
        RoomInfoResponse response = queryService.findById(id);
        return ResponseEntity.ok(response);
    }
}
