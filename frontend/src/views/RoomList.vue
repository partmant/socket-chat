<script>
import api from "../api";

export default {
  data() {
    return {
      rooms: [],
      keyword: "",
    };
  },

  computed: {
    filteredRooms() {
      if (!this.keyword.trim()) return this.rooms;
      return this.rooms.filter((r) =>
        r.title.toLowerCase().includes(this.keyword.toLowerCase())
      );
    },
  },

  mounted() {
    this.fetchRooms();
  },

  methods: {
    async fetchRooms() {
      const res = await api.get("/api/rooms");
      this.rooms = res.data;
    },

    onCreateRoomClick() {
      // TODO: 방 생성 모달 열기
    },

    onEnterRoomClick(room) {
      // TODO: 방 입장 모달 열기
    },
  },
};
</script>

<template>
  <div class="container">
    <h1 class="main-title">socket-chat</h1>

    <header class="header">
      <input v-model="keyword" placeholder="방 제목 검색" class="search" />
      <button @click="onCreateRoomClick" class="btn-create">방 생성하기</button>
    </header>

    <div class="room-list">
      <div v-for="room in filteredRooms" :key="room.id" class="room-card">
        <div class="left">
          <span v-if="room.hasPassword">🔒</span>
          <span class="title">{{ room.title }}</span>
        </div>
        <button class="btn-enter" @click="onEnterRoomClick(room)">입장하기</button>
      </div>
    </div>
  </div>
</template>

<style scoped>
.container {
    max-width: 600px;
    margin: 0 auto;
    padding: 20px;
}
.main-title {
    text-align: center;
    font-size: 28px;
    font-weight: 800;
    color: #4a6cf7; 
    margin-bottom: 30px;
}
.header {
    display: flex;
    justify-content: space-between;
    margin-bottom: 15px;
}
.search {
    flex: 1;
    padding: 8px;
    margin-right: 10px;
}
.btn-create {
    padding: 8px 12px;
    background: #4a6cf7;
    color: white;
}
.room-card {
    display: flex;
    justify-content: space-between;
    align-items: center;
    background: #f7f7f7;
    padding: 12px;
    border-radius: 8px;
    margin: 8px 0;
}
.left {
    display: flex;
    align-items: center;
    gap: 6px;
}
.title {
    font-weight: bold;
}
.btn-enter {
    background: #3897f0;
    color: white;
    padding: 6px 10px;
    border-radius: 6px;
}
</style>
