package com.example.socketchatbackend.domain.chat.vo;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import static com.example.socketchatbackend.exception.chat.ErrorMessages.*;
import static com.example.socketchatbackend.constraint.chat.room.RoomConstraints.MAX_NICKNAME_LENGTH;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class RoomNicknameTest {

    static final String VALID_NICKNAME = "user1";

    @Test
    @DisplayName("닉네임이 null이면 예외가 발생한다.")
    void 닉네임이_null이면_예외가_발생한다() {
        assertThatThrownBy(() -> new RoomNickname(null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(NICKNAME_NULL.getMessage());
    }

    @Test
    @DisplayName("닉네임이 공백이면 예외가 발생한다.")
    void 닉네임_공백이면_예외가_발생한다() {
        assertThatThrownBy(() -> new RoomNickname("   "))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(NICKNAME_BLANK.getMessage());
    }

    @Test
    @DisplayName("닉네임 길이가 1~16자를 벗어나면 예외가 발생한다.")
    void 닉네임_길이가_유효한_범위가_아니면_예외를_발생한다() {
        String tooLong = "a".repeat(MAX_NICKNAME_LENGTH + 1);

        assertThatThrownBy(() -> new RoomNickname(tooLong))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(NICKNAME_LENGTH_EXCEED.getMessage());
    }

    @Test
    @DisplayName("정상적인 닉네임은 예외가 발생하지 않는다.")
    void 닉네임이_정상이면_성공한다() {
        assertThatCode(() -> new RoomNickname(VALID_NICKNAME))
                .doesNotThrowAnyException();
    }
}
