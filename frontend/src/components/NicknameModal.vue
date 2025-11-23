<script>
export default {
  data() {
    return {
      nickname: "",
    };
  },
  methods: {
    submit() {
      if (!this.nickname.trim()) {
        alert("닉네임을 입력하세요.");
        return;
      }
      this.$emit("submit", this.nickname);
    }
  }
}
</script>

<template>
  <div class="overlay" @click.self="$emit('close')">
    <div class="modal">
      <h2 class="title">닉네임</h2>

      <input
        v-model="nickname"
        placeholder="사용할 닉네임을 입력하세요"
        class="input"
      />

      <div class="actions">
        <button class="btn cancel" @click="$emit('close')">취소</button>
        <button class="btn create" @click="submit">입장</button>
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
  margin-bottom: 16px;
}
.input {
  width: 100%;
  padding: 12px;
  margin: 10px 0 18px;
  border-radius: 8px;
  border: 1px solid #d4d4d4;
  font-size: 15px;
  box-sizing: border-box;
}
.input:focus {
  border-color: #4a6cf7;
  outline: none;
  box-shadow: 0 0 0 1px #4a6cf7;
}
.actions {
  display: flex;
  justify-content: flex-end;
  gap: 10px;
}
.btn {
  padding: 8px 12px;
  border-radius: 8px;
  font-weight: 600;
}
.cancel {
  background: #e5e7eb;
  color: #4b5563;
}
.create {
  background: #4a6cf7;
  color: white;
}
</style>
