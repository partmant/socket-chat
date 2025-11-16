package com.example.socketchatbackend.controller.chat;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.socketchatbackend.dto.chat.RoomEnterRequest;
import com.example.socketchatbackend.dto.chat.RoomRequest;
import com.example.socketchatbackend.dto.chat.RoomResponse;
import com.example.socketchatbackend.service.chat.RoomCommandService;
import com.example.socketchatbackend.service.chat.RoomQueryService;
import com.example.socketchatbackend.service.chat.RoomEntranceService;

@RestController
@RequestMapping("/api/rooms")
public class RoomController {

    private final RoomCommandService commandService;
    private final RoomQueryService queryService;
    private final RoomEntranceService entranceService;

    public RoomController(RoomCommandService commandService,
                          RoomQueryService queryService,
                          RoomEntranceService entranceService) {
        this.commandService = commandService;
        this.queryService = queryService;
        this.entranceService = entranceService;
    }

    // 방 생성
    @PostMapping
    public ResponseEntity<Long> create(@RequestBody RoomRequest request) {
        Long id = commandService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(id);
    }

    // 방 목록 조회
    @GetMapping
    public ResponseEntity<List<RoomResponse>> findAll(@RequestParam(required = false) String keyword) {
        List<RoomResponse> responses = queryService.findAll(keyword);
        return ResponseEntity.ok(responses);
    }

    // 방 단건 조회
    @GetMapping("/{id}")
    public ResponseEntity<RoomResponse> findById(@PathVariable Long id) {
        RoomResponse response = queryService.findById(id);
        return ResponseEntity.ok(response);
    }

    // 방 입장
    @PostMapping("/{id}/enter")
    public ResponseEntity<Void> enter(@PathVariable Long id, @RequestBody RoomEnterRequest request) {
        entranceService.validateEnter(id, request.password());
        return ResponseEntity.ok().build();
    }
}
