package it.polimi.ingsw.controller;

import it.polimi.ingsw.exceptions.NoSpaceForStudentException;
import it.polimi.ingsw.exceptions.NoSuchStudentException;
import it.polimi.ingsw.messages.events.GameEventType;
import it.polimi.ingsw.messages.events.MoveStudentFromCardToTableEvent;
import it.polimi.ingsw.messages.GameEvent;
import it.polimi.ingsw.messages.events.PlayAssistantCardEvent;
import it.polimi.ingsw.model.CharacterCard;
import it.polimi.ingsw.model.Color;
import it.polimi.ingsw.model.Player;

public class CharacterSevenState extends CharacterState{


    CharacterCard cc;
    GameState nextState;

    public CharacterSevenState (RoundController context, int numberOfEvents, GameState nextState, CharacterCard cc){
        super(context,numberOfEvents);
        this.cc = cc;
        this.nextState = nextState;

    }


    @Override
    public boolean checkValidEvent(GameEvent event) {
        return event.getEventType() == GameEventType.MOVE_STUDENT_FROM_CARD_TO_TABLE;
    }

    @Override
    public void executeEvent(GameEvent event) throws NoSuchStudentException {
        MoveStudentFromCardToTableEvent eventCast = (MoveStudentFromCardToTableEvent) event;
        Color color = eventCast.getColor();
        Player player = context.getPlayerByUsername(eventCast.getPlayerName());
        try {
            cc.getStudentHolder().moveStudentTo(color, player.getSchool().getStudentsAtTables());
            Color newColor = context.gameManager.getBag().extractRandomStudent();
            cc.getStudentHolder().addStudent(newColor);
            context.gameManager.getModelObserver().moveStudentFromCardToTable(7, player.getPlayerNumber(), color);
            context.gameManager.getModelObserver().addStudentToCard(7,newColor);

            numberOfEvents--;
        } catch(NoSpaceForStudentException ignored){}

        if (numberOfEvents==0){

            context.changeState(nextState);
        }

    }

}
