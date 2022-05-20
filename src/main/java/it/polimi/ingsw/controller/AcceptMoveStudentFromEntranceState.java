package it.polimi.ingsw.controller;

import it.polimi.ingsw.messages.GameEvent;
import it.polimi.ingsw.messages.events.ActivateCharacterCard;
import it.polimi.ingsw.messages.events.GameEventType;
import it.polimi.ingsw.messages.events.MoveStudentFromEntranceToIslandEvent;
import it.polimi.ingsw.messages.events.MoveStudentFromEntranceToTableEvent;
import it.polimi.ingsw.exceptions.NoSpaceForStudentException;
import it.polimi.ingsw.exceptions.NoSuchStudentException;
import it.polimi.ingsw.model.Color;
import it.polimi.ingsw.model.Player;

/**
 * First step of Action Phase
 * @Author Marco Mole
 */
public class AcceptMoveStudentFromEntranceState extends  GameState {


    AcceptMoveStudentFromEntranceState(RoundController context, int numberOfEvents) {
        super(context, numberOfEvents);
    }

    @Override
    public boolean checkValidEvent(GameEvent event) {
        if (event.getEventType() == GameEventType.MOVE_STUDENT_FROM_ENTRANCE_TO_ISLAND) {
            numberOfEvents--;
            return true;
        }

        if (event.getEventType() == GameEventType.MOVE_STUDENT_FROM_ENTRANCE_TO_TABLE) {
            numberOfEvents--;
            return true;
        }

        if (event.getEventType() == GameEventType.ACTIVATE_CHARACTER_CARD) {
            return true;
        }


        return false;
    }


    public void executeEvent(GameEvent event) throws Exception {
        if (!checkValidEvent(event)) {
            return;
        }



        switch (event.getEventType()) {
            case MOVE_STUDENT_FROM_ENTRANCE_TO_ISLAND: {
                MoveStudentFromEntranceToIslandEvent eventCast = (MoveStudentFromEntranceToIslandEvent) event;
                Player player = context.getPlayerByUsername(eventCast.getPlayerName());
                Color color = eventCast.getColor();
                int indexIsland = eventCast.getIndexIsland();
                try {
                    context.gameManager.moveStudentFromEntranceToIsland(player, color, indexIsland);
                    numberOfEvents--;
                } catch (IllegalArgumentException | NoSuchStudentException e) { /*send error*/}

                break;
            }

            case MOVE_STUDENT_FROM_ENTRANCE_TO_TABLE: {
                MoveStudentFromEntranceToTableEvent eventCast = (MoveStudentFromEntranceToTableEvent) event;
                Player player = context.getPlayerByUsername(eventCast.getPlayerName());
                Color color = eventCast.getColor();
                try {
                    context.gameManager.moveStudentFromEntranceToTable(player, color);
                    numberOfEvents--;
                } catch (NoSpaceForStudentException | NoSuchStudentException e) { /*send error*/}
                break;
            }

            case ACTIVATE_CHARACTER_CARD: {
                if (!context.isExpertMode()) {
                    //todo send a nack
                }

                ActivateCharacterCard eventCast = (ActivateCharacterCard) event;
                int cardId = eventCast.getCardId();

                if (!context.gameManager.isCardActive(eventCast.getCardId())){
                    throw new Exception("This card is not present in the game");
                }

                if (context.gameManager.getUsedCard() != -1){
                    throw new Exception("You already activated a card");
                }

                //todo: refactor this functions in setUsedCard
                context.gameManager.setUsedCard(cardId,context.getCurrentPlayer().getPlayerNumber());

                context.getCurrentPlayer().removeCoins(context.gameManager.findCardById(cardId).getPrice());

                context.gameManager.findCardById(cardId).increasePrice();


                if (context.gameManager.findCardById(cardId).getCharacterState(context, this) != null ) {
                    context.changeState(context.gameManager.findCardById(cardId).getCharacterState(context, this));
                }

                break;

            }

        }


        if (numberOfEvents == 0){

            context.changeState(new AcceptMotherNatureMoveState(context, 1));
            context.gameManager.getModelObserver().changePhase(GamePhase.ACTION_PHASE_TWO);
        }

    }

}


