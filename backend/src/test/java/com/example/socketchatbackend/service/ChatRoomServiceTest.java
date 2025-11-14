package com.example.socketchatbackend.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

@DisplayName("채팅방 생성 기능")
class ChatRoomServiceTest {

    private static final String VALID_TITLE = "test-room";
    private static final String VALID_PASSWORD = "1234";
    private static final int VALID_MAX_USER_COUNT = 10;

    private static final int MAX_TITLE_LENGTH = 50;
    private static final String EMPTY_TITLE = "";

    private static final int MIN_PASSWORD_LENGTH = 4;
    private static final int MAX_PASSWORD_LENGTH = 10;

    private static final int MIN_ALLOWED_USER_COUNT = 1;
    private static final int MAX_ALLOWED_USER_COUNT = 10;

    private ChatRoomService chatRoomService;

    @BeforeEach
    void setUp() {
        chatRoomService = new ChatRoomService(new ChatRoomRepository());
    }

    @Test
    @DisplayName("제목이 null이면 예외가 발생한다.")
    void 제목이_null이면_예외가_발생한다() {
        ChatRoomRequest req = new ChatRoomRequest(null, VALID_PASSWORD, VALID_MAX_USER_COUNT);

        assertThatThrownBy(() -> chatRoomService.create(req))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("제목 값이 유효하지 않습니다.");
    }

    @Test
    @DisplayName("제목이 비어 있으면 예외가 발생한다.")
    void 제목이_비어있으면_예외가_발생한다() {
        ChatRoomRequest req = new ChatRoomRequest(EMPTY_TITLE, VALID_PASSWORD, VALID_MAX_USER_COUNT);

        assertThatThrownBy(() -> chatRoomService.create(req))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("제목 값이 유효하지 않습니다.");
    }

    @ParameterizedTest
    @ValueSource(ints = {MAX_TITLE_LENGTH + 1, MAX_TITLE_LENGTH + 10})
    @DisplayName("제목 길이가 허용 범위를 초과하면 예외가 발생한다.")
    void 제목_길이가_허용_범위를_초과하면_예외가_발생한다(int invalidLength) {
        String invalidTitle = "a".repeat(invalidLength);
        ChatRoomRequest req = new ChatRoomRequest(invalidTitle, VALID_PASSWORD, VALID_MAX_USER_COUNT);

        assertThatThrownBy(() -> chatRoomService.create(req))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("제목 길이가 유효하지 않습니다.");
    }

    @ParameterizedTest
    @ValueSource(ints = {MIN_PASSWORD_LENGTH - 1, MAX_PASSWORD_LENGTH + 1})
    @DisplayName("비밀번호 길이가 허용 범위를 벗어나면 예외가 발생한다.")
    void 비밀번호_길이가_허용_범위를_벗어나면_예외가_발생한다(int invalidLength) {
        String invalidPassword = "a".repeat(invalidLength);
        ChatRoomRequest req = new ChatRoomRequest(VALID_TITLE, invalidPassword, VALID_MAX_USER_COUNT);

        assertThatThrownBy(() -> chatRoomService.create(req))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("비밀번호 길이가 유효하지 않습니다.");
    }

    @ParameterizedTest
    @ValueSource(ints = {MIN_ALLOWED_USER_COUNT - 1, MAX_ALLOWED_USER_COUNT + 1})
    @DisplayName("최대 인원이 허용 범위를 벗어나면 예외가 발생한다.")
    void 최대_인원이_허용_범위를_벗어나면_예외가_발생한다(int invalidMaxUserCount) {
        ChatRoomRequest req = new ChatRoomRequest(VALID_TITLE, VALID_PASSWORD, invalidMaxUserCount);

        assertThatThrownBy(() -> chatRoomService.create(req))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("최대 인원 값이 유효하지 않습니다.");
    }

    @Test
    @DisplayName("유효한 입력이면 채팅방이 생성되고 ID가 반환된다.")
    void 유효한_입력이면_채팅방이_생성되고_ID가_반환된다() {
        ChatRoomRequest req = new ChatRoomRequest(VALID_TITLE, VALID_PASSWORD, VALID_MAX_USER_COUNT);

        Long id = chatRoomService.create(req);

        assertThat(id).isNotNull();
    }
}
