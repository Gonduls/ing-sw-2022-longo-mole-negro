package it.polimi.ingsw.messages;

/**
 * Specifies all possible message types
 */
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
    NOTIFY_CHARACTER_CARD,
    MOVE_STUDENT,
    ADD_STUDENT_TO,
    MOVE_TOWERS,
    MERGE_ISLANDS,
    NO_ENTRY,
    SET_PROFESSOR_TO,
    MOVE_MOTHER_NATURE,
    PLAY_ASSISTANT_CARD,
    ACTIVATE_CHARACTER_CARD,
    ADD_COIN,
    CHANGE_PHASE,
    CHANGE_TURN,
    GAME_EVENT,
    END_GAME,
    LEAVE_ROOM,
    PLAYER_DISCONNECT;

    /**
     * @param type The type of message to check
     * @return True if message can be sent form the server, false otherwise
     */
    public static boolean isServerMessage(MessageType type){
        return (type != MessageType.LOGIN &&
                type != MessageType.LOGOUT &&
                type != MessageType.GET_PUBLIC_ROOMS &&
                type != MessageType.ACCESS_ROOM &&
                type != MessageType.CREATE_ROOM &&
                type != MessageType.GAME_EVENT &&
                type != MessageType.LEAVE_ROOM);
    }

    /**
     * @param type The type of message to check
     * @return True if message can be sent form the client, false otherwise
     */
    public static boolean isClientMessage(MessageType type){
        return (!isServerMessage(type));
    }

    /**
     * @param type The type of message to check
     * @return True if message can update the client model, false otherwise
     */
    public static boolean doesUpdate(MessageType type){
        return (type != MessageType.ACK &&
                type != MessageType.NACK &&
                type != MessageType.ROOM_ID &&
                type != MessageType.PLAYER_DISCONNECT &&
                type != MessageType.PUBLIC_ROOMS &&
                isServerMessage(type));
    }
}
