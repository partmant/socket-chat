package com.example.socketchatbackend.service.chat;

import static org.assertj.core.api.Assertions.*;

import static com.example.socketchatbackend.exception.chat.ErrorMessages.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import com.example.socketchatbackend.dto.chat.RoomRequest;
import com.example.socketchatbackend.dto.chat.RoomResponse;
import com.example.socketchatbackend.repository.chat.InMemoryRoomRepository;

class RoomQueryServiceTest {

    private static final String TITLE_UTECO = "우테코 오픈 미션방";
    private static final String TITLE_COTE = "코테 준비방";
    private static final String TITLE_NOT_EXIST = "자바";

    private static final String VALID_PASSWORD = "1234";
    private static final int VALID_MAX_USER_COUNT = 10;

    private RoomCommandService command;
    private RoomQueryService query;

    @BeforeEach
    void setUp() {
        InMemoryRoomRepository repo = new InMemoryRoomRepository();
        command = new RoomCommandService(repo);
        query = new RoomQueryService(repo);
    }

    @Test
    @DisplayName("생성된 방이 없으면 빈 목록을 반환한다.")
    void 생성된_방이_없으면_빈_목록을_반환한다() {
        List<RoomResponse> result = query.findAll(null);
        assertThat(result).isEmpty();
    }

    @ParameterizedTest
    @NullSource
    @ValueSource(strings = { "", " ", "\t", "\n" })
    @DisplayName("검색어가 없으면 전체 목록을 조회한다.")
    void 검색어가_없으면_전체_목록을_조회한다(String keyword) {
        command.create(new RoomRequest(TITLE_UTECO, VALID_PASSWORD, VALID_MAX_USER_COUNT));
        command.create(new RoomRequest(TITLE_COTE, VALID_PASSWORD, VALID_MAX_USER_COUNT));

        List<RoomResponse> result = query.findAll(keyword);

        assertThat(result).hasSize(2);
        assertThat(result)
                .extracting("title")
                .containsExactlyInAnyOrder(TITLE_UTECO, TITLE_COTE);
    }

    @Test
    @DisplayName("검색어가 주어지면 제목에 검색어를 포함한 방만 조회된다.")
    void 검색어를_포함한_방만_조회된다() {
        command.create(new RoomRequest(TITLE_UTECO, VALID_PASSWORD, VALID_MAX_USER_COUNT));
        command.create(new RoomRequest(TITLE_COTE, VALID_PASSWORD, VALID_MAX_USER_COUNT));

        List<RoomResponse> result = query.findAll("우테코");

        assertThat(result).hasSize(1);
        assertThat(result.get(0).title()).isEqualTo(TITLE_UTECO);
    }

    @Test
    @DisplayName("검색 결과가 없으면 빈 목록을 반환한다.")
    void 검색_결과가_없으면_빈_목록을_반환한다() {
        command.create(new RoomRequest(TITLE_UTECO, VALID_PASSWORD, VALID_MAX_USER_COUNT));
        command.create(new RoomRequest(TITLE_COTE, VALID_PASSWORD, VALID_MAX_USER_COUNT));

        List<RoomResponse> result = query.findAll(TITLE_NOT_EXIST);

        assertThat(result).isEmpty();
    }

    @Test
    @DisplayName("존재하지 않는 ID로 조회하면 예외가 발생한다.")
    void 존재하지_않는_ID로_조회하면_예외가_발생한다() {
        assertThatThrownBy(() -> query.findById(1L))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(ROOM_NOT_FOUND.getMessage());
    }

    @Test
    @DisplayName("ID로 채팅방을 조회할 수 있다.")
    void ID로_채팅방을_조회할_수_있다() {
        Long id = command.create(new RoomRequest(TITLE_UTECO, VALID_PASSWORD, VALID_MAX_USER_COUNT));
        RoomResponse res = query.findById(id);

        assertThat(res.id()).isEqualTo(id);
        assertThat(res.title()).isEqualTo(TITLE_UTECO);
    }
}
