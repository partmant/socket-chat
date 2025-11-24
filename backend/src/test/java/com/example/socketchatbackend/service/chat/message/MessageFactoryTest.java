package com.example.socketchatbackend.service.chat.message;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;

import com.example.socketchatbackend.exception.CustomException;
import com.example.socketchatbackend.exception.ErrorCode;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.example.socketchatbackend.dto.chat.message.RoomMessageRequest;
import com.example.socketchatbackend.dto.chat.message.RoomMessageResponse;
import com.example.socketchatbackend.dto.chat.message.MessageType;
import com.example.socketchatbackend.repository.chat.RoomMemberRepository;
import com.example.socketchatbackend.util.ChatConstants;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.util.HtmlUtils;

@ExtendWith(MockitoExtension.class)
class MessageFactoryTest {

    @Mock
    private RoomMemberRepository roomMemberRepository;

    @InjectMocks
    private MessageFactory messageFactory;

    private static final Long VALID_ROOM_ID = 1L;
    private static final String VALID_SENDER = "user";
    private static final String NON_MEMBER = "non-member";

    @Test
    @DisplayName("ENTER 메시지를 올바르게 생성한다")
    void ENTER_메시지가_정상적으로_생성된다() {
        given(roomMemberRepository.exists(eq(VALID_ROOM_ID), eq(VALID_SENDER)))
                .willReturn(true);

        RoomMessageRequest req = new RoomMessageRequest(VALID_ROOM_ID, MessageType.ENTER, VALID_SENDER, null);

        RoomMessageResponse res = messageFactory.createEnterMessage(req);

        assertThat(res.roomId()).isEqualTo(VALID_ROOM_ID);
        assertThat(res.type()).isEqualTo(MessageType.ENTER);
        assertThat(res.sender()).isEqualTo(ChatConstants.SYSTEM_SENDER);
        assertThat(res.content()).isEqualTo(String.format(ChatConstants.ENTER_MESSAGE_FORMAT, VALID_SENDER));
    }

    @Test
    @DisplayName("EXIT 메시지를 올바르게 생성한다")
    void EXIT_메시지가_정상적으로_생성된다() {
        given(roomMemberRepository.exists(eq(VALID_ROOM_ID), eq(VALID_SENDER)))
                .willReturn(true);

        RoomMessageRequest req = new RoomMessageRequest(VALID_ROOM_ID, MessageType.EXIT, VALID_SENDER, null);

        RoomMessageResponse res = messageFactory.createExitMessage(req);

        assertThat(res.roomId()).isEqualTo(VALID_ROOM_ID);
        assertThat(res.type()).isEqualTo(MessageType.EXIT);
        assertThat(res.sender()).isEqualTo(ChatConstants.SYSTEM_SENDER);
        assertThat(res.content()).isEqualTo(String.format(ChatConstants.QUIT_MESSAGE_FORMAT, VALID_SENDER));
    }

    @Test
    @DisplayName("TALK 메시지를 올바르게 생성한다")
    void TALK_메시지가_정상적으로_생성된다() {
        given(roomMemberRepository.exists(eq(VALID_ROOM_ID), eq(VALID_SENDER)))
                .willReturn(true);

        String rawContent = "  <script>alert('xss')</script> Hi  ";
        String expectedContent = HtmlUtils.htmlEscape(rawContent).trim();

        RoomMessageRequest req = new RoomMessageRequest(VALID_ROOM_ID, MessageType.TALK, VALID_SENDER, rawContent);

        RoomMessageResponse res = messageFactory.createTalkMessage(req);

        assertThat(res.roomId()).isEqualTo(VALID_ROOM_ID);
        assertThat(res.type()).isEqualTo(MessageType.TALK);
        assertThat(res.sender()).isEqualTo(VALID_SENDER);
        assertThat(res.content()).isEqualTo(expectedContent);
    }

    @Test
    @DisplayName("TALK 메시지 생성 시 존재하지 않는 닉네임이면 예외가 발생한다")
    void TALK_메시지_생성_시_존재하지_않는_닉네임이면_예외가_발생한다() {
        given(roomMemberRepository.exists(eq(VALID_ROOM_ID), eq(NON_MEMBER)))
                .willReturn(false);

        RoomMessageRequest talkReq = new RoomMessageRequest(VALID_ROOM_ID, MessageType.TALK, NON_MEMBER, "Hi");

        assertThatThrownBy(() -> messageFactory.createTalkMessage(talkReq))
                .isInstanceOf(CustomException.class)
                .hasMessage(ErrorCode.NICKNAME_NOT_FOUND.message());
    }

    @Test
    @DisplayName("ENTER 메시지 생성 시 존재하지 않는 닉네임이면 예외가 발생한다")
    void ENTER_메시지_생성_시_존재하지_않는_닉네임이면_예외가_발생한다() {
        given(roomMemberRepository.exists(eq(VALID_ROOM_ID), eq(NON_MEMBER)))
                .willReturn(false);

        RoomMessageRequest enterReq = new RoomMessageRequest(VALID_ROOM_ID, MessageType.ENTER, NON_MEMBER, null);

        assertThatThrownBy(() -> messageFactory.createEnterMessage(enterReq))
                .isInstanceOf(CustomException.class)
                .hasMessage(ErrorCode.NICKNAME_NOT_FOUND.message());

    }
}
