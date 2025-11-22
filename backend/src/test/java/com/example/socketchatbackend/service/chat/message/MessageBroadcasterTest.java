package com.example.socketchatbackend.service.chat.message;

import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.messaging.simp.SimpMessagingTemplate;

import com.example.socketchatbackend.dto.chat.message.MessageResponse;
import com.example.socketchatbackend.dto.chat.message.MessageType;

class MessageBroadcasterTest {

    private static final String BASE_URL = "/topic/room";
    private static final String SENDER = "user";
    private static final Long VALID_ROOM_ID = 1L;

    private SimpMessagingTemplate template;
    private MessageBroadcaster broadcaster;

    @BeforeEach
    void setUp() {
        template = mock(SimpMessagingTemplate.class);
        broadcaster = new MessageBroadcaster(template);
    }

    @Test
    @DisplayName("MessageBroadcaster는 SimpMessagingTemplate을 올바른 목적지로 호출한다")
    void broadcast_호출이_성공적으로_이루어진다() {
        MessageResponse res = new MessageResponse(VALID_ROOM_ID, MessageType.TALK, SENDER, "안녕");

        broadcaster.broadcast(VALID_ROOM_ID, res);

        verify(template).convertAndSend(BASE_URL + "/" + VALID_ROOM_ID, res);
    }
}
