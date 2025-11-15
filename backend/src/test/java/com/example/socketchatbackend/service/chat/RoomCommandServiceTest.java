package com.example.socketchatbackend.service.chat;

import static org.assertj.core.api.Assertions.*;

import static com.example.socketchatbackend.exception.chat.ChatErrorMessages.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.example.socketchatbackend.dto.chat.ChatRoomRequest;
import com.example.socketchatbackend.repository.chat.InMemoryChatRoomRepository;

class RoomCommandServiceTest {

    private static final String VALID_TITLE = "test-room";
    private static final String VALID_PASSWORD = "1234";
    private static final int VALID_MAX_USER_COUNT = 10;

    private RoomCommandService service;

    @BeforeEach
    void setUp() {
        service = new RoomCommandService(new InMemoryChatRoomRepository());
    }

    @Test
    @DisplayName("같은 제목의 방이 이미 존재하면 방을 생성할 수 없다.")
    void 같은_제목의_방이_존재하면_예외가_발생한다() {
        ChatRoomRequest req1 = new ChatRoomRequest(VALID_TITLE, VALID_PASSWORD, VALID_MAX_USER_COUNT);
        ChatRoomRequest req2 = new ChatRoomRequest(VALID_TITLE, VALID_PASSWORD, VALID_MAX_USER_COUNT);

        service.create(req1);

        assertThatThrownBy(() -> service.create(req2))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(TITLE_DUPLICATION.getMessage());
    }

    @Test
    @DisplayName("유효한 입력이면 채팅방이 생성되고 ID가 반환된다.")
    void 유효한_입력이면_채팅방이_생성되고_ID가_반환된다() {
        ChatRoomRequest req = new ChatRoomRequest(VALID_TITLE, VALID_PASSWORD, VALID_MAX_USER_COUNT);

        Long id = service.create(req);

        assertThat(id).isNotNull();
    }
}
