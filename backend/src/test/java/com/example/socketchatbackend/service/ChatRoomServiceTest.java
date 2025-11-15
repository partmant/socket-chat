package com.example.socketchatbackend.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import static com.example.socketchatbackend.exception.chat.ChatErrorMessages.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import com.example.socketchatbackend.dto.chat.ChatRoomInfoResponse;
import com.example.socketchatbackend.dto.chat.ChatRoomRequest;
import com.example.socketchatbackend.repository.chat.InMemoryChatRoomRepository;
import com.example.socketchatbackend.service.chat.ChatRoomService;

class ChatRoomServiceTest {

    private static final String VALID_TITLE = "test-room";
    private static final String TITLE_UTECO = "우테코 오픈 미션방";
    private static final String TITLE_COTE = "코테 준비방";
    private static final String TITLE_NOT_EXIST = "자바";

    private static final String VALID_PASSWORD = "1234";
    private static final int VALID_MAX_USER_COUNT = 10;

    private ChatRoomService chatRoomService;

    @BeforeEach
    void setUp() {
        chatRoomService = new ChatRoomService(new InMemoryChatRoomRepository());
    }

    // 방 생성
    @Test
    @DisplayName("같은 제목의 방이 이미 존재하면 방을 생성할 수 없다.")
    void 같은_제목의_방이_존재하면_예외가_발생한다() {
        ChatRoomRequest req1 = new ChatRoomRequest(VALID_TITLE, VALID_PASSWORD, VALID_MAX_USER_COUNT);
        ChatRoomRequest req2 = new ChatRoomRequest(VALID_TITLE, VALID_PASSWORD, VALID_MAX_USER_COUNT);

        chatRoomService.create(req1);

        assertThatThrownBy(() -> chatRoomService.create(req2))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining(TITLE_DUPLICATION.getMessage());
    }

    @Test
    @DisplayName("유효한 입력이면 채팅방이 생성되고 ID가 반환된다.")
    void 유효한_입력이면_채팅방이_생성되고_ID가_반환된다() {
        ChatRoomRequest req = new ChatRoomRequest(VALID_TITLE, VALID_PASSWORD, VALID_MAX_USER_COUNT);

        Long id = chatRoomService.create(req);

        assertThat(id).isNotNull();
    }

    // 방 목록 조회
    @Test
    @DisplayName("생성된 방이 없으면 빈 목록을 반환한다.")
    void 생성된_방이_없으면_빈_목록을_반환한다() {
        List<ChatRoomInfoResponse> result = chatRoomService.findAll(null);

        assertThat(result).isEmpty();
    }

    @ParameterizedTest
    @NullSource
    @ValueSource(strings = { "", " ", "  ", "\t", "\n" })
    @DisplayName("검색어가 없으면 전체 목록을 조회한다.")
    void 검색어가_없으면_전체_목록을_조회한다(String keyword) {
        chatRoomService.create(new ChatRoomRequest(TITLE_UTECO, VALID_PASSWORD, VALID_MAX_USER_COUNT));
        chatRoomService.create(new ChatRoomRequest(TITLE_COTE, VALID_PASSWORD, VALID_MAX_USER_COUNT));

        List<ChatRoomInfoResponse> result = chatRoomService.findAll(keyword);

        assertThat(result).hasSize(2);
        assertThat(result)
                .extracting("title")
                .containsExactlyInAnyOrder(TITLE_UTECO, TITLE_COTE);
    }

    @Test
    @DisplayName("검색어가 주어지면 제목에 검색어를 포함한 방만 조회된다.")
    void 검색어를_포함한_방만_조회된다() {
        chatRoomService.create(new ChatRoomRequest(TITLE_UTECO, VALID_PASSWORD, VALID_MAX_USER_COUNT));
        chatRoomService.create(new ChatRoomRequest(TITLE_COTE, VALID_PASSWORD, VALID_MAX_USER_COUNT));

        List<ChatRoomInfoResponse> result = chatRoomService.findAll("우테코");

        assertThat(result).hasSize(1);
        assertThat(result)
                .extracting("title")
                .containsExactly(TITLE_UTECO);
    }

    @Test
    @DisplayName("검색 결과가 없으면 빈 목록을 반환한다.")
    void 검색_결과가_없으면_빈_목록을_반환한다() {
        chatRoomService.create(new ChatRoomRequest(TITLE_UTECO, VALID_PASSWORD, VALID_MAX_USER_COUNT));
        chatRoomService.create(new ChatRoomRequest(TITLE_COTE, VALID_PASSWORD, VALID_MAX_USER_COUNT));

        List<ChatRoomInfoResponse> result = chatRoomService.findAll(TITLE_NOT_EXIST);

        assertThat(result).isEmpty();
    }
}
