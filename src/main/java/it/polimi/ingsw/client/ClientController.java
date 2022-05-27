package it.polimi.ingsw.client;

import it.polimi.ingsw.client.view.UI;
import it.polimi.ingsw.controller.GamePhase;
import it.polimi.ingsw.exceptions.UnexpectedMessageException;
import it.polimi.ingsw.messages.*;
import it.polimi.ingsw.server.RoomInfo;
import javafx.print.PageLayout;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ClientController {
    private int playingPlayer = -1;
    private final UI ui;
    final NetworkHandler nh;
    private ClientModelManager cmm;
    private final String[] players = new String[4];
    private String username;
    private boolean expertGame;
    private GamePhase phase;
    private int[] assistantCardsPlayed;
    private int activeCharachterCard;

    public ClientController(UI ui, String serverIP, int serverPort) throws IOException {
        this.ui = ui;
        nh = new NetworkHandler(serverIP, serverPort, this);
        new Thread(nh).start();

    }

    public boolean login(String username) throws UnexpectedMessageException {
        try {
            return nh.login(username);
        } catch (IOException e) {
            // todo: gestire IO
            return false;
        }
    }

    void updateCModel(Message message) throws UnexpectedMessageException{
        if(! MessageType.doesUpdate(message.getMessageType()))
            throw(new UnexpectedMessageException("Did not receive a message that modifies the model"));

        // todo: finish
        switch (message.getMessageType()){

            case ADD_PLAYER -> {
                players[((AddPlayer) message).position()] = ((AddPlayer) message).username();
                ui.showMessage(message);
            }
            case START_GAME -> {
                int numberOfPlayers;
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

                synchronized (cmm){
                    ui.createGame(numberOfPlayers, expert, cmm);
                }
                System.out.println("Started game!");
            }
            case NOTIFY_CHARACTER_CARD -> {
                NotifyCharacterCard ncc = (NotifyCharacterCard) message;
                cmm.putSHInCharacterCard(ncc.card());
            }
            case PLAY_ASSISTANT_CARD -> {
                PlayAssistantCard pac = (PlayAssistantCard) message;
                if(players[pac.player()].equals(username)){
                    synchronized (cmm){
                        updateCModel(pac);
                    }
                }
                assistantCardsPlayed[pac.player()] = pac.assistantCard().getValue();
                ui.printStatus();
            }
            case ACTIVATE_CHARACTER_CARD -> {
                ActivateCharacterCard acc = (ActivateCharacterCard) message;
                if(players[acc.player()].equals(username)){
                    synchronized (cmm){
                        updateCModel(acc);
                    }
                }
                activeCharachterCard = acc.characterCardIndex();
                ui.printStatus();
            }
            case CHANGE_PHASE -> {
                ChangePhase c = (ChangePhase) message;
                phase = c.gamePhase();
                if(c.gamePhase() == GamePhase.PLANNING_PHASE)
                    assistantCardsPlayed = new int[4];
                ui.printStatus();
            }
            case CHANGE_TURN -> {
                ChangeTurn c = (ChangeTurn) message;
                playingPlayer = c.playingPlayer();
                activeCharachterCard = -1;
                ui.printStatus();
            }
            case END_GAME -> ui.showMessage(message);
            default -> {
                synchronized (cmm){
                    try{
                        cmm.updateModel(message);
                    } catch (UnexpectedMessageException e){
                        System.out.println("Did not send a model-updating message, code is bugged");
                        return;
                    }
                }
                ui.printStatus();
            }
        }
    }

    boolean myTurn(){
        return players[playingPlayer].equals(username);
    }

    void showPublicRooms(List<RoomInfo> rooms){
        ui.showPublicRooms(rooms);
    }

    void showMessage(Message message){
        ui.showMessage(message);
    }

    public GamePhase getPhase() {
        return phase;
    }

    public boolean accessRoom(int id){
        try {
            return nh.accessRoom(id);
        } catch (IOException | UnexpectedMessageException e) {
            System.out.println("There was an error in accessing the room!");
            return false;
        }
    }

    public int createRoom(CreateRoom message) {
        try {
            return nh.createRoom(message);
        } catch (IOException e) {
            System.out.println("There was an error in creating a new game!");
            return -1;
        }
    }

    public boolean logout() {
        try {
            return nh.logout();
        } catch (IOException | UnexpectedMessageException e) {
            System.out.println("There was an error logging out!");
            return false;
        }
    }

    public void getPublicRooms(GetPublicRooms message){
        try{
            nh.getPublicRooms(message);
        }catch (IOException e) {
            System.out.println("There was an error getting public rooms!");
        }
    }

    public int getPlayingPlayer() {
        return playingPlayer;
    }

    public List<String> getActions(){
        // todo: define possible actions based on phase (Or use jline)
        return new ArrayList<>();
    }

    public int[] getAssistantCardsPlayed() {
        return assistantCardsPlayed;
    }

    public int getActiveCharachterCard() {
        return activeCharachterCard;
    }

    public String[] getPlayers() {
        return players;
    }
}
