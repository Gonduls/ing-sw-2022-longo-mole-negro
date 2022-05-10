package it.polimi.ingsw.controller;

import it.polimi.ingsw.exceptions.NoSpaceForStudentException;
import it.polimi.ingsw.exceptions.NoSuchStudentException;
import it.polimi.ingsw.messages.events.GameEventType;
import it.polimi.ingsw.messages.events.MoveStudentFromCardToTableEvent;
import it.polimi.ingsw.messages.GameEvent;
import it.polimi.ingsw.model.CharacterCard;
import it.polimi.ingsw.model.Color;

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
        try {
            cc.getStudentHolder().moveStudentTo(color, context.getPlayerByUsername(eventCast.getPlayerName()).getSchool().getStudentsAtTables());
            cc.getStudentHolder().addStudent(context.gameManager.getBag().extractRandomStudent());
            numberOfEvents--;
        } catch(NoSpaceForStudentException ignored){}

        if (numberOfEvents==0){

            context.changeState(nextState);
        }

    }

}
