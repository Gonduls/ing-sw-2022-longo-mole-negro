package it.polimi.ingsw.controller;

import it.polimi.ingsw.exceptions.NoSpaceForStudentException;
import it.polimi.ingsw.exceptions.NoSuchStudentException;
import it.polimi.ingsw.messages.events.GameEventType;
import it.polimi.ingsw.messages.events.SwapStudentEntranceTableEvent;
import it.polimi.ingsw.messages.GameEvent;
import it.polimi.ingsw.model.CharacterCard;
import it.polimi.ingsw.model.Player;

public class CharacterThreeState extends CharacterState{

    GameState nextState;
    CharacterCard charCard;
    public CharacterThreeState(RoundController context, int numberOfEvents, GameState nextState, CharacterCard cc) {
        super(context, numberOfEvents);
        this.nextState=nextState;
        this.charCard=cc;

    }


    @Override
    public boolean checkValidEvent(GameEvent event) {
        return event.getEventType()== GameEventType.SWAP_STUDENT_ENTRANCE_TABLE ||
                event.getEventType()== GameEventType.END_SELECTION;
    }

    @Override
    public void executeEvent(GameEvent event) throws NoSuchStudentException {

        if (event.getEventType() == GameEventType.END_SELECTION){
            context.changeState(nextState);
        } else{
            SwapStudentEntranceTableEvent eventCast = (SwapStudentEntranceTableEvent) event;
          //  Player player = context.getPlayerByUsername(eventCast.getPlayerName());
            Player player = context.getSeatedPlayers()[eventCast.getPlayerNumber()];

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
