package com.example.socketchatbackend.domain.chat.vo;

import static org.assertj.core.api.Assertions.*;

import static com.example.socketchatbackend.exception.chat.ErrorMessages.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class RoomPasswordTest {

    static final String VALID_PASSWORD = "1234";

    @Test
    @DisplayName("비밀번호가 null이면 비밀번호가 없는 상태로 간주된다.")
    void 비밀번호가_null이면_없음으로_간주된다() {
        RoomPassword pw = new RoomPassword(null);

        assertThat(pw.exists()).isFalse();
    }

    @Test
    @DisplayName("정상 길이의 비밀번호면 생성된다.")
    void 정상_비밀번호면_생성된다() {
        RoomPassword pw = new RoomPassword(VALID_PASSWORD);

        assertThat(pw.password()).isEqualTo(VALID_PASSWORD);
        assertThat(pw.exists()).isTrue();
    }

    @ParameterizedTest
    @ValueSource(strings = { "123", "1", "" })
    @DisplayName("최소 길이보다 짧으면 예외가 발생한다.")
    void 최소_길이보다_짧으면_예외가_발생한다(String tooShort) {
        if (tooShort.isEmpty()) {
            return;
        }

        assertThatThrownBy(() -> new RoomPassword(tooShort))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(PASSWORD_LENGTH_EXCEEDED.getMessage());
    }

    @ParameterizedTest
    @ValueSource(strings = { "12345678901", "abcdefghijk", "passwordtoolong" })
    @DisplayName("최대 길이를 초과하면 예외가 발생한다.")
    void 최대_길이를_초과하면_예외가_발생한다(String tooLong) {
        assertThatThrownBy(() -> new RoomPassword(tooLong))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(PASSWORD_LENGTH_EXCEEDED.getMessage());
    }

    @ParameterizedTest
    @ValueSource(strings = { "1234", "abcd", "0000" })
    @DisplayName("입력한 비밀번호가 일치하면 true를 반환한다.")
    void 입력한_비밀번호가_일치하면_성공한다(String input) {
        RoomPassword pw = new RoomPassword(input);

        assertThat(pw.matches(input)).isTrue();
    }

    @ParameterizedTest
    @ValueSource(strings = { "abcd", "9999", "12345" })
    @DisplayName("입력값이 일치하지 않으면 false를 반환한다.")
    void 입력한_비밀번호가_다르면_실패한다(String input) {
        RoomPassword pw = new RoomPassword(VALID_PASSWORD);

        assertThat(pw.matches(input)).isFalse();
    }
}
