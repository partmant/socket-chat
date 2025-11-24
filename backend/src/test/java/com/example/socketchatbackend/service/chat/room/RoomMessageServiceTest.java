package com.example.socketchatbackend.service.chat.room;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import static com.example.socketchatbackend.constraint.chat.message.MessageConstraints.MAX_TALK_MESSAGE_LENGTH;

import com.example.socketchatbackend.exception.CustomException;
import com.example.socketchatbackend.exception.ErrorCode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import com.example.socketchatbackend.dto.chat.message.RoomMessageRequest;
import com.example.socketchatbackend.dto.chat.message.MessageType;
import com.example.socketchatbackend.dto.chat.message.RoomMessageResponse;
import com.example.socketchatbackend.service.chat.message.MessageBroadcaster;
import com.example.socketchatbackend.service.chat.message.MessageFactory;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class RoomMessageServiceTest {

    private static final Long VALID_ROOM_ID = 1L;
    private static final String VALID_NICKNAME = "user";
    private static final String VALID_CONTENT = "안녕하세요";
    private static final String INVALID_MESSAGE_LENGTH = "a".repeat(MAX_TALK_MESSAGE_LENGTH + 1);

    @Mock
    private RoomValidationService validationService;
    @Mock
    private MessageBroadcaster broadcaster;
    @Mock
    private MessageFactory messageFactory;

    @InjectMocks
    private RoomMessageService roomMessageService;

    @BeforeEach
    void setUp() {
        willDoNothing().given(validationService).validateRoomExists(VALID_ROOM_ID);
        willDoNothing().given(validationService).validateMemberExists(VALID_ROOM_ID, VALID_NICKNAME);
    }

    @Test
    @DisplayName("메시지가 빈 문자열 또는 공백이면 예외가 발생한다")
    void 메시지가_비어있으면_전송되지_않는다() {
        RoomMessageRequest emptyReq = RoomMessageRequest.builder()
                .roomId(VALID_ROOM_ID).type(MessageType.TALK).sender(VALID_NICKNAME).content("")
                .build();

        RoomMessageRequest whitespaceReq = RoomMessageRequest.builder()
                .roomId(VALID_ROOM_ID).type(MessageType.TALK).sender(VALID_NICKNAME).content("   ")
                .build();

        assertThatThrownBy(() -> roomMessageService.sendTalkMessage(emptyReq))
                .isInstanceOf(CustomException.class)
                .hasMessage(ErrorCode.MESSAGE_CONTENT_EMPTY.message());

        assertThatThrownBy(() -> roomMessageService.sendTalkMessage(whitespaceReq))
                .isInstanceOf(CustomException.class)
                .hasMessage(ErrorCode.MESSAGE_CONTENT_EMPTY.message());

        verify(broadcaster, never()).broadcast(anyLong(), any(RoomMessageResponse.class));
    }

    @Test
    @DisplayName("200자 이하의 TALK 메시지이면 정상적으로 메시지가 전송된다")
    void 메시지가_200자를_이하이면_성공한다() {
        RoomMessageRequest req = RoomMessageRequest.builder()
                .roomId(VALID_ROOM_ID)
                .type(MessageType.TALK)
                .sender(VALID_NICKNAME)
                .content(VALID_CONTENT)
                .build();

        given(messageFactory.createTalkMessage(any(RoomMessageRequest.class)))
                .willReturn(new RoomMessageResponse(VALID_ROOM_ID, MessageType.TALK, VALID_NICKNAME, VALID_CONTENT));

        roomMessageService.sendTalkMessage(req);

        verify(validationService).validateRoomExists(VALID_ROOM_ID);
        verify(validationService).validateMemberExists(VALID_ROOM_ID, VALID_NICKNAME);

        verify(messageFactory).createTalkMessage(argThat(
                validatedReq -> validatedReq.roomId().equals(VALID_ROOM_ID)
                        && validatedReq.type().equals(MessageType.TALK)
                        && validatedReq.sender().equals(VALID_NICKNAME)
        ));

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
                .isInstanceOf(CustomException.class)
                .hasMessage(ErrorCode.MESSAGE_LENGTH_EXCEEDED.message());

        verify(broadcaster, never()).broadcast(anyLong(), any(RoomMessageResponse.class));
        verify(messageFactory, never()).createTalkMessage(any(RoomMessageRequest.class));
    }
}
