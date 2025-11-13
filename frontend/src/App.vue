<script setup>
import Stomp from "stompjs";

let stompClient = null;

function connect() {
  const socket = new WebSocket("ws://localhost:8080/ws");
  stompClient = Stomp.over(socket);

  stompClient.connect({}, () => {
    console.log("Connected to WebSocket!");

    stompClient.subscribe("/topic/test", (message) => {
      console.log("Received:", message.body);
    });
  });
}

function sendTestMessage() {
  if (!stompClient || !stompClient.connected) {
    console.log("Not connected yet.");
    return;
  }
  stompClient.send("/app/test", {}, "Hello from Vue!");
}
</script>

<template>
  <div>
    <button @click="connect">Connect</button>
    <button @click="sendTestMessage">Send Test</button>
  </div>
</template>
