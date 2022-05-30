package it.polimi.ingsw.messages.events;

public enum GameEventType {
    PLAY_ASSISTANT_CARD, //planning phase
    MOVE_STUDENT_FROM_ENTRANCE_TO_ISLAND, //actionPhase
    MOVE_STUDENT_FROM_ENTRANCE_TO_TABLE,  //actionPhase
    MOVE_MOTHER_NATURE, //actionPhase
    CHOOSE_CLOUD_TILE, //actionPhase
    ACTIVATE_CHARACTER_CARD, //actionPhase
    MOVE_STUDENT_FROM_CARD_TO_ISLAND, //CardRelated
    MOVE_STUDENT_FROM_CARD_TO_TABLE,  //CardRelated
    MOVE_STUDENT_FROM_ENTRANCE_TO_CARD, //CardRelated -> prolly useless
    CHOOSE_COLOR, //CardRelated
    MOVE_STUDENT_FROM_ENTRANCE_TO_DINING_ROOM_CARD, //CardRelated -> prolly useless
    MOVE_STUDENT_FROM_DINING_ROOM_TO_ENTRANCE, //CardRelated -> prolly useless
    CHOOSE_ISLAND, //CardRelated
    SWAP_STUDENT_CARD_ENTRANCE, //CardRelated
    SWAP_STUDENT_ENTRANCE_TABLE,
    END_SELECTION//CardRelated
}
