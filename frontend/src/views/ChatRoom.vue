<script setup>
import { ref, watch, onMounted } from "vue";
import { useRoute, useRouter } from "vue-router";
import { Client } from "@stomp/stompjs";
import { USER_ERROR_MESSAGES } from "../UserErrorMessages";
import SockJS from "sockjs-client";
import api from "../api";

const route = useRoute();
const router = useRouter();
const roomId = Number(route.params.id);

const nickname = ref(route.query.nickname || null);
const password = ref(route.query.password || "");

watch(
  () => route.query.nickname,
  (val) => { if (val) nickname.value = val; }
);
watch(
  () => route.query.password,
  (val) => { if (val) password.value = val; }
);

const roomTitle = ref("방 정보 불러오는 중...");
const message = ref("");
const messages = ref([]);
const connected = ref(false);

let client = null;
const exitCalled = ref(false);

// 방 정보 가져오기
const fetchRoomInfo = async () => {
  try {
    const res = await api.get(`/api/rooms/${roomId}`);
    roomTitle.value = res.data.title;
  } catch (e) {
    roomTitle.value = "방 정보 없음";
  }
};

// WebSocket 연결
const connect = () => {
  if (client && client.active) return;

  client = new Client({
    reconnectDelay: 0,

    webSocketFactory: () => new SockJS("http://localhost:8080/ws"),

    onConnect: () => {
      connected.value = true;

      client.subscribe(`/topic/room/${roomId}`, (msg) => {
        try {
          messages.value.push(JSON.parse(msg.body));
        } catch (e) {
          console.error("메시지 파싱 오류:", e);
        }
      });
    },
  });

  client.activate();
};

// 메시지 전송
const send = () => {
  if (!client?.active) return;
  if (!message.value.trim()) return;

  if (message.value.length > 200) {
    alert(USER_ERROR_MESSAGES.MESSAGE_LENGTH_EXCEEDED.message);
    return;
  }

  client.publish({
    destination: "/app/chat.send",
    body: JSON.stringify({
      roomId,
      type: "TALK",
      sender: nickname.value,
      content: message.value,
    }),
  });

  message.value = "";
};

// WebSocket 종료
const disconnect = () => {
  if (client?.active) {
    client.deactivate({ force: true });
  }
  connected.value = false;
};

// 방 나가기
const leaveRoom = async () => {
  if (exitCalled.value) return;
  exitCalled.value = true;

  try {
    await api.post(`/api/rooms/${roomId}/exit`, {
      nickname: nickname.value,
    });
  } catch (e) {
    console.warn("EXIT error ignored:", e);
  }

  disconnect();
  router.push("/");
};

// 컴포넌트 마운트
onMounted(async () => {
  while (!nickname.value) {
    await new Promise((r) => setTimeout(r, 10));
  }

  await fetchRoomInfo();

  // 입장 시 닉네임, 비밀번호 전달
  await api.post(`/api/rooms/${roomId}/enter`, {
    nickname: nickname.value,
    password: password.value,
  });

  connect();
});
</script>

<template>
  <div class="chat-container">
    <header class="chat-header">
      <div class="room-title">{{ roomTitle }}</div>

      <div class="status" :class="{ on: connected }">
        {{ connected ? "연결됨" : "연결 안 됨" }}
      </div>

      <button class="btn-leave" @click="leaveRoom">나가기</button>
    </header>

    <section class="messages">
      <div
        v-for="(m, i) in messages"
        :key="i"
        class="message-item"
        :class="{
          'mine': m.sender === nickname,
          'other': m.sender !== nickname && m.type === 'TALK',
          'system': m.type !== 'TALK'
        }"
      >
        <!-- 일반 메시지 -->
        <template v-if="m.type === 'TALK'">
          <div class="bubble">
            <span class="sender" v-if="m.sender !== nickname">[{{ m.sender }}]</span>
            <span class="content">{{ m.content }}</span>
          </div>
        </template>

        <!-- 시스템 메시지 -->
        <template v-else>
          <div class="system-message">
            --- {{ m.content }} ---
          </div>
        </template>
      </div>
    </section>

    <section class="input-area">
      <input
        v-model="message"
        class="message-input"
        placeholder="메시지를 입력하세요"
        @keyup.enter="send"
      />
      <button class="btn-send" @click="send">전송</button>
    </section>
  </div>
</template>

<style scoped>
.chat-container {
  max-width: 600px;
  margin: 0 auto;
  padding: 16px 20px;
  display: flex;
  flex-direction: column;
  height: 100vh;
  box-sizing: border-box;
}
.chat-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 12px;
}
.room-title {
  font-size: 30px;
  font-weight: 800;
}
.status {
  font-size: 16px;
  color: #9ca3af;
  margin-left: 12px;
}
.status.on {
  color: #22c55e;
}
.btn-leave {
  padding: 6px 12px;
  border-radius: 8px;
  background: #ef4444;
  color: white;
  font-size: 13px;
  font-weight: 600;
}
.messages {
  flex: 1;
  border: 1px solid #e5e7eb;
  border-radius: 8px;
  padding: 10px;
  overflow-y: auto;
  margin-bottom: 10px;
  background: #f9fafb;
  word-break: break-word;
  overflow-wrap: break-word;
}
.message-item {
  margin-bottom: 10px;
  display: flex;
  width: 100%;
}
.message-item.mine {
  justify-content: flex-end;
}
.message-item.mine .bubble {
  background: #d1e7ff;
  border-radius: 12px 12px 0 12px;
  text-align: right;
}
.message-item.other {
  justify-content: flex-start;
}
.message-item.other .bubble {
  background: #ffffff;
  border-radius: 12px 12px 12px 0;
  text-align: left;
}
.bubble {
  max-width: 70%;
  padding: 10px 14px;
  font-size: 16px;
  line-height: 1.4;
  word-break: break-word;
  overflow-wrap: break-word;
  box-shadow: 0 1px 4px rgba(0,0,0,0.1);
}
.message-item.system {
  justify-content: center;
}
.system-message {
  font-size: 12px;
  color: #6b7280;
  font-style: italic;
  text-align: center;
}
.input-area {
  display: flex;
  gap: 8px;
}
.message-input {
  flex: 1;
  padding: 10px;
  border-radius: 8px;
  border: 1px solid #d4d4d4;
  font-size: 14px;
}
.btn-send {
  padding: 10px 14px;
  border-radius: 8px;
  background: #4a6cf7;
  color: white;
  font-weight: 600;
  font-size: 14px;
}
</style>
