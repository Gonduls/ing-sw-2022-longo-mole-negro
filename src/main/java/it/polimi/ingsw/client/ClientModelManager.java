package it.polimi.ingsw.client;

import it.polimi.ingsw.client.view.UI;
import it.polimi.ingsw.exceptions.UnexpectedMessageException;
import it.polimi.ingsw.messages.*;
import it.polimi.ingsw.model.*;

import java.util.*;

public class ClientModelManager {
    private final EnumMap<Color, Integer>[] entrances, diningRooms, clouds;
    private final List<AssistantCard> deck;
    private final int[] coins, towers;
    private final EnumMap<Color, Integer> professors;
    private final List<ClientIsland> islands;
    private int motherNature;
    private final String[] players;
    private final HashMap<Integer, Integer> prices;
    private final HashMap<Integer, Boolean> activated;
    private final HashMap<Integer, EnumMap<Color, Integer>> characterStudents;
    private int noEntries;
    private final UI ui;

    ClientModelManager(String[] players, boolean expert, UI ui) {
        int numberOfPlayers = players.length;
        this.players = players;
        this.ui = ui;
        motherNature = 0;
        islands = new ArrayList<>(12);

        for (int i = 0; i < 12; i++) {
            islands.add(new ClientIsland());
        }

        professors = new EnumMap<>(Color.class);
        towers = new int[numberOfPlayers];
        deck = new ArrayList<>(Arrays.asList(AssistantCard.values()));
        entrances = new EnumMap[numberOfPlayers];
        diningRooms = new EnumMap[numberOfPlayers];
        clouds = new EnumMap[numberOfPlayers];

        for (int i = 0; i < numberOfPlayers; i++) {
            entrances[i] = new EnumMap<>(Color.class);
            diningRooms[i] = new EnumMap<>(Color.class);
            clouds[i] = new EnumMap<>(Color.class);

            for (Color color : Color.values()) {
                entrances[i].put(color, 0);
                diningRooms[i].put(color, 0);
                clouds[i].put(color, 0);
            }
        }

        for (Color color : Color.values()) {
            professors.put(color, -1);
        }

        if (expert) {
            coins = new int[numberOfPlayers];
            prices = new HashMap<>();
            activated = new HashMap<>();
            characterStudents = new HashMap<>();

        } else {
            coins = new int[]{0, 0, 0};
            prices = null;
            characterStudents = null;
            activated = null;
        }

        if (numberOfPlayers == 3) {
            towers[0] = 6;
            towers[1] = 6;
            towers[2] = 6;
        } else {
            towers[0] = 8;
            towers[1] = 8;
        }
    }

    void putSHInCharacterCard(int cardId) {
        prices.put(cardId, cardId / 4 + 1);
        activated.put(cardId, false);

        if (CharacterCard.hasStudentHolder(cardId)) {
            EnumMap<Color, Integer> studs = new EnumMap<>(Color.class);
            for (Color color : Color.values()) {
                studs.put(color, 0);
            }
            characterStudents.put(cardId, studs);
        }

        if (cardId == 5)
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

    public Map<Color, Integer> getCharacterStudents(int cardId) {
        return characterStudents.get(cardId);
    }

    public List<AssistantCard> getDeck() {
        return new ArrayList<>(deck);
    }

    public Integer getCoins(int playerIndex) {
        if (coins != null)
            return coins[playerIndex];
        return null;
    }

    public Integer getPrice(int cardId) {
        return prices.get(cardId);
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

    void updateModel(Message message) throws UnexpectedMessageException {
        switch (message.getMessageType()) {
            case MOVE_STUDENT -> {
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
                ui.merge();
                mergeIslands((MergeIslands) message);
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
                activated.put(acc.characterCardIndex(), true);

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
                if (ne.add()) {
                    islands.get(ne.islandIndex()).addNoEntry();
                    noEntries--;
                } else {
                    islands.get(ne.islandIndex()).removeNoEntry();
                    noEntries++;
                }

            }
            default -> throw (new UnexpectedMessageException("Message does not update the model"));
        }
    }

    void moveStudent(String from, String to, Color color) {
        removeStudent(from, color);
        addStudent(to, color);
    }

    void removeStudent(String from, Color color) {
        EnumMap<Color, Integer> position = parsePosition(from);
        if (position == null)
            return;

        int result = position.get(color) - 1;
        position.put(color, result);
    }

    void addStudent(String to, Color color) {
        EnumMap<Color, Integer> position = parsePosition(to);
        if (position == null) {
            return;
        }

        int result = position.get(color) + 1;
        position.put(color, result);
    }

    void moveTowers(String from, String to, int amount) {
        if (from.startsWith("ISLAND")) {
            int indexIslands = Integer.parseInt(from.split(":")[1]);
            int indexPlayers = Integer.parseInt(to.split(":")[1]);

            ClientIsland island = islands.get(indexIslands);
            towers[indexPlayers] += amount;
            island.setTowers(island.getTowers() - amount);
            island.setTc(null);
            return;
        }

        int indexPlayers = Integer.parseInt(from.split(":")[1]);
        int indexIslands = Integer.parseInt(to.split(":")[1]);
        ClientIsland island = islands.get(indexIslands);
        towers[indexPlayers] -= amount;
        island.setTowers(island.getTowers() + amount);
        island.setTc(TowerColor.values()[indexPlayers]);

    }

    private EnumMap<Color, Integer> parsePosition(String pos) {
        String[] splitPos = pos.split(":");
        int index = Integer.parseInt(splitPos[1]);

        return switch (splitPos[0]) {
            case ("ISLAND") -> islands.get(index).getStudents();
            case ("CLOUD") -> clouds[index];
            case ("ENTRANCE") -> entrances[index];
            case ("DININGROOM") -> diningRooms[index];
            case ("CARD") -> characterStudents.get(index);
            default -> null;
        };
    }

    public int getNoEntries() {
        return noEntries;
    }

    public Set<Integer> getCharactersIndexes() {
        return prices.keySet();
    }

    void mergeIslands(MergeIslands message) {
        ClientIsland island1 = islands.get(message.firstIslandIndex());
        ClientIsland island2 = islands.get(message.secondIslandIndex());

        // moving all students and towers from one island to the other
        EnumMap<Color, Integer> students1 = island1.getStudents();
        EnumMap<Color, Integer> students2 = island2.getStudents();
        for (Color color : Color.values()) {
            students1.put(color, students1.get(color) + students2.get(color));
        }
        island1.setTowers(island1.getTowers() + island2.getTowers());

        // Only need one of the 2 islands in the list
        islands.remove(message.secondIslandIndex());

    }

}