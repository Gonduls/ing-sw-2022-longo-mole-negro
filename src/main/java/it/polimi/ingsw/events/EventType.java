package it.polimi.ingsw.events;

public enum EventType {
    PLAY_ASSISTANT_CARD, //planning phase
    MOVE_STUDENT_FROM_ENTRANCE_TO_ISLAND, //actionPhase
    MOVE_STUDENT_FROM_ENTRANCE_TO_DINING_ROOM,  //actionPhase
    MOVE_MOTHER_NATURE, //actionPhase
    CHOOSE_CLOUD_TILE, //actionPhase
    ACTIVATE_CHARACTER_CARD, //actionPhase
    MOVE_STUDENT_FROM_CARD_TO_ISLAND, //CardRelated
    MOVE_STUDENT_FROM_CARD_TO_ENTRANCE,  //CardRelated
    MOVE_STUDENT_FROM_ENTRANCE_TO_CARD, //CardRelated
    CHOOSE_COLOR, //CardRelated
    MOVE_STUDENT_FROM_ENTRANCE_TO_DINING_ROOM_CARD, //CardRelated
    MOVE_STUDENT_FROM_DINING_ROMM_TO_ENTRANCE, //CardRelated
}
