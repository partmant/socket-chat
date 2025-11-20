<script setup>
import { ref } from "vue";
import { Client } from "@stomp/stompjs";
import SockJS from "sockjs-client";

const roomId = ref(1);
const message = ref("");
const messages = ref([]);

let client = null;

// STOMP 연결
const connect = () => {
  console.log("connect() 함수 실행");

  if (client && client.active) {
    console.warn("이미 연결되어 있습니다.");
    return;
  }

  client = new Client({
    debug: (str) => console.log("--- STOMP DEBUG:", str),
    webSocketFactory: () => new SockJS("http://localhost:8080/ws"),

    onConnect: () => {
      console.log("STOMP 연결 성공");

      // 서버 토픽 구독
      client.subscribe(`/topic/room/${roomId.value}`, (msg) => {
        try {
          const chatMessage = JSON.parse(msg.body);
          messages.value.push(chatMessage);
        } catch (e) {
          console.error("메시지 파싱 오류:", e, msg.body);
        }
      });

      // 입장 시 입장 메시지 전송
      const enterPayload = {
        roomId: Number(roomId.value),
        type: "ENTER",
        sender: "홍길동",
        content: ""
      };

      client.publish({
        destination: "/app/chat.send",
        body: JSON.stringify(enterPayload)
      });
    }
  });

  client.activate();
};

// 일반 메시지 전송
const send = () => {
  if (!client || !client.active) {
    console.warn("연결되지 않았습니다.");
    return;
  }
  if (!message.value.trim()) return;

  const payload = {
    roomId: Number(roomId.value),
    type: "TALK",
    sender: "홍길동",
    content: message.value
  };

  client.publish({
    destination: "/app/chat.send",
    body: JSON.stringify(payload)
  });

  message.value = "";
};

// 퇴장 시 퇴장 메시지 전송
const disconnect = () => {
  if (!client || !client.active) {
    console.warn("이미 연결 없음");
    return;
  }

  const exitPayload = {
    roomId: Number(roomId.value),
    type: "EXIT",
    sender: "홍길동",
    content: ""
  };

  // 퇴장 메시지 전송
  client.publish({
    destination: "/app/chat.send",
    body: JSON.stringify(exitPayload)
  });

  // WebSocket 종료
  client.deactivate();
  console.log("연결 종료");
};
</script>

<template>
  <div>
    <h2>WebSocket Chat Test</h2>
    <div>
      <input v-model="roomId" type="number" placeholder="roomId 입력" />
      <button @click="connect">Connect</button>
      <button @click="disconnect">Disconnect</button>
    </div>
    <div style="margin-top: 10px;">
      <input
        v-model="message"
        placeholder="보낼 메시지"
        @keyup.enter="send"
        style="width: 250px;"
      />
      <button @click="send">Send</button>
    </div>
    <hr>
    <h3>수신 메시지 (Room {{ roomId }})</h3>
    <div
      style="border: 1px solid #ccc; padding: 10px; max-height: 300px; overflow-y: auto;">
      <div v-for="(m, i) in messages" :key="i" style="margin-bottom: 5px;">
        <span v-if="m.type === 'TALK'">
          <strong>[{{ m.sender }}]</strong>: {{ m.content }}
        </span>
        <span v-else style="color: gray; font-style: italic;">
          --- {{ m.content }} ---
        </span>
      </div>
    </div>
  </div>
</template>
