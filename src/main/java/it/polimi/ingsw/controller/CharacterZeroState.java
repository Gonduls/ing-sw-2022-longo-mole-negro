package it.polimi.ingsw.controller;

import it.polimi.ingsw.messages.events.viewcontroller.ChooseColorEvent;
import it.polimi.ingsw.messages.events.viewcontroller.ChooseIslandEvent;
import it.polimi.ingsw.messages.events.viewcontroller.GameEventType;
import it.polimi.ingsw.messages.events.viewcontroller.VC_GameEvent;
import it.polimi.ingsw.exceptions.NoSpaceForStudentException;
import it.polimi.ingsw.exceptions.NoSuchStudentException;
import it.polimi.ingsw.model.CharacterCard;
import it.polimi.ingsw.model.Color;

/**
 *
 * è una carta con 4 studenti sopra.
 * quando applico l’effetto sposto uno dei suoi studenti su una isola a mia scelta.
 * pesca un nuovo studente per la carta
 *
 */

public class CharacterZeroState extends CharacterState {

    Color color;
    int islandIndex;

    CharacterCard cc;

    GameState nextState;

    public CharacterZeroState(RoundController context, int numberOfEvents, GameState nextState, CharacterCard cc) {
        super(context, numberOfEvents, nextState);
        //this.nextState = nextState;
        color = null;
        islandIndex = -1;
        this.cc =cc;
    }

    @Override
    public boolean checkValidEvent(VC_GameEvent event) {
        return event.getEventType() == GameEventType.CHOOSE_COLOR || event.getEventType() == GameEventType.CHOOSE_ISLAND;
    }

    @Override
    public void executeEvent(VC_GameEvent event) {
        switch( event.getEventType()){

            case CHOOSE_COLOR: {
                if (color != null) {
                    return;/*throw an error*/
                }
                ChooseColorEvent eventCast = (ChooseColorEvent) event;
                color = eventCast.getColor();

                if (cc.getStudentHolder().getStudentByColor(color)>0) {
                    numberOfEvents--;
                }
                else {

                    //todo send a nack
                }

                break;
            }

            case CHOOSE_ISLAND:{
                if(islandIndex> 0) {return; /*throw an error*/}
                ChooseIslandEvent eventCast = (ChooseIslandEvent) event;
                islandIndex = eventCast.getIslandIndex();
                if(islandIndex > context.gameManager.getIslands().size()) {
                    numberOfEvents--;
                } else {
                    //todo send a nack
                }

                break;
            }

        }

        if(numberOfEvents==0){
            try {
                cc.getStudentHolder().moveStudentTo(color, context.gameManager.getIslands().get(islandIndex));
                cc.getStudentHolder().addStudent(context.gameManager.getBag().extractRandomStudent());
            } catch (NoSuchStudentException e) {
                //send a nack, maybe a should do it in the switch case block
            } catch(NoSpaceForStudentException ignored) {/*impossibile*/}

            context.changeState(nextState);
        }
    }
}
