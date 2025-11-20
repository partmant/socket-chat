package com.example.socketchatbackend.service.chat.message;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.example.socketchatbackend.dto.chat.message.MessageRequest;
import com.example.socketchatbackend.dto.chat.message.MessageResponse;
import com.example.socketchatbackend.dto.chat.message.MessageType;
import com.example.socketchatbackend.util.ChatConstants;

class MessageFactoryTest {

    private final MessageFactory factory = new MessageFactory();

    private static final Long VALID_ROOM_ID = 1L;
    private static final String SENDER = "user";

    @Test
    @DisplayName("ENTER 메시지를 올바르게 생성한다")
    void ENTER_메시지가_정상적으로_생성된다() {
        MessageRequest req = new MessageRequest(VALID_ROOM_ID, MessageType.ENTER, SENDER, null);

        MessageResponse res = factory.createEnterMessage(req);

        assertThat(res.roomId()).isEqualTo(VALID_ROOM_ID);
        assertThat(res.type()).isEqualTo(MessageType.ENTER);
        assertThat(res.sender()).isEqualTo(ChatConstants.SYSTEM_SENDER);
        assertThat(res.content()).isEqualTo(String.format(ChatConstants.ENTER_MESSAGE_FORMAT, SENDER));
    }

    @Test
    @DisplayName("EXIT 메시지를 올바르게 생성한다")
    void EXIT_메시지가_정상적으로_생성된다() {
        MessageRequest req = new MessageRequest(VALID_ROOM_ID, MessageType.EXIT, SENDER, null);

        MessageResponse res = factory.createExitMessage(req);

        assertThat(res.roomId()).isEqualTo(VALID_ROOM_ID);
        assertThat(res.type()).isEqualTo(MessageType.EXIT);
        assertThat(res.sender()).isEqualTo(ChatConstants.SYSTEM_SENDER);
        assertThat(res.content()).isEqualTo(String.format(ChatConstants.QUIT_MESSAGE_FORMAT, SENDER));
    }

    @Test
    @DisplayName("TALK 메시지를 올바르게 생성한다")
    void TALK_메시지가_정상적으로_생성된다() {
        MessageRequest req = new MessageRequest(VALID_ROOM_ID, MessageType.TALK, SENDER, "안녕");

        MessageResponse res = factory.createTalkMessage(req);

        assertThat(res.roomId()).isEqualTo(VALID_ROOM_ID);
        assertThat(res.type()).isEqualTo(MessageType.TALK);
        assertThat(res.sender()).isEqualTo(SENDER);
        assertThat(res.content()).isEqualTo("안녕");
    }
}
