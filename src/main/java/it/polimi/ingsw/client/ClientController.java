package it.polimi.ingsw.client;

import it.polimi.ingsw.Log;
import it.polimi.ingsw.client.view.UI;
import it.polimi.ingsw.controller.GamePhase;
import it.polimi.ingsw.exceptions.UnexpectedMessageException;
import it.polimi.ingsw.messages.*;
import it.polimi.ingsw.messages.events.ActivateCharacterCardEvent;
import it.polimi.ingsw.messages.events.GameEventType;
import it.polimi.ingsw.server.RoomInfo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Client side controller
 */
public class ClientController {
    // general control related
    private String username;
    final NetworkHandler nh;
    private ClientModelManager cmm;
    private final UI ui;

    // single-game related
    private int playingPlayer = -1;
    private String[] players = new String[4];
    private GamePhase phase = GamePhase.PLANNING_PHASE;
    private int[] assistantCardsPlayed = new int[]{-1, -1, -1, -1};
    private int activeCharacterCard;
    private boolean expert;
    private int cardActions;


    /**
     * Sets ui, creates and starts NetworkHandler thread
     * @param ui The UI previously created
     * @param serverIP The IP address of the server, localhost if empty
     * @param serverPort The port needed for TCP/IP connection with the server
     * @throws IOException if no connection or input/output stream can be created with the server
     */
    public ClientController(UI ui, String serverIP, int serverPort) throws IOException {
        this.ui = ui;
        nh = new NetworkHandler(serverIP, serverPort, this);
        new Thread(nh).start();
    }

    /**
     * Updates model or sets parameters according to passed message
     * @param message The message that aims to update the model or to set parameters
     * @throws UnexpectedMessageException If the message is not a message that updates the model or sets parameters
     */
    void updateCModel(Message message) throws UnexpectedMessageException{
        if(! MessageType.doesUpdate(message.getMessageType()))
            throw(new UnexpectedMessageException("Did not receive a message that modifies the model"));

        boolean updates = false;
        switch (message.getMessageType()){

            case ADD_PLAYER -> {
                players[((AddPlayer) message).position()] = ((AddPlayer) message).username();
                ui.showMessage(message);
            }
            case START_GAME -> {
                int numberOfPlayers;
                StartGame s = (StartGame) message;
                expert = s.expert();

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
            }
            case NOTIFY_CHARACTER_CARD -> {
                NotifyCharacterCard ncc = (NotifyCharacterCard) message;
                cmm.insertCharacterCard(ncc.card());
            }
            case MERGE_ISLANDS -> {
                updates = true;
                ui.merge(((MergeIslands) message).secondIslandIndex());
            }
            case PLAY_ASSISTANT_CARD -> {
                PlayAssistantCard pac = (PlayAssistantCard) message;
                if(players[pac.player()].equals(username))
                    updates = true;

                assistantCardsPlayed[pac.player()] = pac.assistantCard().getValue();
                ui.refresh();
            }
            case ACTIVATE_CHARACTER_CARD -> {
                ActivateCharacterCard acc = (ActivateCharacterCard) message;
                updates = true;

                activeCharacterCard = acc.characterCardIndex();
                ui.refresh();
            }
            case CHANGE_PHASE -> {
                ChangePhase c = (ChangePhase) message;
                phase = c.gamePhase();
                if(c.gamePhase() == GamePhase.PLANNING_PHASE)
                    assistantCardsPlayed = new int[]{-1, -1, -1, -1};
                ui.refresh();
            }
            case CHANGE_TURN -> {
                ChangeTurn c = (ChangeTurn) message;
                playingPlayer = c.playingPlayer();
                activeCharacterCard = -1;
                ui.refresh();
                cardActions = 0;
            }
            case END_GAME -> ui.showMessage(message);
            default -> {
                updates = true;
                ui.refresh();
            }
        }

        if(updates) {
            synchronized (cmm) {
                cmm.updateModel(message);
            }
            ui.refresh();
        }
    }

    /**
     * Calls UI showPublicRooms
     * @param rooms the info to be showed
     */
    void showPublicRooms(List<RoomInfo> rooms){
        ui.showPublicRooms(rooms);
    }

    /**
     * Calls UI ShowMessage
     * @param message The message to be shown
     */
    void showMessage(Message message){
        ui.showMessage(message);
    }

    /**
     * @return The current phase
     */
    public GamePhase getPhase() {
        return phase;
    }

    /**
     * Calls NetworkHandler login function passing username
     * @param username The username passed
     * @return True if login was successful, false otherwise
     * @throws UnexpectedMessageException If a message different from ACK or NACK was responded
     * @throws IOException If the input/output streams are not correctly set up
     */
    public boolean login(String username) throws UnexpectedMessageException, IOException {
        if(nh.login(username)){
            this.username = username;
            return true;
        }
        return false;
    }

    /**
     * Calls NetworkHandler accessRoom function passing username
     * @param roomId The roomId to be passed
     * @return true if the room was accessed successfully, false otherwise
     */
    public boolean accessRoom(int roomId){
        try {
            return nh.accessRoom(roomId);
        } catch (IOException | UnexpectedMessageException e) {
            ui.showMessage(new Nack("There was an error in accessing the room!"));
            return false;
        }
    }

    /**
     * Calls NetworkHandler createRoom function passing "message"
     * @param message The CreateRoom message to be passed
     * @return the roomId of the room that was created
     */
    public int createRoom(CreateRoom message) {
        try {
            return nh.createRoom(message);
        } catch (IOException e) {
            System.out.println("There was an error in creating a new game!");
            return -1;
        }
    }

    /**
     * Calls NetworkHandler logout function
     * @return True if logout was successful, false otherwise
     */
    public boolean logout() {
        try {
            return nh.logout();
        } catch (IOException | UnexpectedMessageException e) {
            System.out.println("There was an error logging out!");
            return false;
        }
    }

    /**
     * Calls NetworkHandler leaveRoom function
     * @return True if logout was successful, false otherwise
     */
    public boolean leaveRoom() {
        try {
            return nh.leaveRoom();
        } catch (IOException | UnexpectedMessageException e) {
            System.out.println("There was an error leaving room!");
            return false;
        }
    }


    /**
     * Calls NetworkHandler getPublicRooms function passing "message"
     * @param message The GetPublicRooms message to be passed
     */
    public void getPublicRooms(GetPublicRooms message){
        try{
            nh.getPublicRooms(message);
        }catch (IOException e) {
            System.out.println("There was an error getting public rooms!");
        }
    }

    /**
     * Calls NetworkHandler performEvent function passing "event";
     * if the event activated a Character card, it sets the number of actions needed to fulfill the card activation,
     * else if this number of actions is greater than 0, then it gradually reduces it back to 0
     * @param event The event to be performed
     * @return The response sent from the server (Ack / Nack)
     */
    public Message performEvent(GameEvent event){
        Message answer = new Nack("Could not get a proper answer from server");
        try{
            answer = nh.performEvent(event);
        }catch (UnexpectedMessageException | IOException e){
            Log.logger.severe(e.getMessage());
            Log.logger.severe(e.getLocalizedMessage());
        }

        if(event.getEventType() == GameEventType.ACTIVATE_CHARACTER_CARD){
            int cardId = ((ActivateCharacterCardEvent) event).getCardId();
            switch (cardId){
                case 0, 5, 7, 8, 10, 11 -> cardActions = 1;
                case 2 -> cardActions = 3;
                case 3 -> cardActions = 2;
                default -> cardActions = 0;
            }
        } else if(cardActions > 0){
            cardActions --;
            if(event.getEventType() == GameEventType.END_SELECTION)
                cardActions = 0;
        }
        return answer;
    }

    /**
     * @return true if it is the turn of the player controlled
     */
    public boolean myTurn(){
        if(playingPlayer == -1)
            return false;
        return players[playingPlayer].equals(username);
    }

    /**
     * @return the name of the currently playing player
     */
    public int getPlayingPlayer() {
        return playingPlayer;
    }

    /**
     * @return A list of string representing tha actions according to what the server expects at any given point
     */
    public List<String> getActions(){
        List<String> actions = new ArrayList<>();
        if(!myTurn()){
            if(expert)
                actions.add(0 + ") Display card # effect");
            else
                actions.add("No possible actions");
            return actions;
        }

        if(cardActions > 0){
            switch (activeCharacterCard){
                case 0 -> actions.add(0 + ") Move student X from CC to I #");
                case 2 -> {
                    actions.add(0 + ") Swap X from CC with Y from E");
                    actions.add(1 + ") End selections");
                }
                case 3 -> {
                    actions.add(0 + ") Swap X from E with Y from DR");
                    actions.add(1 + ") End selections");
                }
                case 5 -> actions.add(0 + ") Choose I # to place a NoEntry");
                case 7 -> actions.add(0 + ") Move X from CC to DR");
                case 8 -> actions.add(0 + ") Calculate influence in I #");
                case 10 -> actions.add(0 + ") Choose X to not influence");
                case 11 -> actions.add(0 + ") Choose X to remove from DR(s)");
                default -> cardActions = 0;
            }
            return actions;
        }

        int i = 0;
        switch (phase){
            case PLANNING_PHASE -> {
                actions.add(i + ") Play assistant card #");
                i++;
            }
            case ACTION_PHASE_ONE ->{
                actions.add(i + ") Move student X from E to DR");
                i++;
                actions.add(i + ") Move student X from E to I #");
                i++;
            }
            case ACTION_PHASE_TWO -> {
                actions.add(i + ") Move MN of # steps");
                i++;
            }
            case ACTION_PHASE_THREE -> {
                actions.add(i + ") Choose cloud #");
                i++;
            }
        }
        if(expert){
            if(activeCharacterCard == -1 && phase != GamePhase.PLANNING_PHASE){
                actions.add(i + ") Activate card #");
                i++;
            }
            actions.add(i + ") Display card # effect");
        }

        return actions;
    }

    /**
     * @return The assistant cards played this turn, placing -1 if the corresponding player has not played one yet
     */
    public int[] getAssistantCardsPlayed() {
        return assistantCardsPlayed;
    }

    /**
     * @return The CharacterCard played this turn, -1 if none were played
     */
    public int getActiveCharacterCard() {
        return activeCharacterCard;
    }

    /**
     * @return The names of the players in the room
     */
    public String[] getPlayers() {
        return players;
    }

    /**
     * Sets back to original values all parameters related to a game
     */
    public void startOver(){
        playingPlayer = -1;
        players = new String[4];
        phase = GamePhase.PLANNING_PHASE;
        assistantCardsPlayed = new int[]{-1, -1, -1, -1};
    }
}
