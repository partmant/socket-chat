package com.example.socketchatbackend.domain.chat;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import static com.example.socketchatbackend.constraint.chat.room.RoomConstraints.*;
import static com.example.socketchatbackend.exception.chat.ErrorMessages.*;

import java.util.stream.Stream;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import com.example.socketchatbackend.domain.chat.vo.RoomCapacity;
import com.example.socketchatbackend.domain.chat.vo.RoomPassword;
import com.example.socketchatbackend.domain.chat.vo.RoomTitle;

class RoomTest {

    private static final String VALID_TITLE = "chat-room";
    private static final String VALID_PASSWORD = "1234";
    private static final int VALID_CAPACITY = 10;

    @Test
    @DisplayName("제목이 null이면 예외가 발생한다.")
    void 제목이_null이면_예외가_발생한다() {
        assertThatThrownBy(() ->
                Room.of(new RoomTitle(null),
                        RoomPassword.of(VALID_PASSWORD),
                        new RoomCapacity(VALID_CAPACITY))
        )
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining(TITLE_NULL.getMessage());
    }

    @Test
    @DisplayName("제목이 비어 있으면 예외가 발생한다.")
    void 제목이_비어있으면_예외가_발생한다() {
        assertThatThrownBy(() ->
                Room.of(new RoomTitle("   "),
                        RoomPassword.of(VALID_PASSWORD),
                        new RoomCapacity(VALID_CAPACITY))
        )
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining(TITLE_BLANK.getMessage());
    }

    @Test
    @DisplayName("제목 길이가 허용 범위를 초과하면 예외가 발생한다.")
    void 제목_길이가_허용범위를_초과하면_예외가_발생한다() {
        String tooLong = "a".repeat(MAX_TITLE_LENGTH + 1);

        assertThatThrownBy(() ->
                Room.of(new RoomTitle(tooLong),
                        RoomPassword.of(VALID_PASSWORD),
                        new RoomCapacity(VALID_CAPACITY))
        )
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining(TITLE_LENGTH_EXCEEDED.getMessage());
    }

    @ParameterizedTest
    @MethodSource("invalidPasswordLengthProvider")
    @DisplayName("비밀번호 길이가 허용 범위를 벗어나면 예외가 발생한다.")
    void 비밀번호_길이가_허용범위를_벗어나면_예외가_발생한다(String invalidPassword) {
        assertThatThrownBy(() ->
                Room.of(new RoomTitle(VALID_TITLE),
                        RoomPassword.of(invalidPassword),
                        new RoomCapacity(VALID_CAPACITY))
        )
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining(PASSWORD_LENGTH_EXCEEDED.getMessage());
    }

    @ParameterizedTest
    @ValueSource(ints = {MIN_ALLOWED_CAPACITY - 1, MAX_ALLOWED_CAPACITY + 1})
    @DisplayName("최대 인원이 허용 범위를 벗어나면 예외가 발생한다.")
    void 최대_인원이_허용범위를_벗어나면_예외가_발생한다(int invalidCapacity) {
        assertThatThrownBy(() ->
                Room.of(new RoomTitle(VALID_TITLE),
                        RoomPassword.of(VALID_PASSWORD),
                        new RoomCapacity(invalidCapacity))
        )
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining(CAPACITY_EXCEEDED.getMessage());
    }

    private static Stream<String> invalidPasswordLengthProvider() {
        String tooShort = "a".repeat(MIN_PASSWORD_LENGTH - 1);
        String tooLong =   "a".repeat(MAX_PASSWORD_LENGTH + 1);
        return Stream.of(tooShort, tooLong);
    }
}
