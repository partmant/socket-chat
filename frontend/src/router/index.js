import { createRouter, createWebHistory } from "vue-router";

const routes = [
  {
    path: "/",
    redirect: "/test-chat",
  },
  {
    path: "/test-chat",
    name: "test-chat",
    component: () => import("../components/ChatTest.vue"),
  },
];

const router = createRouter({
  history: createWebHistory(),
  routes,
});

export default router;
