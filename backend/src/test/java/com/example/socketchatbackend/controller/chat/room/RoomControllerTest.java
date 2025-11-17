package com.example.socketchatbackend.controller.chat.room;

import static org.mockito.BDDMockito.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.example.socketchatbackend.dto.chat.room.RoomEnterRequest;
import com.example.socketchatbackend.dto.chat.room.RoomCreateRequest;
import com.example.socketchatbackend.dto.chat.room.RoomResponse;
import com.example.socketchatbackend.service.chat.room.RoomCommandService;
import com.example.socketchatbackend.service.chat.room.RoomQueryService;
import com.example.socketchatbackend.service.chat.room.RoomEntranceService;

@WebMvcTest(RoomController.class)
class RoomControllerTest {

    private static final String BASE_URL = "/api/rooms";

    private static final long VALID_ID1 = 1L;
    private static final long VALID_ID2 = 2L;
    private static final String VALID_TITLE1 = "test-room1";
    private static final String VALID_TITLE2 = "test-room2";
    private static final String VALID_PASSWORD = "1234";
    private static final int VALID_MAX_USER_COUNT = 10;

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockitoBean
    RoomCommandService commandService;

    @MockitoBean
    RoomQueryService queryService;

    @MockitoBean
    RoomEntranceService entranceService;

    @Test
    @DisplayName("방 생성 요청이 들어오면 생성된 방 ID를 반환한다")
    void 방_생성_요청이_들어오면_ID를_반환한다() throws Exception {
        RoomCreateRequest request = new RoomCreateRequest(VALID_TITLE1, VALID_PASSWORD, VALID_MAX_USER_COUNT);
        given(commandService.create(any(RoomCreateRequest.class))).willReturn(VALID_ID1);

        mockMvc.perform(post(BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(content().string(String.valueOf(VALID_ID1)));
    }

    @Test
    @DisplayName("방 목록을 조회하면 전체 방 목록을 반환한다")
    void 방_목록을_조회하면_전체_목록을_반환한다() throws Exception {
        List<RoomResponse> rooms = List.of(
                new RoomResponse(VALID_ID1, VALID_TITLE1, true, VALID_MAX_USER_COUNT),
                new RoomResponse(VALID_ID2, VALID_TITLE2, false, VALID_MAX_USER_COUNT)
        );
        given(queryService.findAll(null)).willReturn(rooms);

        mockMvc.perform(get(BASE_URL))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(VALID_ID1))
                .andExpect(jsonPath("$[0].title").value(VALID_TITLE1))
                .andExpect(jsonPath("$[0].hasPassword").value(true))
                .andExpect(jsonPath("$[1].id").value(VALID_ID2))
                .andExpect(jsonPath("$[1].title").value(VALID_TITLE2))
                .andExpect(jsonPath("$[1].hasPassword").value(false));
    }

    @Test
    @DisplayName("방 ID로 조회하면 해당 방 정보를 반환한다")
    void 방_ID로_조회하면_해당_방_정보를_반환한다() throws Exception {
        RoomResponse response = new RoomResponse(VALID_ID1, VALID_TITLE1, true, VALID_MAX_USER_COUNT);
        given(queryService.findById(VALID_ID1)).willReturn(response);

        mockMvc.perform(get(BASE_URL + "/" + VALID_ID1))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(VALID_ID1))
                .andExpect(jsonPath("$.title").value(VALID_TITLE1))
                .andExpect(jsonPath("$.hasPassword").value(true));
    }

    @Test
    @DisplayName("비밀번호가 필요한 방에 올바른 비밀번호로 입장하면 성공한다")
    void 비밀번호가_필요한_방에_올바른_비밀번호로_입장하면_성공한다() throws Exception {
        RoomEnterRequest request = new RoomEnterRequest(VALID_PASSWORD);
        willDoNothing().given(entranceService).validateEnter(VALID_ID1, VALID_PASSWORD);

        mockMvc.perform(post(BASE_URL + "/" + VALID_ID1 + "/enter")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());
    }
}
