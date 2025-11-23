import { createRouter, createWebHistory } from "vue-router";
import RoomList from "../views/RoomList.vue";
import ChatRoom from "../views/ChatRoom.vue";

const routes = [
  {
    path: "/",
    name: "RoomList",
    component: RoomList,
  },
  {
    path: "/rooms/:id",
    name: "ChatRoom",
    component: ChatRoom,
  },
];

export default createRouter({
  history: createWebHistory(),
  routes,
});
