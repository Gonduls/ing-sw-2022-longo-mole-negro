package it.polimi.ingsw.client;

import it.polimi.ingsw.exceptions.UnexpectedMessageException;
import it.polimi.ingsw.messages.*;
import it.polimi.ingsw.model.*;

import java.util.*;

public class ClientModelManager {
    private final EnumMap<Color, Integer>[] entrances, diningRooms, clouds, characterStudents;
    private final List<AssistantCard> deck;
    private final int[] coins, prices, towers;
    private final EnumMap<Color, Integer> professors;
    private final List<ClientIsland> islands;
    private int motherNature;
    private final String[] players;
    private final boolean[] activated;
    private HashMap<Integer, Integer> characterCardsIndexes;
    private int noEntries;

    ClientModelManager(String[] players, boolean expert){
        int numberOfPlayers = players.length;
        this.players = players;
        motherNature = 0;
        islands = new ArrayList<>(12);

        for(int i = 0; i< 12 ; i++){
            islands.add(new ClientIsland());
        }

        professors = new EnumMap<>(Color.class);
        towers = new int[numberOfPlayers];
        deck = new ArrayList<>(Arrays.asList(AssistantCard.values()));
        entrances = new EnumMap[numberOfPlayers];
        diningRooms = new EnumMap[numberOfPlayers];
        clouds = new EnumMap[numberOfPlayers];

        for(int i = 0; i < numberOfPlayers; i++){
            entrances[i] = new EnumMap<>(Color.class);
            diningRooms[i] = new EnumMap<>(Color.class);
            clouds[i] = new EnumMap<>(Color.class);

            for(Color color : Color.values()){
                entrances[i].put(color, 0);
                diningRooms[i].put(color, 0);
                clouds[i].put(color, 0);
            }
        }

        for(Color color : Color.values()){
            professors.put(color, -1);
        }

        if(expert){
            coins = new int[numberOfPlayers];
            prices = new int[3];
            activated = new boolean[]{false, false, false};
            characterCardsIndexes = new HashMap<>();
            characterStudents = new EnumMap[3];

        } else {
            coins = new int[]{0, 0, 0};
            prices = null;
            characterStudents = null;
            activated = null;
        }

        if(numberOfPlayers == 3){
            towers[0] = 6;
            towers[1] = 6;
            towers[2] = 6;
        } else {
            towers[0] = 8;
            towers[1] = 8;
        }
    }

    void putSHInCharacterCard(int card) {
        int index =characterCardsIndexes.size();
        characterCardsIndexes.put(card, index);

        if(CharacterCard.hasStudentHolder(card)){
            this.characterStudents[index] = new EnumMap<>(Color.class);
            for(Color color : Color.values()){
                characterStudents[index].put(color, 0);
            }
        }

        if(card == 5)
            noEntries = 4;
    }

    public Map<Color, Integer> getEntrance(int playerIndex) {
        return new EnumMap<>(entrances[playerIndex]);
    }

    public Map<Color, Integer> getDiningRooms(int playerIndex) {
        return new EnumMap<>(diningRooms[playerIndex]);
    }

    public Map<Color, Integer> getCloud(int index) {
        return new EnumMap<>(clouds[index]);
    }

    public Map<Color, Integer> getCharacterStudents(int index) {
        if(characterStudents != null && characterStudents[index] != null)
            return new EnumMap<>(characterStudents[index]);
        return null;
    }

    public List<AssistantCard> getDeck() {
        return new ArrayList<>(deck);
    }

    public Integer getCoins(int playerIndex) {
        if(coins != null)
            return coins[playerIndex];
        return null;
    }

    public Integer getPrices(int index) {
        if(prices != null)
            return prices[index] + (activated[index] ? 1 : 0);
        return null;
    }

    public int getTowers(int playerIndex) {
        return towers[playerIndex];
    }

    public Map<Color, Integer> getProfessors() {
        return new EnumMap<>(professors);
    }

    public List<ClientIsland> getIslands() {
        return islands;
    }

    public int getMotherNature() {
        return motherNature;
    }

    public String[] getPlayers() {
        return players;
    }

    void updateModel(Message message) throws UnexpectedMessageException{
        switch(message.getMessageType()) {
            case MOVE_STUDENT ->{
                MoveStudent ms = (MoveStudent) message;
                moveStudent(ms.from(), ms.to(), ms.color());
            }
            case ADD_STUDENT_TO -> {
                AddStudentTo ast = (AddStudentTo) message;
                addStudent(ast.to(), ast.color());
            }
            case MOVE_TOWERS -> {
                MoveTowers mt = (MoveTowers) message;
                moveTowers(mt.from(), mt.to(), mt.amount());
            }
            case MERGE_ISLANDS -> {
                // todo: merge islands in client model
            }
            case SET_PROFESSOR_TO -> {
                SetProfessorTo spt = (SetProfessorTo) message;
                professors.put(spt.color(), spt.player());
            }
            case MOVE_MOTHER_NATURE -> {
                MoveMotherNature mmn = (MoveMotherNature) message;
                motherNature = (motherNature + mmn.steps()) % islands.size();
            }
            case PLAY_ASSISTANT_CARD -> {
                PlayAssistantCard pac = (PlayAssistantCard) message;
                deck.remove(pac.assistantCard());
            }

            case ACTIVATE_CHARACTER_CARD -> {
                ActivateCharacterCard acc = (ActivateCharacterCard) message;
                activated[characterCardsIndexes.get(acc.characterCardIndex())] = true;

            }
            case ADD_COIN -> {
                AddCoin ac = (AddCoin) message;
                coins[ac.player()] += 1;
            }
            case PAY_PRICE -> {
                PayPrice pp = (PayPrice) message;
                coins[pp.player()] -= pp.amount();
            }
            case NO_ENTRY -> {
                NoEntry ne = (NoEntry) message;
                if(ne.add()) {
                    islands.get(ne.islandIndex()).addNoEntry();
                    noEntries --;
                }
                else {
                    islands.get(ne.islandIndex()).removeNoEntry();
                    noEntries ++;
                }

            }
            default -> throw(new UnexpectedMessageException("Message does not update the model"));
        }
        //parsing message

        //update model


    }


    void moveStudent(String from, String to, Color color){
        removeStudent(from, color);
        addStudent(to, color);
    }

    void removeStudent(String from, Color color){
        EnumMap<Color, Integer> position = parsePosition(from);
        if(position == null)
            return;

        int result = position.get(color) - 1;
        position.put(color, result);
    }

    void addStudent(String to, Color color){
        EnumMap<Color, Integer> position = parsePosition(to);
        if(position == null)
            return;

        int result = position.get(color) + 1;
        position.put(color, result);
    }

    void moveTowers(String from, String to, int amount){
        if(from.startsWith("ISLAND")){
            int indexIslands = Integer.parseInt(from.split(":")[1]);
            int indexPlayers = Integer.parseInt(to.split(":")[1]);

            ClientIsland island = islands.get(indexIslands);
            towers[indexPlayers] += amount;
            island.setTowers(island.getTowers() - amount);
            return;
        }

        int indexPlayers = Integer.parseInt(from.split(":")[1]);
        int indexIslands = Integer.parseInt(to.split(":")[1]);
        ClientIsland island = islands.get(indexIslands);
        towers[indexPlayers] -= amount;
        island.setTowers(island.getTowers() + amount);
    }

    private EnumMap<Color, Integer> parsePosition(String pos){
        String[] splitPos = pos.split(":");
        int index = Integer.parseInt(splitPos[1]);

        return switch (splitPos[0]) {
            case ("ISLAND") -> islands.get(index).getStudents();
            case ("CLOUD") -> clouds[index];
            case ("ENTRANCE") -> entrances[index];
            case ("DININGROOM") -> diningRooms[index];
            case ("CHARACTERCARD") -> characterStudents[characterCardsIndexes.get(index)];
            default -> null;
        };
    }

    public EnumMap<Color, Integer> getStudentsInIsland(int islandIndex){
        return islands.get(islandIndex).getStudents();
    }

    //
    public int getNoEntries() {
        return noEntries;
    }
}


