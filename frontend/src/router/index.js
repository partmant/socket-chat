import { createRouter, createWebHistory } from "vue-router";
import RoomList from "../views/RoomList.vue";

const routes = [
  {
    path: "/",
    name: "RoomList",
    component: RoomList,
  }
];

export default createRouter({
  history: createWebHistory(),
  routes,
});
