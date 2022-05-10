package it.polimi.ingsw.controller;

import it.polimi.ingsw.exceptions.NoSpaceForStudentException;
import it.polimi.ingsw.exceptions.NoSuchStudentException;
import it.polimi.ingsw.messages.events.viewcontroller.ChooseColorEvent;
import it.polimi.ingsw.messages.events.viewcontroller.GameEventType;
import it.polimi.ingsw.messages.events.viewcontroller.VC_GameEvent;
import it.polimi.ingsw.model.CharacterCard;
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
    public boolean checkValidEvent(VC_GameEvent event) {
        return event.getEventType() == GameEventType.CHOOSE_COLOR;
    }


    @Override
    public void executeEvent(VC_GameEvent event) throws NoSuchStudentException {
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
