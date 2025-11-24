# Socket-Chat

## 📌 프로젝트 개요

이 프로젝트는 WebSocket을 활용한 실시간 채팅 애플리케이션입니다.  
사용자는 채팅방을 생성하거나 목록에서 방을 선택하여 입장할 수 있으며,  
다른 사용자들과 실시간으로 메시지를 주고받을 수 있습니다.  
비밀번호 방, 인원 제한, 방 삭제 등 기본적인 채팅방 관리 기능을 중심으로
실시간 통신 구조와 WebSocket(STOMP) 기반 메시지 전달 방식을 직접 설계·구현하는 것을 목표로 했습니다.

## 🖼️ 화면 미리보기
<div style="display: flex; gap: 10px;">
  <img src="https://github.com/user-attachments/assets/2a783a54-fc0b-44d9-9808-49c83988ee9a" width="300" />
  <img src="https://github.com/user-attachments/assets/5b430750-9565-4808-8475-d2fe63322e0c" width="300" />
  <img src="https://github.com/user-attachments/assets/e46b1503-9810-4f9b-a0f3-d0537d8c9ee4" width="300" height="400" />
</div>

## 🛠 기술 스택

### Backend
- Java 21
- Spring Boot 3.5.7
- WebSocket(STOMP), SockJS

### Frontend
- Vue 3
- Vite
- SockJS-client, STOMP.js

### Infra
- Oracle Cloud (추후 예정)

## 🚀 실행 방법
- Backend 실행 방법

```
# 1. 프로젝트 클론
git clone https://github.com/partmant/socket-chat-backend.git
cd socket-chat-backend

# 2. 빌드 및 실행
./gradlew build
./gradlew bootRun
```

- Frontend 실행 방법

```
# 1. 프로젝트 클론
git clone https://github.com/partmant/socket-chat-frontend.git
cd socket-chat-frontend

# 2. 의존성 설치
npm install

# 3. 개발 서버 실행
npm run dev
```

## 📋 구현 기능 목록

### 방 목록 조회
- [x] 현재 생성된 방 목록을 조회한다.
  - 방 제목, 비밀번호, 현재 인원 수 여부를 포함한다.
- [x] 사용자가 검색어를 전달하면 제목에 검색어를 포함한 방만 조회한다.
  - 검색어가 null 또는 공백이면 전체 목록을 조회한다.
  - 검색 결과가 없으면 빈 목록을 반환한다.

### 방 생성
- [x] 사용자가 방 생성 요청을 보낸다.
  - [x] 방 제목을 입력받는다.
    - 제목이 null 또는 공백이면 예외를 발생시킨다.
    - 제목 길이가 1~50자를 벗어나면 예외를 발생시킨다.
    - 동일한 제목의 방이 이미 존재하면 예외를 발생시킨다
  - [x] 비밀번호를 선택적으로 입력받는다.
    - 비밀번호가 null이면 비밀번호 없는 방으로 생성한다.
    - 비밀번호가 존재할 경우 길이가 4~10자를 벗어나면 예외를 발생시킨다.
  - [x] 방 허용 인원을 입력받는다.
    -  허용 인원이 1~10 이내가 아니면 예외를 발생시킨다.
  - [x] 모든 검증을 통과하면 방을 생성하고 저장한다.
  - [x] 방 생성 후 사용자는 방으로 자동으로 입장한다.

### 방 입장
- [x] 사용자가 특정 방에 입장 요청을 보낸다.
  - [x] 닉네임을 입력받는다.
    - 닉네임이 null 또는 공백이면 예외를 발생시킨다.
    - 닉네임 길이가 1~16자를 벗어나면 예외를 발생시킨다.
    - 닉네임이 해당 방 내에서 이미 존재하면 예외를 발생시킨다.
  - [x] 비밀번호가 있는 방은 비밀번호를 입력받는다.
    - 비밀번호가 null 또는 공백이면 예외를 발생시킨다.
    - 비밀번호가 일치하지 않으면 예외를 발생시킨다.
  - [x] 방의 현재 인원이 최대 허용 인원을 초과하면 입장을 허용하지 않는다.
- [x] 입장 후 시스템 메시지를 같은 방 사용자들에게 전송한다.

### 방 퇴장
- [x] 사용자가 방 퇴장 요청을 보낸다. 
  - 요청한 방이 존재하지 않으면 예외를 발생시킨다.
  - 요청한 닉네임이 해당 방에 존재하지 않으면 예외를 발생시킨다.
- [x] 정상적인 요청이면 해당 사용자를 방에서 제거한다.
- [x] 퇴장 후 시스템 메시지를 같은 방 사용자들에게 전송한다.

### 방 삭제
- [x] 방의 현재 인원 수가 0이면 해당 방을 삭제한다.

### 채팅 메시지 송수신
- [x] 사용자가 메시지를 전송한다.
  - 메시지가 200자 이상이면 예외를 발생시킨다.
- [x] 서버는 방에 속한 모든 사용자에게 메시지를 전송한다.

## 🔧 추가 개선 항목

- [ ] 퇴장 시 이중 퇴장 요청 문제
- [ ] 방 생성 시 이름 입력 전에 이미 방 생성되는 문제

## 📌 추후 구현 예정 기능

- 채팅방 내 사용자 확인 기능
- 강제퇴장 기능
- 메시지 저장 및 최근 대화 로딩 기능
- JWT 기반 인증 기능
- 파일 및 이미지 전송 등 채팅 확장 기능

## 📁 프로젝트 디렉토리 구조

- Backend 디렉토리 구조

```
com
    └───example
        └───socketchatbackend
            │   BackendApplication.java
            │
            ├───config
            │       WebSocketConfig.java
            │
            ├───constraint
            │   └───chat
            │       ├───message
            │       │       MessageConstraints.java
            │       │
            │       └───room
            │               RoomConstraints.java
            │
            ├───controller
            │   └───chat
            │       ├───room
            │       │       RoomController.java
            │       │       RoomMemberController.java
            │       │
            │       └───websocket
            │               WebSocketMessageController.java
            │
            ├───domain
            │   └───chat
            │       │   Room.java
            │       │
            │       └───vo
            │               RoomCapacity.java
            │               RoomNickname.java
            │               RoomPassword.java
            │               RoomTitle.java
            │
            ├───dto
            │   ├───chat
            │   │   ├───message
            │   │   │       MessageType.java
            │   │   │       RoomMessageRequest.java
            │   │   │       RoomMessageResponse.java
            │   │   │
            │   │   └───room
            │   │           RoomCreateRequest.java
            │   │           RoomEnterRequest.java
            │   │           RoomExitRequest.java
            │   │           RoomInfoResponse.java
            │   │
            │   └───exception
            │           ErrorResponse.java
            │
            ├───exception
            │       CustomException.java
            │       ErrorCode.java
            │       GlobalExceptionHandler.java
            │
            ├───repository
            │   └───chat
            │           InMemoryRoomMemberRepository.java
            │           InMemoryRoomRepository.java
            │           RoomMemberRepository.java
            │           RoomRepository.java
            │
            ├───service
            │   └───chat
            │       ├───message
            │       │       MessageBroadcaster.java
            │       │       MessageFactory.java
            │       │       MessageService.java
            │       │
            │       └───room
            │               RoomCommandService.java
            │               RoomEntranceService.java
            │               RoomExitService.java
            │               RoomMessageService.java
            │               RoomQueryService.java
            │               RoomValidationService.java
            │
            └───util
                    ChatConstants.java
```

- Frontend 디렉터리 구조

```
src
│   App.vue
│   main.js
│   style.css
│   UserErrorMessages.js
│
├───api
│       index.js
│
├───components
│       ChatTest.vue
│       CreateRoomModal.vue
│       NicknameModal.vue
│       PasswordCheckModal.vue
│
├───router
│       index.js
│
└───views
        ChatRoom.vue
        RoomList.vue
```

