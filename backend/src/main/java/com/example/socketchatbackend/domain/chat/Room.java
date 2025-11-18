package com.example.socketchatbackend.domain.chat;

import com.example.socketchatbackend.domain.chat.vo.RoomCapacity;
import com.example.socketchatbackend.domain.chat.vo.RoomPassword;
import com.example.socketchatbackend.domain.chat.vo.RoomTitle;

import static com.example.socketchatbackend.exception.chat.ErrorMessages.*;

public record Room(Long id, RoomTitle title, RoomPassword password, RoomCapacity capacity) {

    public Room {
        if (title == null) {
            throw new IllegalArgumentException(TITLE_NULL.getMessage());
        }
        if (password == null) {
            throw new IllegalArgumentException(PASSWORD_NULL.getMessage());
        }
        if (capacity == null) {
            throw new IllegalArgumentException(CAPACITY_NULL.getMessage());
        }
    }

    public static Room of(RoomTitle title, RoomPassword password, RoomCapacity capacity) {
        return new Room(null, title, password, capacity);
    }

    public Room withId(Long newId) {
        return new Room(newId, this.title, this.password, this.capacity);
    }

    public void verifyPassword(String input) {
        if (!password.exists()) {   // 비밀번호 없는 방이면 입장
            return;
        }

        if (!password.matches(input)) {
            throw new IllegalArgumentException(PASSWORD_INCORRECT.getMessage());
        }
    }

    public boolean isFull(int currentUserCount) {
        return capacity.isFull(currentUserCount);
    }

    public boolean canAccept(int currentUserCount) {
        return capacity.canAccept(currentUserCount);
    }

    public boolean hasPassword() {
        return password.exists();
    }
}
