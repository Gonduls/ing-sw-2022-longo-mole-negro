package it.polimi.ingsw.controller;

import it.polimi.ingsw.messages.GameEvent;
import it.polimi.ingsw.messages.events.PlayAssistantCardEvent;
import it.polimi.ingsw.model.AssistantCard;
import it.polimi.ingsw.server.DummyRoom;
import it.polimi.ingsw.server.Room;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.security.spec.ECParameterSpec;

import static org.junit.jupiter.api.Assertions.*;

class RoundControllerTest {
    Room dummyRoom;
    String[] playerNames;

    @BeforeEach
    void init(){
        dummyRoom = new DummyRoom(0,2,true);
        playerNames = new String[]{"Albano", "Romina"};
    }

    @Test
    void testConstructor(){
         RoundController controller = new RoundController(playerNames, true, dummyRoom);

         System.out.println("the current player for the controller: " + controller.getCurrentPlayer().getUsername());
        try {
            controller.handleEvent(new PlayAssistantCardEvent(controller.getCurrentPlayer().getUsername(), AssistantCard.FOUR));
        } catch (Exception e) {
            System.out.println(controller.getCurrentPlayer().getUsername());
            System.out.println(e.getMessage());
            e.printStackTrace();
        }

        try{
            controller.handleEvent(new PlayAssistantCardEvent(controller.getCurrentPlayer().getUsername(), AssistantCard.TWO));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

}