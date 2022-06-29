package it.polimi.ingsw.client;

import it.polimi.ingsw.exceptions.UnexpectedMessageException;
import it.polimi.ingsw.messages.*;
import it.polimi.ingsw.model.*;

import java.util.*;

/**
 * Client side model
 */
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
    private final boolean expert;

    /**
     * Creates the game starting from passed information, character cards are excluded and are added afterwards
     * @param players The name of the players
     * @param expert The difficulty of the game
     */
    ClientModelManager(String[] players, boolean expert) {
        int numberOfPlayers = players.length;
        this.players = players;
        this.expert = expert;
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

    /**
     * Adds a CharacterCard to the game, only done in expert mode
     * @param cardId The identifier of the card
     */
    void insertCharacterCard(int cardId) {
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

    /**
     * Returns the students in the entrance of the school owned by the specified player
     * @param playerIndex The target player (in order of entrance in the room)
     * @return A copy of the desired students
     */
    public Map<Color, Integer> getEntrance(int playerIndex) {
        return new EnumMap<>(entrances[playerIndex]);
    }

    /**
     * Returns the students in the dining room of the school owned by the specified player
     * @param playerIndex The target player (in order of entrance in the room)
     * @return A copy of the desired students
     */
    public Map<Color, Integer> getDiningRooms(int playerIndex) {
        return new EnumMap<>(diningRooms[playerIndex]);
    }

    /**
     * Returns the students contained in the specified cloud
     * @param index The cloud number
     * @return A copy of the desired students
     */
    public Map<Color, Integer> getCloud(int index) {
        return new EnumMap<>(clouds[index]);
    }

    /**
     * Returns the students contained in the specified card
     * @param cardId The card identifier
     * @return The desired students
     */
    public Map<Color, Integer> getCharacterStudents(int cardId) {
        return characterStudents.get(cardId);
    }

    /**
     * @return A copy of the cards contained in the deck of Assistant Cards owned by the player
     */
    public List<AssistantCard> getDeck() {
        return new ArrayList<>(deck);
    }

    /**
     * @param playerIndex The target player (in order of entrance in the room)
     * @return The coins owned by the player
     */
    public Integer getCoins(int playerIndex) {
        if (coins != null)
            return coins[playerIndex];
        return null;
    }

    /**
     * @param cardId The desired Character Card
     * @return The current price of the character card, considering its eventual previous activation inside the game
     */
    public Integer getPrice(int cardId) {
        return prices.get(cardId) + (activated.get(cardId) ? 1 : 0);
    }

    /**
     * @param playerIndex The target player (in order of entrance in the room)
     * @return The number of towers still inside the player school
     */
    public int getTowers(int playerIndex) {
        return towers[playerIndex];
    }

    /**
     * @return A copy of professors
     */
    public Map<Color, Integer> getProfessors() {
        return new EnumMap<>(professors);
    }

    /**
     * @return All islands currently in the model
     */
    public List<ClientIsland> getIslands() {
        return islands;
    }

    /**
     * @return Mother nature's island's index
     */
    public int getMotherNature() {
        return motherNature;
    }

    /**
     * @return All playing players' names
     */
    public String[] getPlayers() {
        return players;
    }

    /**
     * @param cardId The desired Character Card
     * @return True if tha card has been activated during this game, false otherwise
     */
    public boolean wasActivated(int cardId){
        if(!activated.containsKey(cardId)) return false;
        return activated.get(cardId);
    }

    /**
     * Modifies client model depending on message received
     * @param message The message containing information on how to modify the model
     * @throws UnexpectedMessageException If the message does not modify the model
     */
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
            case MERGE_ISLANDS -> mergeIslands((MergeIslands) message);
            case SET_PROFESSOR_TO -> {
                SetProfessorTo spt = (SetProfessorTo) message;
                professors.put(spt.color(), spt.player());
            }
            case MOVE_MOTHER_NATURE -> motherNature = ((MoveMotherNature) message).position();
            case PLAY_ASSISTANT_CARD -> deck.remove(((PlayAssistantCard) message).assistantCard());
            case ACTIVATE_CHARACTER_CARD -> {
                ActivateCharacterCard acc = (ActivateCharacterCard) message;
                coins[acc.player()] -= getPrice(acc.characterCardIndex());
                activated.put(acc.characterCardIndex(), true);
            }
            case ADD_COIN -> coins[((AddCoin) message).player()] += 1;
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

    /**
     * Move a single student from one place to the other
     * @param from Where the student is to be taken from
     * @param to Where the student is to be placed
     * @param color The color of the student
     */
    void moveStudent(String from, String to, Color color) {
        removeStudent(from, color);
        addStudent(to, color);
    }

    /**
     * Removes a single student from a target place
     * @param from Where the student is to be removed from
     * @param color The color of the student
     */
    void removeStudent(String from, Color color) {
        EnumMap<Color, Integer> position = parsePosition(from);
        if (position == null)
            return;

        int result = position.get(color) - 1;
        position.put(color, result);
    }

    /**
     * Adds a single student to a target place
     * @param to Where the student is to be added
     * @param color The color of the student
     */
    void addStudent(String to, Color color) {
        EnumMap<Color, Integer> position = parsePosition(to);
        if (position == null) {
            return;
        }

        int result = position.get(color) + 1;
        position.put(color, result);
    }

    /**
     * Moves a determined amount of towers from a place to another
     * @param from Where the towers are to be taken from
     * @param to Where the towers are to be added to
     * @param amount The amount of towers to move
     */
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

    /**
     * Returns the students specified by the string position.
     * Position has to be in the form of: "place:index", where place can be one of:
     * ISLAND, CLOUD, ENTRANCE, DININGROOM, CARD
     * And index has to refer to one of the possible places
     * @param position A string determining the students' position
     * @return The students needed
     */
    private EnumMap<Color, Integer> parsePosition(String position) {
        String[] splitPos = position.split(":");
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

    /**
     * @return No entries still on top of the card
     */
    public int getNoEntries() {
        return noEntries;
    }

    /**
     * @return The indexes of the cards available in this game
     */
    public Set<Integer> getCharactersIndexes() {
        return prices.keySet();
    }

    /**
     * Merges the two islands identified in the message
     * @param message The message that merges two islands
     */
    void mergeIslands(MergeIslands message) {
        int ind1 = message.firstIslandIndex();
        int ind2 = message.secondIslandIndex();
        ClientIsland island1 = islands.get(ind1);
        ClientIsland island2 = islands.get(ind2);

        // moving all students and towers from one island to the other
        EnumMap<Color, Integer> students1 = island1.getStudents();
        EnumMap<Color, Integer> students2 = island2.getStudents();
        for (Color color : Color.values()) {
            students1.put(color, students1.get(color) + students2.get(color));
        }
        island1.setTowers(island1.getTowers() + island2.getTowers());

        for (int i =0; i<island2.getNoEntry(); i++){
            island1.addNoEntry();
        }


        // Only need one of the two islands in the list
        islands.remove(ind2);
    }

    /**
     * @return The difficulty of the game
     */
    public boolean isExpert() {
        return expert;
    }
}