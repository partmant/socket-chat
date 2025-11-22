package com.example.socketchatbackend.domain.chat.vo;

import static org.assertj.core.api.Assertions.*;

import static com.example.socketchatbackend.constraint.chat.room.RoomConstraints.*;
import static com.example.socketchatbackend.exception.chat.ErrorMessages.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class RoomCapacityTest {

    static final int VALID_CAPACITY = 5;

    @Test
    @DisplayName("허용된 최소값과 최대값 범위 내라면 RoomCapacity가 정상 생성된다.")
    void 허용된_범위라면_정상_생성된다() {
        RoomCapacity min = new RoomCapacity(MIN_ALLOWED_CAPACITY);
        RoomCapacity max = new RoomCapacity(MAX_ALLOWED_CAPACITY);

        assertThat(min.value()).isEqualTo(MIN_ALLOWED_CAPACITY);
        assertThat(max.value()).isEqualTo(MAX_ALLOWED_CAPACITY);
    }

    @ParameterizedTest
    @ValueSource(ints = { MIN_ALLOWED_CAPACITY - 1, MAX_ALLOWED_CAPACITY + 1 })
    @DisplayName("허용된 범위를 벗어나면 예외가 발생한다.")
    void 허용된_범위를_벗어나면_예외가_발생한다(int invalidValue) {
        assertThatThrownBy(() -> new RoomCapacity(invalidValue))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(CAPACITY_EXCEEDED.getMessage());
    }

    @ParameterizedTest
    @ValueSource(ints = {VALID_CAPACITY - 1, VALID_CAPACITY - 2})
    @DisplayName("현재 인원수가 정원보다 적으면 입장이 가능하다.")
    void 현재_인원이_정원보다_적으면_입장이_가능하다(int currentUserCount) {
        RoomCapacity capacity = new RoomCapacity(VALID_CAPACITY);

        assertThat(capacity.canAccept(currentUserCount)).isTrue();
    }

    @ParameterizedTest
    @ValueSource(ints = { VALID_CAPACITY, VALID_CAPACITY + 1, VALID_CAPACITY + 2 })
    @DisplayName("현재 인원수가 정원과 같거나 크면 입장이 불가능하다.")
    void 현재_인원이_정원과_같거나_크면_입장이_불가능하다(int currentUserCount) {
        RoomCapacity capacity = new RoomCapacity(VALID_CAPACITY);

        assertThat(capacity.canAccept(currentUserCount)).isFalse();
    }
}
