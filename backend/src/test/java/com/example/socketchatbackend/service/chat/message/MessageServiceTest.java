package com.example.socketchatbackend.service.chat.message;

import static org.mockito.BDDMockito.*;
import static org.mockito.Mockito.mock;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.example.socketchatbackend.dto.chat.message.MessageRequest;
import com.example.socketchatbackend.dto.chat.message.MessageResponse;
import com.example.socketchatbackend.dto.chat.message.MessageType;

class MessageServiceTest {

    private static final Long VALID_ROOM_ID = 1L;
    private static final String SENDER = "user";
    private static final String SYSTEM_SENDER = "system";

    private MessageFactory factory;
    private MessageBroadcaster broadcaster;
    private MessageService service;
    @BeforeEach
    void setUp() {
        factory = mock(MessageFactory.class);
        broadcaster = mock(MessageBroadcaster.class);
        service = new MessageService(factory, broadcaster);

    }

    @Test
    @DisplayName("ENTER 메시지를 생성하여 해당 topic으로 전송한다")
    void ENTER_메시지_브로드캐스트() {
        MessageRequest req = new MessageRequest(VALID_ROOM_ID, MessageType.ENTER, SENDER, null);
        MessageResponse res = new MessageResponse(VALID_ROOM_ID, MessageType.ENTER, SYSTEM_SENDER, "입장");

        given(factory.createEnterMessage(req)).willReturn(res);

        service.broadcast(req);

        verify(broadcaster).broadcast(VALID_ROOM_ID, res);
    }

    @Test
    @DisplayName("EXIT 메시지를 생성하여 해당 topic으로 전송한다")
    void EXIT_메시지_브로드캐스트() {
        MessageRequest req = new MessageRequest(VALID_ROOM_ID, MessageType.EXIT, SENDER, null);
        MessageResponse res = new MessageResponse(VALID_ROOM_ID, MessageType.EXIT, SYSTEM_SENDER, "퇴장");

        given(factory.createExitMessage(req)).willReturn(res);

        service.broadcast(req);

        verify(broadcaster).broadcast(VALID_ROOM_ID, res);
    }

    @Test
    @DisplayName("TALK 메시지를 생성하여 해당 topic으로 전송한다")
    void TALK_메시지_브로드캐스트() {
        MessageRequest req = new MessageRequest(VALID_ROOM_ID, MessageType.TALK, SENDER, "hello");
        MessageResponse res = new MessageResponse(VALID_ROOM_ID, MessageType.TALK, SENDER, "hello");

        given(factory.createTalkMessage(req)).willReturn(res);

        service.broadcast(req);

        verify(broadcaster).broadcast(VALID_ROOM_ID, res);
    }
}
