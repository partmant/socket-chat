package com.example.socketchatbackend.service.chat.room;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;
import static org.mockito.Mockito.mock;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.example.socketchatbackend.domain.chat.Room;
import com.example.socketchatbackend.exception.chat.ErrorMessages;
import com.example.socketchatbackend.repository.chat.RoomMemberRepository;
import com.example.socketchatbackend.repository.chat.RoomRepository;

class RoomValidationServiceTest {

    private static final Long VALID_ROOM_ID = 1L;
    private static final String VALID_NICKNAME = "user";
    private static final Room MOCKED_ROOM = mock(Room.class);

    private RoomRepository roomRepository;
    private RoomMemberRepository memberRepository;
    private RoomValidationService validationService;

    @BeforeEach
    void setUp() {
        roomRepository = mock(RoomRepository.class);
        memberRepository = mock(RoomMemberRepository.class);

        validationService = new RoomValidationService(roomRepository, memberRepository);
    }

    @Test
    @DisplayName("존재하는 방 ID로 검증 시 성공한다")
    void 방이_존재하면_검증이_성공한다() {
        given(roomRepository.findById(VALID_ROOM_ID)).willReturn(Optional.of(MOCKED_ROOM));

        assertThatCode(() -> validationService.validateRoomExists(VALID_ROOM_ID))
                .doesNotThrowAnyException();
    }

    @Test
    @DisplayName("존재하지 않는 방 ID로 검증 시 예외를 발생시킨다")
    void 방이_존재하지_않으면_예외가_발생한다() {
        given(roomRepository.findById(VALID_ROOM_ID)).willReturn(Optional.empty());

        assertThatThrownBy(() -> validationService.validateRoomExists(VALID_ROOM_ID))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(ErrorMessages.ROOM_NOT_FOUND.getMessage());
    }

    @Test
    @DisplayName("방 멤버가 존재하는 경우 검증 시 성공한다")
    void 닉네임_멤버가_존재하면_성공한다() {
        given(memberRepository.exists(VALID_ROOM_ID, VALID_NICKNAME)).willReturn(true);

        assertThatCode(() -> validationService.validateMemberExists(VALID_ROOM_ID, VALID_NICKNAME))
                .doesNotThrowAnyException();
    }

    @Test
    @DisplayName("방 멤버가 존재하지 않는 경우, 예외를 발생시킨다")
    void 닉네임_멤버가_없으면_예외가_발생한다() {
        given(memberRepository.exists(VALID_ROOM_ID, VALID_NICKNAME)).willReturn(false);

        assertThatThrownBy(() -> validationService.validateMemberExists(VALID_ROOM_ID, VALID_NICKNAME))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(ErrorMessages.NICKNAME_NOT_FOUND.getMessage());
    }
}
