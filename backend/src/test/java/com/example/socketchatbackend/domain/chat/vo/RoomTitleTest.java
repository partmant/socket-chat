package com.example.socketchatbackend.domain.chat.vo;

import static org.assertj.core.api.Assertions.*;

import static com.example.socketchatbackend.constraint.chat.room.RoomConstraints.*;
import static com.example.socketchatbackend.exception.chat.ErrorMessages.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class RoomTitleTest {

    static final String VALID_TITLE = "valid-title";

    @Test
    @DisplayName("정상적인 제목이면 RoomTitle이 생성된다.")
    void 정상_제목이면_생성된다() {
        RoomTitle title = new RoomTitle(VALID_TITLE);

        assertThat(title.value()).isEqualTo(VALID_TITLE);
    }

    @Test
    @DisplayName("제목이 null이면 예외가 발생한다.")
    void 제목이_null이면_예외가_발생한다() {
        assertThatThrownBy(() -> new RoomTitle(null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(TITLE_NULL.getMessage());
    }

    @ParameterizedTest
    @ValueSource(strings = {"", " ", "   "})
    @DisplayName("제목이 공백 문자열이면 예외가 발생한다.")
    void 제목이_공백이면_예외가_발생한다(String blank) {
        assertThatThrownBy(() -> new RoomTitle(blank))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(TITLE_BLANK.getMessage());
    }

    @Test
    @DisplayName("제목 길이가 최대 길이를 초과하면 예외가 발생한다.")
    void 제목_길이_초과하면_예외가_발생한다() {
        String over = "a".repeat(MAX_TITLE_LENGTH + 1);

        assertThatThrownBy(() -> new RoomTitle(over))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(TITLE_LENGTH_EXCEEDED.getMessage());
    }
}
