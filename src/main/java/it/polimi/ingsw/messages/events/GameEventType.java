package it.polimi.ingsw.messages.events;

/**
 * Defines all possible game events that a player can create
 */
public enum GameEventType {
    PLAY_ASSISTANT_CARD, //planning phase
    MOVE_STUDENT_FROM_ENTRANCE_TO_ISLAND, //actionPhase
    MOVE_STUDENT_FROM_ENTRANCE_TO_TABLE,  //actionPhase
    MOVE_MOTHER_NATURE, //actionPhase
    CHOOSE_CLOUD, //actionPhase
    ACTIVATE_CHARACTER_CARD, //actionPhase
    MOVE_STUDENT_FROM_CARD_TO_ISLAND, //CardRelated
    MOVE_STUDENT_FROM_CARD_TO_TABLE,  //CardRelated
    CHOOSE_COLOR, //CardRelated
    CHOOSE_ISLAND, //CardRelated
    SWAP_STUDENT_CARD_ENTRANCE, //CardRelated
    SWAP_STUDENT_ENTRANCE_TABLE,
    END_SELECTION//CardRelated
}
