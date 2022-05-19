package it.polimi.ingsw.client;

import it.polimi.ingsw.client.view.UI;
import it.polimi.ingsw.exceptions.UnexpectedMessageException;
import it.polimi.ingsw.messages.*;
import it.polimi.ingsw.server.RoomInfo;

import java.io.IOException;
import java.util.List;

public class ClientController {
    private boolean turn = true;
    private final UI ui;
    final NetworkHandler nh;
    private ClientModelManager cmm;
    private String[] players = new String[4];
    private final String username;

    public ClientController(UI ui, String serverIP, int serverPort) throws IOException {
        this.ui = ui;
        nh = new NetworkHandler(serverIP, serverPort, this);
        //TODO: login
        username = "";
    }

    void updateCModel(Message message) throws UnexpectedMessageException{
        if(! MessageType.doesUpdate(message.getMessageType()))
            throw(new UnexpectedMessageException("Did not receive a message that modifies the model"));

        // todo: finish
        switch (message.getMessageType()){

            case ADD_PLAYER -> {
                players[((AddPlayer) message).position()] = ((AddPlayer) message).username();
                break;
            }
            case START_GAME -> {
                int numberOfPlayers = 0;
                StartGame s = (StartGame) message;
                boolean expert = s.getIndexes() != null;

                if(players[2] == null){
                    cmm = new ClientModelManager(new String[]{players[0], players[1]}, expert);
                    numberOfPlayers = 2;
                }else if(players[3] == null){
                    cmm = new ClientModelManager(new String[]{players[0], players[1], players[2]}, expert);
                    numberOfPlayers = 3;
                } else {
                    cmm = new ClientModelManager(players.clone(), expert);
                    numberOfPlayers = 4;
                }

                if(expert)
                    cmm.putSHInCharacterCard(s.getIndexes()[0], s.getIndexes()[1], s.getIndexes()[2]);

                synchronized (cmm){
                    ui.createGame(numberOfPlayers, expert, cmm);
                }
                break;
            }
            case CHANGE_PHASE -> {
                ChangePhase c = (ChangePhase) message;


                break;
            }
            case CHANGE_TURN -> {
                ChangeTurn c = (ChangeTurn) message;
                if(players[c.playingPlayer()].equals(username))
                    turn = true;
                else
                    turn = false;
                ui.printStatus();
                break;
            }
            case END_GAME -> {
                break;
            }
            default -> {
                synchronized (cmm){
                    cmm.updateModel(message);
                }
                ui.printStatus();
            }
        }
    }

    boolean myTurn(){
        return turn;
    }

    void showPublicRooms(List<RoomInfo> rooms){
        ui.showPublicRooms(rooms);
    }

    void showMessage(Message message){
        ui.showMessage(message);
    }
}
