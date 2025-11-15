package com.example.socketchatbackend.domain.chat;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import static com.example.socketchatbackend.constraint.chat.RoomConstraints.*;
import static com.example.socketchatbackend.exception.chat.ErrorMessages.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class RoomTest {

    private static final Long VALID_ID = 1L;
    private static final String VALID_TITLE = "chat-room";
    private static final String VALID_PASSWORD = "1234";
    private static final Integer VALID_MAX_USER_COUNT = 10;

    @Test
    @DisplayName("제목이 null이면 예외가 발생한다.")
    void 제목이_null이면_예외가_발생한다() {
        assertThatThrownBy(() ->
                Room.of(null, VALID_PASSWORD, VALID_MAX_USER_COUNT)
        )
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining(TITLE_NULL.getMessage());
    }

    @Test
    @DisplayName("제목이 비어 있으면 예외가 발생한다.")
    void 제목이_비어있으면_예외가_발생한다() {
        assertThatThrownBy(() ->
                Room.of("", VALID_PASSWORD, VALID_MAX_USER_COUNT)
        )
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining(TITLE_BLANK.getMessage());
    }

    @Test
    @DisplayName("제목 길이가 허용 범위를 초과하면 예외가 발생한다.")
    void 제목_길이가_허용범위를_초과하면_예외가_발생한다() {
        String tooLongTitle = "a".repeat(MAX_TITLE_LENGTH + 1);

        assertThatThrownBy(() ->
                Room.of(tooLongTitle, VALID_PASSWORD, VALID_MAX_USER_COUNT)
        )
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining(TITLE_LENGTH_EXCEEDED.getMessage());
    }

    @Test
    @DisplayName("비밀번호 길이가 허용 범위를 벗어나면 예외가 발생한다.")
    void 비밀번호_길이가_허용범위를_벗어나면_예외가_발생한다() {
        String tooShort = "a".repeat(MIN_PASSWORD_LENGTH - 1);
        String tooLong = "a".repeat(MAX_PASSWORD_LENGTH + 1);

        assertThatThrownBy(() ->
                Room.of(VALID_TITLE, tooShort, VALID_MAX_USER_COUNT)
        )
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining(PASSWORD_LENGTH_EXCEEDED.getMessage());

        assertThatThrownBy(() ->
                Room.of(VALID_TITLE, tooLong, VALID_MAX_USER_COUNT)
        )
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining(PASSWORD_LENGTH_EXCEEDED.getMessage());
    }

    @Test
    @DisplayName("최대 인원이 허용 범위를 벗어나면 예외가 발생한다.")
    void 최대_인원이_허용범위를_벗어나면_예외가_발생한다() {
        assertThatThrownBy(() ->
                Room.of(VALID_TITLE, VALID_PASSWORD, MIN_ALLOWED_USER_COUNT - 1)
        )
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining(MAX_USER_COUNT_EXCEEDED.getMessage());

        assertThatThrownBy(() ->
                Room.of(VALID_TITLE, VALID_PASSWORD, MAX_ALLOWED_USER_COUNT + 1)
        )
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining(MAX_USER_COUNT_EXCEEDED.getMessage());
    }
}
