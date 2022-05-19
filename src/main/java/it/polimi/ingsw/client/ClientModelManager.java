package it.polimi.ingsw.client;

import it.polimi.ingsw.messages.Message;
import it.polimi.ingsw.model.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.EnumMap;
import java.util.List;

public class ClientModelManager {
    private final EnumMap<Color, Integer>[] entrances, diningRooms, clouds, characterStudents;
    private final List<AssistantCard> deck;
    private final int[] coins, prices, towers;
    private final EnumMap<Color, Integer> professors;
    private final List<ClientIsland> islands;
    private int motherNature;
    private final String[] players;

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
        deck = Arrays.stream(AssistantCard.values()).toList();
        entrances = new EnumMap[numberOfPlayers];
        diningRooms = new EnumMap[numberOfPlayers];
        clouds = new EnumMap[numberOfPlayers];

        if(expert){
            coins = new int[numberOfPlayers];
            prices = new int[3];
            characterStudents = new EnumMap[3];

        } else {
            coins = null;
            prices = null;
            characterStudents = null;
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

    void putSHInCharacterCard(int index0, int index1, int index2) {
        this.characterStudents[0] = CharacterCard.hasStudentHolder(index0) ? new EnumMap<>(Color.class) : null;
        this.characterStudents[1] = CharacterCard.hasStudentHolder(index1) ? new EnumMap<>(Color.class) : null;
        this.characterStudents[2] = CharacterCard.hasStudentHolder(index2) ? new EnumMap<>(Color.class) : null;
    }

    public EnumMap<Color, Integer> getEntrance(int playerIndex) {
        return new EnumMap<>(entrances[playerIndex]);
    }

    public EnumMap<Color, Integer> getDiningRooms(int playerIndex) {
        return new EnumMap<>(diningRooms[playerIndex]);
    }

    public EnumMap<Color, Integer> getCloud(int index) {
        return new EnumMap<>(clouds[index]);
    }

    public EnumMap<Color, Integer> getCharacterStudents(int index) {
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
            return prices[index];
        return null;
    }

    public int getTowers(int playerIndex) {
        return towers[playerIndex];
    }

    public EnumMap<Color, Integer> getProfessors() {
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
            case MOVE_TOWERS -> {}
            case MERGE_ISLANDS -> {}
            case SET_PROFESSOR_TO -> {}
            case MOVE_MOTHER_NATURE -> {}
            case PLAY_ASSISTANT_CARD -> {}
            case ACTIVATE_CHARACTER_CARD -> {}
            case ADD_COIN -> {}
            case PAY_PRICE -> {}
            default -> {
                throw(new UnexpectedMessageException("Message does not update the model"));
            }
        }
        //parsing message

        //update model


    }

    void moveMotherNature(int steps){
        motherNature = (motherNature + steps) % islands.size();
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

    private EnumMap<Color, Integer> parsePosition(String pos){
        String[] splitPos = pos.split(":");
        int index = Integer.parseInt(splitPos[1]);

        return switch (splitPos[0]) {
            case ("ISLAND") -> islands.get(index).getStudents();
            case ("CLOUD") -> clouds[index];
            case ("ENTRANCE") -> entrances[index];
            case ("DININGROOM") -> diningRooms[index];
            case ("CHARACTERCARD") -> characterStudents[index];
            default -> null;
        };
    }
}

class ClientIsland{
    private final EnumMap<Color, Integer> students;
    private int towers, noEntry;
    private TowerColor tc;

    ClientIsland(){
        students = new EnumMap<>(Color.class);
        noEntry = 0;
        towers = 0;
    }

    public EnumMap<Color, Integer> getStudents() {
        return students;
    }

    public int getNoEntry() {
        return noEntry;
    }

    public int getTowers() {
        return towers;
    }

    public void addNoEntry() {
        noEntry ++;
    }

    public void removeNoEntry() {
        noEntry --;
    }

    public void setTc(TowerColor tc) {
        this.tc = tc;
    }

    public void setTowers(int towers) {
        this.towers = towers;
    }
}
