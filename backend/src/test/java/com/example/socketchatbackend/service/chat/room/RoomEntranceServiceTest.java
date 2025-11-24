package com.example.socketchatbackend.service.chat.room;

import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.*;

import com.example.socketchatbackend.exception.CustomException;
import com.example.socketchatbackend.exception.ErrorCode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.messaging.simp.SimpMessagingTemplate;

import com.example.socketchatbackend.service.chat.message.MessageService;
import com.example.socketchatbackend.dto.chat.room.RoomCreateRequest;
import com.example.socketchatbackend.dto.chat.room.RoomEnterRequest;
import com.example.socketchatbackend.repository.chat.InMemoryRoomMemberRepository;
import com.example.socketchatbackend.repository.chat.InMemoryRoomRepository;

class RoomEntranceServiceTest {

    private static final Long INVALID_ID = 9999L;
    private static final String VALID_NICKNAME = "user";
    private static final String VALID_TITLE = "test-room";
    private static final String VALID_PASSWORD = "1234";
    private static final String INVALID_PASSWORD = "wrong";
    private static final int VALID_CAPACITY = 10;

    private MessageService messageService;
    private RoomCommandService command;
    private RoomEntranceService entrance;
    private InMemoryRoomMemberRepository memberRepo;
    private InMemoryRoomRepository roomRepo;
    private SimpMessagingTemplate messagingTemplate;

    @BeforeEach
    void setUp() {
        memberRepo = new InMemoryRoomMemberRepository();
        roomRepo = new InMemoryRoomRepository();

        messageService = mock(MessageService.class);
        messagingTemplate = mock(SimpMessagingTemplate.class);

        command = new RoomCommandService(roomRepo, messagingTemplate);

        entrance = new RoomEntranceService(roomRepo, memberRepo, messageService, messagingTemplate);
    }

    @Test
    @DisplayName("존재하지 않는 방에 입장 시도 시 예외가 발생한다.")
    void 존재하지_않는_방에_입장시_예외_발생() {
        RoomEnterRequest req = new RoomEnterRequest(VALID_NICKNAME, null);

        assertThatThrownBy(() -> entrance.enter(INVALID_ID, req))
                .isInstanceOf(CustomException.class)
                .hasMessage(ErrorCode.ROOM_NOT_FOUND.message());
    }

    @Test
    @DisplayName("비밀번호가 설정되지 않은 방은 비밀번호 없이 입장 가능하다.")
    void 비밀번호_설정이_없으면_입장한다() {
        Long id = command.create(new RoomCreateRequest(VALID_NICKNAME, null, VALID_CAPACITY));
        RoomEnterRequest req = new RoomEnterRequest(VALID_NICKNAME, null);

        assertThatCode(() -> entrance.enter(id, req))
                .doesNotThrowAnyException();
    }

    @Test
    @DisplayName("비밀번호가 틀리면 예외가 발생한다.")
    void 비밀번호가_유효하지_않으면_예외가_발생한다() {
        Long id = command.create(new RoomCreateRequest(VALID_TITLE, VALID_PASSWORD, VALID_CAPACITY));
        RoomEnterRequest req = new RoomEnterRequest(VALID_NICKNAME, INVALID_PASSWORD);

        assertThatThrownBy(() -> entrance.enter(id, req))
                .isInstanceOf(CustomException.class)
                .hasMessage(ErrorCode.PASSWORD_INCORRECT.message());
    }

    @Test
    @DisplayName("비밀번호가 맞으면 정상적으로 입장할 수 있다.")
    void 유효한_비밀번호이면_입장한다() {
        Long id = command.create(new RoomCreateRequest(VALID_TITLE, VALID_PASSWORD, VALID_CAPACITY));
        RoomEnterRequest req = new RoomEnterRequest(VALID_NICKNAME, VALID_PASSWORD);

        assertThatCode(() -> entrance.enter(id, req))
                .doesNotThrowAnyException();
    }

    @Test
    @DisplayName("닉네임이 null이면 예외가 발생한다.")
    void 닉네임이_null이면_예외가_발생한다() {
        Long id = command.create(new RoomCreateRequest(VALID_TITLE, null, VALID_CAPACITY));
        RoomEnterRequest req = new RoomEnterRequest(null, null);

        assertThatThrownBy(() -> entrance.enter(id, req))
                .isInstanceOf(CustomException.class)
                .hasMessage(ErrorCode.NICKNAME_NULL.message());
    }

    @Test
    @DisplayName("닉네임이 공백이면 예외가 발생한다.")
    void 닉네임이_비어있으면_예외가_발생한다() {
        Long id = command.create(new RoomCreateRequest(VALID_TITLE, null, VALID_CAPACITY));
        RoomEnterRequest req = new RoomEnterRequest("   ", null);

        assertThatThrownBy(() -> entrance.enter(id, req))
                .isInstanceOf(CustomException.class)
                .hasMessage(ErrorCode.NICKNAME_BLANK.message());
    }

    @Test
    @DisplayName("닉네임 길이가 1~16자를 벗어나면 예외가 발생한다.")
    void 닉네임_길이가_유효하지_않으면_예외가_발생한다() {
        Long id = command.create(new RoomCreateRequest(VALID_TITLE, null, VALID_CAPACITY));
        RoomEnterRequest req = new RoomEnterRequest("a".repeat(17), null);

        assertThatThrownBy(() -> entrance.enter(id, req))
                .isInstanceOf(CustomException.class)
                .hasMessage(ErrorCode.NICKNAME_LENGTH_EXCEED.message());
    }

    @Test
    @DisplayName("같은 방에서 닉네임이 중복되면 예외가 발생한다.")
    void 닉네임이_중복이면_예외가_발생한다() {
        Long id = command.create(new RoomCreateRequest(VALID_TITLE, null, VALID_CAPACITY));

        entrance.enter(id, new RoomEnterRequest(VALID_NICKNAME, null));

        assertThatThrownBy(() -> entrance.enter(id, new RoomEnterRequest(VALID_NICKNAME, null)))
                .isInstanceOf(CustomException.class)
                .hasMessage(ErrorCode.NICKNAME_DUPLICATION.message());
    }

    @Test
    @DisplayName("방 최대 인원 수를 초과하여 입장할 수 없다.")
    void 정원이_초과된_방에_입장하면_예외가_발생한다() {
        Long id = command.create(new RoomCreateRequest(VALID_TITLE, null, 1));

        entrance.enter(id, new RoomEnterRequest(VALID_NICKNAME + 1, null));

        assertThatThrownBy(() -> entrance.enter(id, new RoomEnterRequest(VALID_NICKNAME + 2, null)))
                .isInstanceOf(CustomException.class)
                .hasMessage(ErrorCode.ROOM_FULL.message());
    }

    @Test
    @DisplayName("입장 성공 시 입장 메시지를 브로드캐스트한다.")
    void 입장_성공시_메시지를_호출한다() {
        Long id = command.create(new RoomCreateRequest(VALID_TITLE, VALID_PASSWORD, VALID_CAPACITY));
        RoomEnterRequest req = new RoomEnterRequest(VALID_NICKNAME, VALID_PASSWORD);

        assertThatCode(() -> entrance.enter(id, req))
                .doesNotThrowAnyException();

        verify(messageService, times(1)).broadcast(any());
    }
}
