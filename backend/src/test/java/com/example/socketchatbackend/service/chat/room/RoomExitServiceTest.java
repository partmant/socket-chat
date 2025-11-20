package com.example.socketchatbackend.service.chat.room;

import static org.assertj.core.api.Assertions.*;
import static com.example.socketchatbackend.exception.chat.ErrorMessages.*;
import static org.mockito.Mockito.*;

import com.example.socketchatbackend.dto.chat.room.RoomCreateRequest;
import com.example.socketchatbackend.dto.chat.room.RoomExitRequest;
import com.example.socketchatbackend.repository.chat.InMemoryRoomMemberRepository;
import com.example.socketchatbackend.repository.chat.InMemoryRoomRepository;
import com.example.socketchatbackend.service.chat.message.MessageService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class RoomExitServiceTest {

    private RoomExitService exitService;
    private RoomCommandService commandService;
    private InMemoryRoomRepository roomRepository;
    private InMemoryRoomMemberRepository memberRepository;
    private MessageService messageService;

    private static final String VALID_NICK = "user";
    private static final String VALID_TITLE = "room";
    private static final int VALID_CAPACITY = 10;
    private static final Long INVALID_ROOM_ID = 999L;

    @BeforeEach
    void setUp() {
        roomRepository = new InMemoryRoomRepository();
        memberRepository = new InMemoryRoomMemberRepository();
        messageService = mock(MessageService.class);

        exitService = new RoomExitService(roomRepository, memberRepository, messageService);
        commandService = new RoomCommandService(roomRepository);
    }

    @Test
    @DisplayName("정상 퇴장 시 멤버를 제거하고 퇴장 메시지를 전송한다.")
    void 퇴장시_멤버를_제거하고_메시지를_전송한다() {
        Long id = commandService.create(new RoomCreateRequest(VALID_TITLE, null, VALID_CAPACITY));
        memberRepository.add(id, VALID_NICK);

        RoomExitRequest req = new RoomExitRequest(VALID_NICK);

        assertThatCode(() -> exitService.exit(id, req))
                .doesNotThrowAnyException();

        assertThat(memberRepository.exists(id, VALID_NICK)).isFalse();
        verify(messageService, times(1)).broadcast(any());
    }

    @Test
    @DisplayName("존재하지 않는 방에서 퇴장 요청 시 예외가 발생한다")
    void 방이_존재하지_않으면_예외가_발생한다() {
        RoomExitRequest req = new RoomExitRequest(VALID_NICK);

        assertThatThrownBy(() -> exitService.exit(INVALID_ROOM_ID, req))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(ROOM_NOT_FOUND.getMessage());
    }

    @Test
    @DisplayName("방에 없는 닉네임으로 퇴장 요청 시 예외가 발생한다.")
    void 닉네임_없으면_예외가_발생한다() {
        Long id = commandService.create(new RoomCreateRequest(VALID_TITLE, null, VALID_CAPACITY));

        RoomExitRequest req = new RoomExitRequest(VALID_NICK);

        assertThatThrownBy(() -> exitService.exit(id, req))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(NICKNAME_NOT_FOUND.getMessage());
    }
}
