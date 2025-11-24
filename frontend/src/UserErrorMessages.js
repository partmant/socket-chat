export const USER_ERROR_MESSAGES = {
    // 디폴트 
    DEFAULT: {
        message: "요청을 처리하지 못했습니다. 잠시 후 다시 시도해주세요.",
        status: 500
    },

    // 방 제목 (Room Title)
    TITLE_NULL: {
        message: "방 제목을 입력해주세요.",
        status: 400,
    },
    TITLE_BLANK: {
        message: "방 제목은 비어 있을 수 없습니다.",
        status: 400,
    },
    TITLE_LENGTH_EXCEEDED: {
        message: "방 제목은 50자 이내여야 합니다.",
        status: 400,
    },
    TITLE_DUPLICATION: {
        message: "이미 존재하는 방 제목입니다.",
        status: 409,
    },

    // 비밀번호 (Password)
    PASSWORD_LENGTH_EXCEEDED: {
        message: "비밀번호 길이는 4~10자 사이여야 합니다.",
        status: 400,
    },
    PASSWORD_INCORRECT: {
        message: "비밀번호가 올바르지 않습니다.",
        status: 401,
    },
    PASSWORD_BLANK: {
        message: "비밀번호를 입력해주세요.",
        status: 400,
    },

    // 정원 (Capacity)
    CAPACITY_EXCEEDED: {
        message: "설정 가능한 최대 인원 수를 초과했습니다.",
        status: 400,
    },
    CAPACITY_NULL: {
        message: "최대 인원 수를 선택해주세요.",
        status: 400,
    },
    ROOM_FULL: {
        message: "방 인원이 가득 찼습니다.",
        status: 403,
    },

    // 닉네임 (Nickname)
    NICKNAME_BLANK: {
        message: "닉네임을 입력해주세요.",
        status: 400,
    },
    NICKNAME_NULL: {
        message: "닉네임을 입력해주세요.",
        status: 400,
    },
    NICKNAME_LENGTH_EXCEED: {
        message: "닉네임은 16자 이내여야 합니다.",
        status: 400,
    },
    NICKNAME_DUPLICATION: {
        message: "이미 사용 중인 닉네임입니다.",
        status: 409,
    },
    NICKNAME_NOT_FOUND: {
        message: "해당 닉네임의 사용자를 찾을 수 없습니다.",
        status: 404,
    },

    // 방 (Room)
    ROOM_NOT_FOUND: {
        message: "해당 방을 찾을 수 없습니다.",
        status: 404,
    },

    // 메시지 (Message)
    MESSAGE_LENGTH_EXCEEDED: {
        message: "메시지의 길이는 200자 이내여야 합니다.",
        status: 400,
    },
    MESSAGE_CONTENT_EMPTY: {
        message: "메시지를 입력해주세요.",
        status: 400,
    },
};