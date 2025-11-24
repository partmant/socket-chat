package com.example.socketchatbackend.service.chat.message;

import com.example.socketchatbackend.exception.CustomException;
import com.example.socketchatbackend.exception.ErrorCode;
import org.springframework.stereotype.Component;
import org.springframework.web.util.HtmlUtils;

import com.example.socketchatbackend.repository.chat.RoomMemberRepository;
import com.example.socketchatbackend.dto.chat.message.RoomMessageRequest;
import com.example.socketchatbackend.dto.chat.message.RoomMessageResponse;
import com.example.socketchatbackend.dto.chat.message.MessageType;
import com.example.socketchatbackend.util.ChatConstants;

@Component
public class MessageFactory {

    private final RoomMemberRepository roomMemberRepository;

    public MessageFactory(RoomMemberRepository roomMemberRepository) {
        this.roomMemberRepository = roomMemberRepository;
    }

    public RoomMessageResponse createEnterMessage(RoomMessageRequest req) {
        String nickname = verifySender(req.sender(), req.roomId());

        return new RoomMessageResponse(
                req.roomId(),
                MessageType.ENTER,
                ChatConstants.SYSTEM_SENDER,
                String.format(ChatConstants.ENTER_MESSAGE_FORMAT, nickname)
        );
    }

    public RoomMessageResponse createExitMessage(RoomMessageRequest req) {
        String nickname = verifySender(req.sender(), req.roomId());

        return new RoomMessageResponse(
                req.roomId(),
                MessageType.EXIT,
                ChatConstants.SYSTEM_SENDER,
                String.format(ChatConstants.QUIT_MESSAGE_FORMAT, nickname)
        );
    }

    public RoomMessageResponse createTalkMessage(RoomMessageRequest req) {
        String verifiedSender = verifySender(req.sender(), req.roomId());
        String sanitizedContent = sanitizeContent(req.content());

        return new RoomMessageResponse(
                req.roomId(),
                MessageType.TALK,
                verifiedSender,
                sanitizedContent
        );
    }

    private String verifySender(String sender, Long roomId) {
        if (!roomMemberRepository.exists(roomId, sender)) {
            throw new CustomException(ErrorCode.NICKNAME_NOT_FOUND);
        }
        return sender;
    }

    private String sanitizeContent(String content) {
        if (content == null) {
            return "";
        }

        String escaped = HtmlUtils.htmlEscape(content);
        return escaped.trim();
    }
}
