<script>
import api from "../api";

export default {
  data() {
    return {
      title: "",
      password: "",
      isPrivate: false,
      maxUserCount: 10,
    };
  },

  methods: {
    async createRoom() {
      if (!this.title.trim()) {
        alert("방 제목을 입력하세요.");
        return;
      }

      if (this.isPrivate && !this.password.trim()) {
        alert("비밀번호를 입력해야 합니다.");
        return;
      }

      try {
        const payload = {
          title: this.title,
          maxUserCount: this.maxUserCount,
        };

        if (this.isPrivate) {
          payload.password = this.password;
        }

        const res = await api.post("/api/rooms", payload);

        this.$emit("created", res.data);
        this.$emit("close");

      } catch (e) {
        console.error(e);
        alert("방 생성에 실패했습니다.");
      }
    }
  }
};
</script>

<template>
  <div class="overlay" @click.self="$emit('close')">
    <div class="modal">
      <h2 class="title">방 생성</h2>

      <input
        v-model="title"
        placeholder="방 제목"
        class="input"
      />

      <div class="checkbox">
        <input
          type="checkbox"
          id="isPrivate"
          v-model="isPrivate"
          class="check"
        />
        <label for="isPrivate">비밀번호 설정</label>
      </div>

      <input
        type="password"
        v-model="password"
        placeholder="비밀번호"
        class="input"
        :disabled="!isPrivate"
        :class="{ disabled: !isPrivate }"
      />

      <select v-model="maxUserCount" class="input">
        <option v-for="n in 10" :key="n" :value="n">{{ n }}명</option>
      </select>

      <div class="actions">
        <button class="btn cancel" @click="$emit('close')">취소</button>
        <button class="btn create" @click="createRoom">생성</button>
      </div>
    </div>
  </div>
</template>

<style scoped>
.overlay {
  position: fixed;
  inset: 0;
  background: rgba(0, 0, 0, 0.35);
  backdrop-filter: blur(4px);
  display: flex;
  justify-content: center;
  align-items: center;
}
.modal {
  background: white;
  padding: 24px;
  border-radius: 12px;
  width: 100%;
  max-width: 380px;
  box-shadow: 0 6px 20px rgba(0,0,0,0.2);
}
.title {
  text-align: center;
  font-size: 22px;
  font-weight: 700;
  margin-bottom: 18px;
  color: #1f2937;
}
.input {
  width: 100%;
  padding: 12px;
  margin: 8px 0;
  border-radius: 8px;
  border: 1px solid #d1d5db;
  box-sizing: border-box;
  transition: border-color 0.2s;
}
.input:focus {
  outline: none;
  border-color: #4f46e5;
  box-shadow: 0 0 0 1px #4f46e5;
}
.checkbox {
  display: flex;
  align-items: center;
  gap: 6px;
  margin: 8px 0;
}
.disabled {
  opacity: 0.5;
  cursor: not-allowed;
}
.actions {
  display: flex;
  justify-content: flex-end;
  gap: 12px;
  margin-top: 18px;
}
.btn {
  padding: 10px 16px;
  border-radius: 8px;
  font-weight: 600;
  cursor: pointer;
}
.cancel {
  background: #e5e7eb;
  color: #4b5563;
}
.create {
  background: #4f46e5;
  color: white;
}
</style>
