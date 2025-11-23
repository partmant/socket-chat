package com.example.socketchatbackend.controller.chat.room;

import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.example.socketchatbackend.dto.chat.message.RoomMessageRequest;
import com.example.socketchatbackend.dto.chat.message.MessageType;
import com.example.socketchatbackend.service.chat.room.RoomMessageService;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(RoomMessageController.class)
class RoomMessageControllerTest {

    private static final String BASE_URL = "/api/rooms";

    private static final long VALID_ROOM_ID = 1L;
    private static final String VALID_NICKNAME = "user1";

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockitoBean
    RoomMessageService roomMessageService;

    @Test
    @DisplayName("TALK 메시지 전송 요청이 오면 정상 처리 후 200 OK를 반환한다")
    void 메시지_전송요청이_유효하면_성공한다() throws Exception {
        RoomMessageRequest request = RoomMessageRequest.builder()
                .roomId(VALID_ROOM_ID)
                .type(MessageType.TALK)
                .sender(VALID_NICKNAME)
                .content("안녕")
                .build();

        willDoNothing().given(roomMessageService).sendTalkMessage(any(RoomMessageRequest.class));

        mockMvc.perform(
                        post(BASE_URL + "/" + VALID_ROOM_ID + "/message")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(request))
                )
                .andExpect(status().isOk());

        verify(roomMessageService).sendTalkMessage(eq(request));
    }
}
