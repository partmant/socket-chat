package com.example.socketchatbackend.domain.chat.vo;

import static com.example.socketchatbackend.constraint.chat.room.RoomConstraints.MAX_PASSWORD_LENGTH;
import static com.example.socketchatbackend.constraint.chat.room.RoomConstraints.MIN_PASSWORD_LENGTH;
import static org.assertj.core.api.Assertions.*;

import com.example.socketchatbackend.exception.CustomException;
import com.example.socketchatbackend.exception.ErrorCode;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class RoomPasswordTest {

    static final String VALID_PASSWORD = "1234";
    static final String TOO_LONG_PASSWORD = "a".repeat(MAX_PASSWORD_LENGTH + 1);
    static final String TOO_SHORT_PASSWORD = "a".repeat(MIN_PASSWORD_LENGTH -1);

    @ParameterizedTest
    @ValueSource(strings = { "", " ", "  " })
    @DisplayName("비밀번호가 없는 상태이면 Null Object가 반환된다.")
    void none_호출시_비밀번호가_없는_상태가_반환된다() {
        RoomPassword pw = RoomPassword.none();

        assertThat(pw.exists()).isFalse();
        assertThat(pw.value()).isEqualTo("");
        assertThat(pw.matches("전부 false")).isFalse();
    }

    @Test
    @DisplayName("정상 길이의 비밀번호면 생성된다.")
    void 정상_비밀번호면_생성된다() {
        RoomPassword pw = RoomPassword.of(VALID_PASSWORD);

        assertThat(pw.value()).isEqualTo(VALID_PASSWORD);
        assertThat(pw.exists()).isTrue();
    }

    @ParameterizedTest
    @ValueSource(strings = { "", " ", "  " })
    @DisplayName("of() 호출 시 비밀번호가 공백 또는 빈 문자열이면 예외가 발생한다.")
    void of_호출시_비밀번호가_공백이거나_비어있으면_예외가_발생한다(String blankOrEmpty) {
        assertThatThrownBy(() -> RoomPassword.of(blankOrEmpty))
                .isInstanceOf(CustomException.class)
                .hasMessage(ErrorCode.PASSWORD_BLANK.message());
    }

    @Test
    @DisplayName("of() 호출 시 비밀번호가 null이면 예외가 발생한다.")
    void of_호출시_비밀번호가_null이면_예외가_발생한다() {
        assertThatThrownBy(() -> RoomPassword.of(null))
                .isInstanceOf(CustomException.class)
                .hasMessage(ErrorCode.PASSWORD_BLANK.message());
    }

    @Test
    @DisplayName("최소 길이보다 짧으면 예외가 발생한다.")
    void 최소_길이보다_짧으면_예외가_발생한다() {
        assertThatThrownBy(() -> RoomPassword.of(TOO_SHORT_PASSWORD))
                .isInstanceOf(CustomException.class)
                .hasMessage(ErrorCode.PASSWORD_LENGTH_EXCEEDED.message());
    }

    @Test
    @DisplayName("최대 길이를 초과하면 예외가 발생한다.")
    void 최대_길이를_초과하면_예외가_발생한다() {
        assertThatThrownBy(() -> RoomPassword.of(TOO_LONG_PASSWORD))
                .isInstanceOf(CustomException.class)
                .hasMessage(ErrorCode.PASSWORD_LENGTH_EXCEEDED.message());
    }

    @Test
    @DisplayName("입력한 비밀번호가 일치하면 true를 반환한다.")
    void 입력한_비밀번호가_일치하면_성공한다() {
        RoomPassword pw = RoomPassword.of(VALID_PASSWORD);

        assertThat(pw.matches(VALID_PASSWORD)).isTrue();
    }

    @ParameterizedTest
    @ValueSource(strings = { "abcd", "99999", "123456" })
    @DisplayName("입력값이 일치하지 않으면 false를 반환한다.")
    void 입력한_비밀번호가_다르면_실패한다(String input) {
        RoomPassword pw = RoomPassword.of(VALID_PASSWORD);

        assertThat(pw.matches(input)).isFalse();
    }
}
