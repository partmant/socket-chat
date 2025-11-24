package com.example.socketchatbackend.service.chat.room;

import static org.assertj.core.api.Assertions.*;

import static org.mockito.Mockito.mock;

import com.example.socketchatbackend.exception.CustomException;
import com.example.socketchatbackend.exception.ErrorCode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.example.socketchatbackend.dto.chat.room.RoomCreateRequest;
import com.example.socketchatbackend.repository.chat.InMemoryRoomRepository;
import org.springframework.messaging.simp.SimpMessagingTemplate;

class RoomCommandServiceTest {

    private static final String VALID_TITLE = "test-room";
    private static final String VALID_PASSWORD = "1234";
    private static final int VALID_MAX_USER_COUNT = 10;

    private RoomCommandService commandService;
    private InMemoryRoomRepository roomRepository;
    private SimpMessagingTemplate messagingTemplate;


    @BeforeEach
    void setUp() {
        roomRepository = new InMemoryRoomRepository();

        messagingTemplate = mock(SimpMessagingTemplate.class);

        commandService = new RoomCommandService(roomRepository, messagingTemplate);
    }

    @Test
    @DisplayName("같은 제목의 방이 이미 존재하면 방을 생성할 수 없다.")
    void 같은_제목의_방이_존재하면_예외가_발생한다() {
        RoomCreateRequest req1 = new RoomCreateRequest(VALID_TITLE, VALID_PASSWORD, VALID_MAX_USER_COUNT);
        RoomCreateRequest req2 = new RoomCreateRequest(VALID_TITLE, VALID_PASSWORD, VALID_MAX_USER_COUNT);

        commandService.create(req1);

        assertThatThrownBy(() -> commandService.create(req2))
                .isInstanceOf(CustomException.class)
                .hasMessage(ErrorCode.TITLE_DUPLICATION.message());
    }

    @Test
    @DisplayName("유효한 입력이면 채팅방이 생성되고 ID가 반환된다.")
    void 유효한_입력이면_채팅방이_생성되고_ID가_반환된다() {
        RoomCreateRequest req = new RoomCreateRequest(VALID_TITLE, VALID_PASSWORD, VALID_MAX_USER_COUNT);

        Long id = commandService.create(req);

        assertThat(id).isNotNull();
    }
}
