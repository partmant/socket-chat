package com.example.socketchatbackend.service.chat;

import static org.assertj.core.api.Assertions.*;

import static com.example.socketchatbackend.exception.chat.ChatErrorMessages.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.example.socketchatbackend.dto.chat.ChatRoomRequest;
import com.example.socketchatbackend.repository.chat.InMemoryChatRoomRepository;

class RoomEntranceServiceTest {

    private static final String VALID_TITLE = "test-room";
    private static final String VALID_PASSWORD = "1234";
    private static final int VALID_MAX_USER_COUNT = 10;

    private RoomCommandService command;
    private RoomEntranceService entrance;

    @BeforeEach
    void setUp() {
        InMemoryChatRoomRepository repo = new InMemoryChatRoomRepository();
        command = new RoomCommandService(repo);
        entrance = new RoomEntranceService(repo);
    }

    @Test
    @DisplayName("비밀번호가 설정되지 않은 방은 비밀번호 없이 입장 가능하다.")
    void 비밀번호_설정이_없으면_입장한다() {
        Long id = command.create(new ChatRoomRequest(VALID_TITLE, null, VALID_MAX_USER_COUNT));

        assertThatCode(() -> entrance.validateEnter(id, null))
                .doesNotThrowAnyException();
    }

    @Test
    @DisplayName("비밀번호가 틀리면 예외가 발생한다.")
    void 비밀번호가_유효하지_않으면_예외가_발생한다() {
        Long id = command.create(new ChatRoomRequest(VALID_TITLE, VALID_PASSWORD, VALID_MAX_USER_COUNT));

        assertThatThrownBy(() -> entrance.validateEnter(id, "wrong"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(INVALID_PASSWORD.getMessage());
    }

    @Test
    @DisplayName("비밀번호가 맞으면 정상적으로 입장할 수 있다.")
    void 유효한_비밀번호이면_입장한다() {
        Long id = command.create(new ChatRoomRequest(VALID_TITLE, VALID_PASSWORD, VALID_MAX_USER_COUNT));

        assertThatCode(() -> entrance.validateEnter(id, VALID_PASSWORD))
                .doesNotThrowAnyException();
    }
}
