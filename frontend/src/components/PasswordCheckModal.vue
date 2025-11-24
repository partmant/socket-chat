<script>
import { USER_ERROR_MESSAGES } from "../UserErrorMessages"; 
import api from "../api";

export default {
  props: ["room"],

  data() {
    return {
      password: "",
      error: null,
    };
  },

  methods: {
    async verify() {
      this.error = null; 

      if (!this.password.trim()) {
        this.error = USER_ERROR_MESSAGES.PASSWORD_BLANK.message;
        return;
      }

      try { 
        await api.post(`/api/rooms/${this.room.id}/check-password`, {
          password: this.password,
        });

        this.$emit("success", this.password);
      } catch (e) {
        const errorResponse = e.response;

        if (errorResponse && errorResponse.data) {
          const errorCode = errorResponse.data.errorCode;

          if (USER_ERROR_MESSAGES[errorCode]) {
            this.error = USER_ERROR_MESSAGES[errorCode].message;
          } else {
            this.error = USER_ERROR_MESSAGES.DEFAULT.message;
          }
        } else {
          this.error = error = USER_ERROR_MESSAGES.DEFAULT.message;
        }
      }
    },
  },
};
</script>

<template>
  <div class="overlay" @click.self="$emit('close')">
    <div class="modal">
      <h2 class="title">비밀번호 입력</h2>

      <input
        type="password"
        v-model="password"
        placeholder="비밀번호"
        class="input"
      />

      <p v-if="error" class="error">{{ error }}</p>

      <div class="actions">
        <button class="btn cancel" @click="$emit('close')">취소</button>
        <button class="btn enter" @click="verify">입장</button>
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
  padding: 22px;
  border-radius: 12px;
  width: 100%;
  max-width: 350px;
  box-shadow: 0 6px 20px rgba(0,0,0,0.15);
}
.title {
  text-align: center;
  font-size: 22px;
  font-weight: 800;
  color: #4a6cf7;
  margin-bottom: 18px;
}
.input {
  width: 100%;
  padding: 12px;
  margin-bottom: 22px;
  border-radius: 8px;
  border: 1px solid #d4d4d4;
  font-size: 15px;
  box-sizing: border-box;
}
.input:focus {
  outline: none;
  border-color: #4a6cf7;
  box-shadow: 0 0 0 1px #4a6cf7;
}
.error {
  color: #e63946;
  font-size: 14px;
  margin-top: -16px;
  margin-bottom: 14px;
  text-align: center;
}
.actions {
  display: flex;
  justify-content: flex-end;
  gap: 10px;
}
.btn {
  padding: 10px 14px;
  border-radius: 8px;
  font-weight: 600;
  font-size: 14px;
}
.cancel {
  background: #e5e7eb;
  color: #4b5563;
}
.enter {
  background: #4a6cf7;
  color: white;
}
</style>
