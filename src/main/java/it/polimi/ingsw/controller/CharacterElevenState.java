package it.polimi.ingsw.controller;

import it.polimi.ingsw.exceptions.NoSpaceForStudentException;
import it.polimi.ingsw.exceptions.NoSuchStudentException;
import it.polimi.ingsw.messages.events.ChooseColorEvent;
import it.polimi.ingsw.messages.events.GameEventType;
import it.polimi.ingsw.messages.GameEvent;
import it.polimi.ingsw.model.CharacterCardEleven;
import it.polimi.ingsw.model.Player;

public class CharacterElevenState extends CharacterState{
    GameState nextState;
    CharacterCardEleven cc;
    public CharacterElevenState(RoundController context, int numberOfEvents, GameState nextState, CharacterCardEleven cc){
        super(context, numberOfEvents);
        this.nextState=nextState;
        this.cc=cc;
    }

    @Override
    public boolean checkValidEvent(GameEvent event) {
        return event.getEventType() == GameEventType.CHOOSE_COLOR;
    }


    @Override
    public void executeEvent(GameEvent event) throws NoSuchStudentException {
        ChooseColorEvent eventCast = (ChooseColorEvent) event;
        for(Player p: context.getSeatedPlayers()){
            for (int i =0; i<3;i++){

                try {
                    p.getSchool().getStudentsAtTables().moveStudentTo(eventCast.getColor(),context.gameManager.getBag());
                } catch (NoSpaceForStudentException ignored) {}
            }
        }

        numberOfEvents--;
        context.changeState(nextState);
    }
}
