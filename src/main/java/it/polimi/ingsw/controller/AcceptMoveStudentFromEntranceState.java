package it.polimi.ingsw.controller;

import it.polimi.ingsw.messages.events.viewcontroller.*;
import it.polimi.ingsw.exceptions.NoSpaceForStudentException;
import it.polimi.ingsw.exceptions.NoSuchStudentException;
import it.polimi.ingsw.messages.events.viewcontroller.*;
import it.polimi.ingsw.messages.events.viewcontroller.*;
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
    public boolean checkValidEvent(VC_GameEvent event) {
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


    public void executeEvent(VC_GameEvent event) {
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
                    context.gameManager.moveStudentFromEntranceToIsland(player.getSchool(), color, indexIsland);
                    numberOfEvents--;
                } catch (IllegalArgumentException | NoSuchStudentException e) { /*send error*/}

                break;
            }

            case MOVE_STUDENT_FROM_ENTRANCE_TO_TABLE: {
                MoveStudentFromEntranceToTableEvent eventCast = (MoveStudentFromEntranceToTableEvent) event;
                Player player = context.getPlayerByUsername(eventCast.getPlayerName());
                Color color = eventCast.getColor();
                try {
                    context.gameManager.moveStudentFromEntranceToTable(player.getSchool(), color);
                    numberOfEvents--;
                } catch (NoSpaceForStudentException | NoSuchStudentException e) { /*send error*/}
                break;
            }

            case ACTIVATE_CHARACTER_CARD: {
                if (context.isHardMode() == false) {
                    //todo send a nack
                }

                ActivateCharacterCard eventCast = (ActivateCharacterCard) event;


            }

        }


        if (numberOfEvents == 0){

            context.changeState(new AcceptCloudTileState(context, 1));
        }

    }

}


