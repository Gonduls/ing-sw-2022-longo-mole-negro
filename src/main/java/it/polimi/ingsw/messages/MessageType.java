package it.polimi.ingsw.messages;

public enum MessageType {
    LOGIN,
    LOGOUT,
    ACK,
    NACK,
    GET_PUBLIC_ROOMS,
    ROOM_ID,
    CREATE_ROOM,
    PUBLIC_ROOMS,
    ACCESS_ROOM,
    ADD_PLAYER,
    START_GAME,
    MOVE_STUDENT,
    ADD_STUDENT_TO,
    MOVE_TOWER,
    MERGE_ISLANDS,
    SET_PROFESSOR_TO,
    MOVE_MOTHER_NATURE,
    PLAY_ASSISTANT_CARD,
    ACTIVATE_CHARACTER_CARD,
    ADD_COIN,
    PAY_PRICE,
    CHANGE_PHASE,
    CHANGE_TURN,
    GAME_EVENT,
    END_GAME,
    LEAVE_ROOM,
    PLAYER_DISCONNECT;

    public static boolean isServerMessage(MessageType type){
        return (type != MessageType.LOGIN &&
                type != MessageType.LOGOUT &&
                type != MessageType.GET_PUBLIC_ROOMS &&
                type != MessageType.ACCESS_ROOM &&
                type != MessageType.CREATE_ROOM &&
                type != MessageType.GAME_EVENT &&
                type != MessageType.LEAVE_ROOM);
    }

    public static boolean isClientMessage(MessageType type){
        return (!isServerMessage(type));
    }

    public static boolean doesUpdate(MessageType type){
        return (type != MessageType.ACK &&
                type != MessageType.NACK &&
                type != MessageType.ROOM_ID &&
                type != MessageType.PLAYER_DISCONNECT &&
                type != MessageType.PUBLIC_ROOMS &&
                isServerMessage(type));
    }
}
