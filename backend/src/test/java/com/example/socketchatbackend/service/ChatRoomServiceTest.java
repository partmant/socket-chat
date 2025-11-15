package com.example.socketchatbackend.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import static com.example.socketchatbackend.exception.chat.ChatErrorMessages.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.example.socketchatbackend.dto.chat.ChatRoomRequest;
import com.example.socketchatbackend.repository.chat.InMemoryChatRoomRepository;
import com.example.socketchatbackend.service.chat.ChatRoomService;

class ChatRoomServiceTest {

    private static final String VALID_TITLE = "test-room";
    private static final String VALID_PASSWORD = "1234";
    private static final int VALID_MAX_USER_COUNT = 10;

    private ChatRoomService chatRoomService;

    @BeforeEach
    void setUp() {
        chatRoomService = new ChatRoomService(new InMemoryChatRoomRepository());
    }

    @Test
    @DisplayName("같은 제목의 방이 이미 존재하면 예외가 발생한다")
    void 같은_제목의_방이_존재하면_예외가_발생한다() {
        ChatRoomRequest req1 = new ChatRoomRequest(VALID_TITLE, VALID_PASSWORD, VALID_MAX_USER_COUNT);
        ChatRoomRequest req2 = new ChatRoomRequest(VALID_TITLE, VALID_PASSWORD, VALID_MAX_USER_COUNT);

        chatRoomService.create(req1);

        assertThatThrownBy(() -> chatRoomService.create(req2))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining(TITLE_DUPLICATION.getMessage());
    }

    @Test
    @DisplayName("유효한 입력이면 채팅방이 생성되고 ID가 반환된다.")
    void 유효한_입력이면_채팅방이_생성되고_ID가_반환된다() {
        ChatRoomRequest req = new ChatRoomRequest(VALID_TITLE, VALID_PASSWORD, VALID_MAX_USER_COUNT);

        Long id = chatRoomService.create(req);

        assertThat(id).isNotNull();
    }
}
