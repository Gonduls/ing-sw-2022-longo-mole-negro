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
                StartGame s = (StartGame) message;
                if(players[3] == null){
                    cmm = new ClientModelManager(new String[]{players[0], players[1]}, s.getIndexes() != null);
                }else if(players[4] == null){
                    cmm = new ClientModelManager(new String[]{players[0], players[1], players[2]}, s.getIndexes() != null);
                } else {
                    cmm = new ClientModelManager(players.clone(), s.getIndexes() != null);
                }

                cmm.putSHInCharacterCard(s.getIndexes()[0], s.getIndexes()[1], s.getIndexes()[2]);
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
                cmm.updateModel(message);
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
