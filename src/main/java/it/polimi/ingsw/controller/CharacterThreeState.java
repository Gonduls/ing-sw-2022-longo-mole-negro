package it.polimi.ingsw.controller;

import it.polimi.ingsw.exceptions.NoSpaceForStudentException;
import it.polimi.ingsw.exceptions.NoSuchStudentException;
import it.polimi.ingsw.messages.events.viewcontroller.GameEventType;
import it.polimi.ingsw.messages.events.viewcontroller.SwapStudentEntranceTableEvent;
import it.polimi.ingsw.messages.events.viewcontroller.VC_GameEvent;
import it.polimi.ingsw.model.CharacterCard;
import it.polimi.ingsw.model.Player;

public class CharacterThreeState extends CharacterState{

    GameState nextState;
    public CharacterThreeState(RoundController context, int numberOfEvents, GameState nextState, CharacterCard cc) {
        super(context, numberOfEvents, nextState, cc);
        this.nextState=nextState;

    }


    @Override
    public boolean checkValidEvent(VC_GameEvent event) {
        return event.getEventType()== GameEventType.SWAP_STUDENT_ENTRANCE_TABLE ||
                event.getEventType()== GameEventType.END_SELECTION;
    }

    @Override
    public void executeEvent(VC_GameEvent event) throws NoSuchStudentException {

        if (event.getEventType() == GameEventType.END_SELECTION){
            context.changeState(nextState);
        } else{
            SwapStudentEntranceTableEvent eventCast = (SwapStudentEntranceTableEvent) event;
            Player player = context.getPlayerByUsername(eventCast.getPlayerName());

            if (player.getSchool().getStudentsAtEntrance().getStudentByColor(eventCast.getStudentFromEntrance()) ==0)
                throw new NoSuchStudentException("the player doesn't have this student in the entrance");

            if (player.getSchool().getStudentsAtTables().getStudentByColor(eventCast.getStudentFromTable()) ==0)
                throw new NoSuchStudentException("the player doesn't have this student in the table");

            try{
                player.getSchool().getStudentsAtEntrance().moveStudentTo(eventCast.getStudentFromEntrance(), player.getSchool().getStudentsAtTables());
                player.getSchool().getStudentsAtTables().moveStudentTo(eventCast.getStudentFromEntrance(), player.getSchool().getStudentsAtEntrance());
                numberOfEvents--;
            }catch (NoSpaceForStudentException ignored) {}


            if (numberOfEvents == 0){
                context.changeState(nextState);
            }


        }

    }
}
