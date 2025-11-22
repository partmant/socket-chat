package com.example.socketchatbackend.service.chat.room;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;
import static org.mockito.Mockito.mock;

import static com.example.socketchatbackend.constraint.chat.message.MessageConstraints.MAX_TALK_MESSAGE_LENGTH;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.example.socketchatbackend.dto.chat.message.RoomMessageRequest;
import com.example.socketchatbackend.dto.chat.message.MessageType;
import com.example.socketchatbackend.dto.chat.message.RoomMessageResponse;
import com.example.socketchatbackend.exception.chat.ErrorMessages;
import com.example.socketchatbackend.service.chat.message.MessageBroadcaster;

class RoomMessageServiceTest {

    private static final Long VALID_ROOM_ID = 1L;
    private static final String VALID_NICKNAME = "user";
    private static final String INVALID_MESSAGE_LENGTH = "a".repeat(MAX_TALK_MESSAGE_LENGTH + 1);

    private RoomValidationService validationService;
    private MessageBroadcaster broadcaster;
    private RoomMessageService roomMessageService;

    @BeforeEach
    void setUp() {
        validationService = mock(RoomValidationService.class);
        broadcaster = mock(MessageBroadcaster.class);

        roomMessageService = new RoomMessageService(validationService, broadcaster);

        willDoNothing().given(validationService).validateRoomExists(VALID_ROOM_ID);
        willDoNothing().given(validationService).validateMemberExists(VALID_ROOM_ID, VALID_NICKNAME);
    }

    @Test
    @DisplayName("메시지가 비어있으면 전송되지 않는다")
    void 메시지가_비어있으면_전송되지_않는다() {
        RoomMessageRequest req = RoomMessageRequest.builder()
                .roomId(VALID_ROOM_ID)
                .type(MessageType.TALK)
                .sender(VALID_NICKNAME)
                .content("")
                .build();

        roomMessageService.sendTalkMessage(req);

        verify(broadcaster, never()).broadcast(anyLong(), any(RoomMessageResponse.class));
    }

    @Test
    @DisplayName("200자 이하의 TALK 메시지이면 정상적으로 메시지가 전송된다")
    void 메시지가_200자를_이하이면_성공한다() {
        RoomMessageRequest req = RoomMessageRequest.builder()
                .roomId(VALID_ROOM_ID)
                .type(MessageType.TALK)
                .sender(VALID_NICKNAME)
                .content("안녕")
                .build();

        roomMessageService.sendTalkMessage(req);

        verify(broadcaster).broadcast(eq(VALID_ROOM_ID), any(RoomMessageResponse.class));
    }

    @Test
    @DisplayName("메시지가 200자를 초과하면 예외가 발생한다")
    void 메시지가_200자를_초과하면_예외가_발생한다() {
        RoomMessageRequest req = RoomMessageRequest.builder()
                .roomId(VALID_ROOM_ID)
                .type(MessageType.TALK)
                .sender(VALID_NICKNAME)
                .content(INVALID_MESSAGE_LENGTH)
                .build();

        assertThatThrownBy(() -> roomMessageService.sendTalkMessage(req))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(ErrorMessages.MESSAGE_LENGTH_EXCEEDED.getMessage());

        verify(broadcaster, never()).broadcast(anyLong(), any(RoomMessageResponse.class));
    }
}
