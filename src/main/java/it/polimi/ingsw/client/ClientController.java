package it.polimi.ingsw.client;

import it.polimi.ingsw.Log;
import it.polimi.ingsw.client.view.UI;
import it.polimi.ingsw.controller.GamePhase;
import it.polimi.ingsw.exceptions.UnexpectedMessageException;
import it.polimi.ingsw.messages.*;
import it.polimi.ingsw.server.RoomInfo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ClientController {
    // general control related
    private String username;
    final NetworkHandler nh;
    private ClientModelManager cmm;
    private final UI ui;

    // single-game related
    private int playingPlayer = -1;
    private String[] players = new String[4];
    private GamePhase phase;
    private int[] assistantCardsPlayed = new int[]{-1, -1, -1, -1};
    private int activeCharacterCard;
    private boolean expert;
    private boolean finishedCardActions;


    public ClientController(UI ui, String serverIP, int serverPort) throws IOException {
        this.ui = ui;
        nh = new NetworkHandler(serverIP, serverPort, this);
        new Thread(nh).start();

    }

    public boolean login(String username) throws UnexpectedMessageException {
        try {
            if(nh.login(username)){
                this.username = username;
                return true;
            }
            return false;
        } catch (IOException e) {
            // todo: gestire IO
            return false;
        }
    }

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
                    cmm = new ClientModelManager(new String[]{players[0], players[1]}, expert, ui);
                    numberOfPlayers = 2;
                }else if(players[3] == null){
                    cmm = new ClientModelManager(new String[]{players[0], players[1], players[2]}, expert, ui);
                    numberOfPlayers = 3;
                } else {
                    cmm = new ClientModelManager(players.clone(), expert, ui);
                    numberOfPlayers = 4;
                }

                synchronized (cmm){
                    ui.createGame(numberOfPlayers, expert, cmm);
                }
            }
            case NOTIFY_CHARACTER_CARD -> {
                NotifyCharacterCard ncc = (NotifyCharacterCard) message;
                cmm.putSHInCharacterCard(ncc.card());
            }
            case PLAY_ASSISTANT_CARD -> {
                PlayAssistantCard pac = (PlayAssistantCard) message;
                if(players[pac.player()].equals(username))
                    updates = true;

                assistantCardsPlayed[pac.player()] = pac.assistantCard().getValue();
                ui.printStatus();
            }
            case ACTIVATE_CHARACTER_CARD -> {
                ActivateCharacterCard acc = (ActivateCharacterCard) message;
                if(players[acc.player()].equals(username))
                    updates = true;

                activeCharacterCard = acc.characterCardIndex();
                ui.printStatus();
            }
            case CHANGE_PHASE -> {
                ChangePhase c = (ChangePhase) message;
                phase = c.gamePhase();
                if(c.gamePhase() == GamePhase.PLANNING_PHASE)
                    assistantCardsPlayed = new int[]{-1, -1, -1, -1};
                ui.printStatus();
            }
            case CHANGE_TURN -> {
                ChangeTurn c = (ChangeTurn) message;
                playingPlayer = c.playingPlayer();
                activeCharacterCard = -1;
                ui.printStatus();
                finishedCardActions = true;
            }
            case END_GAME -> ui.showMessage(message);
            default -> {
                updates = true;
                ui.printStatus();
            }
        }

        if(updates)
            synchronized (cmm){
                cmm.updateModel(message);
            }
    }

    boolean myTurn(){
        if(playingPlayer == -1)
            return false;
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
        List<String> actions = new ArrayList<>();
        if(!myTurn()){
            if(expert)
                actions.add(0 + ") Display card # effect");
            else
                actions.add("No possible actions");
            return actions;
        }

        if(!finishedCardActions){
            switch (activeCharacterCard){
                case 0 -> actions.add(0 + ") Move student X from CC to I #");
                case 2 -> {
                    actions.add(0 + ") Swap X from CC with Y from E");
                    actions.add(1 + ") End selections");
                }
                case 3 -> {
                    actions.add(0 + ")Swap X from E with Y from DR");
                    actions.add(1 + ") End selections");
                }
                case 5 -> actions.add(1 + ") Choose I # to place a NoEntry");
                case 7 -> actions.add(1 + ") Move X from CC to DR");
                case 8 -> actions.add(1 + ") Calculate influence in I #");
                case 10 -> actions.add(1 + ") Choose X to not influence");
                case 11 -> actions.add(1 + ")Choose X to remove from DR(s)");
                default -> finishedCardActions = true;
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

    public int[] getAssistantCardsPlayed() {
        return assistantCardsPlayed;
    }

    public int getActiveCharacterCard() {
        return activeCharacterCard;
    }

    public String[] getPlayers() {
        return players;
    }

    public void startOver(){
        playingPlayer = -1;
        players = new String[4];
        phase = GamePhase.PLANNING_PHASE;
        assistantCardsPlayed = new int[]{-1, -1, -1, -1};
    }

    public Message performEvent(GameEvent event){
        Message answer = new Nack("Could not get a proper answer from server");
        try{
            answer = nh.performEvent(event);
        }catch (UnexpectedMessageException | IOException e){
            Log log = new Log("ClientController.txt");
            log.logger.severe(e.getMessage());
            log.logger.severe(e.getLocalizedMessage());
        }

        return answer;
    }
}
