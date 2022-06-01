package it.polimi.ingsw.controller;

import it.polimi.ingsw.messages.events.ChooseColorEvent;
import it.polimi.ingsw.messages.events.ChooseIslandEvent;
import it.polimi.ingsw.messages.events.GameEventType;
import it.polimi.ingsw.messages.GameEvent;
import it.polimi.ingsw.exceptions.NoSpaceForStudentException;
import it.polimi.ingsw.exceptions.NoSuchStudentException;
import it.polimi.ingsw.messages.events.MoveStudentFromCardToIslandEvent;
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
        super(context, numberOfEvents, nextState,cc);
        this.nextState = nextState;
        color = null;
        islandIndex = -1;
        this.cc =cc;
    }

    @Override
    public boolean checkValidEvent(GameEvent event) {
        return event.getEventType() == GameEventType.MOVE_STUDENT_FROM_CARD_TO_ISLAND;
    }
//TODO CHECKS THAT THIS WORKS AND THE REMOVE LEGACY CODE
    @Override
    public void executeEvent(GameEvent event) throws Exception {
        switch( event.getEventType()){

            //to remove
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
            //to remove
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

            // only keep this
            case MOVE_STUDENT_FROM_CARD_TO_ISLAND: {
                MoveStudentFromCardToIslandEvent eventCast = (MoveStudentFromCardToIslandEvent) event;
                this.color=eventCast.getStudentFromCard();
                this.islandIndex=eventCast.getIslandIndex();

                if(color == null || (islandIndex<0 || islandIndex > context.gameManager.getIslands().size() )){
                    throw new Exception("wrong parameters");
                }

                try {
                    cc.getStudentHolder().moveStudentTo(color, context.gameManager.getIslands().get(islandIndex));
                    Color newColor =  context.gameManager.getBag().extractRandomStudent();
                    cc.getStudentHolder().addStudent(newColor);
                    context.gameManager.getModelObserver().moveStudentFromCardToIsland(islandIndex, color);
                    context.gameManager.getModelObserver().addStudentToCard(0, newColor);

                } catch (NoSuchStudentException e) {
                    //send a nack, maybe a should do it in the switch case block
                } catch(NoSpaceForStudentException ignored) {/*impossible*/}

                context.changeState(nextState);



            }

        }

        if(numberOfEvents==0){
            try {
                cc.getStudentHolder().moveStudentTo(color, context.gameManager.getIslands().get(islandIndex));
                Color newColor =  context.gameManager.getBag().extractRandomStudent();
                cc.getStudentHolder().addStudent(newColor);
                context.gameManager.getModelObserver().moveStudentFromCardToIsland(islandIndex, color);
                context.gameManager.getModelObserver().addStudentToCard(0, newColor);

            } catch (NoSuchStudentException e) {
                //send a nack, maybe a should do it in the switch case block
            } catch(NoSpaceForStudentException ignored) {/*impossible*/}

            context.changeState(nextState);
        }
    }
}
