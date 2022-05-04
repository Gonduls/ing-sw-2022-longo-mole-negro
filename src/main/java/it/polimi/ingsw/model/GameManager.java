package it.polimi.ingsw.model;

import it.polimi.ingsw.exceptions.NoSpaceForStudentException;
import it.polimi.ingsw.exceptions.NoSuchStudentException;

import java.util.Arrays;
import java.util.List;

public class GameManager {

    private final Board board;
    private final Cloud[] clouds;
    private final Bag bag;
    private final CharacterCard[] activeCards;
    private final boolean[] usedCards;
    private final Player[] players;
    private final Professors professors;

    /**
     * This constructor, having given it the array of initialized players and the expert mode flag,
     * creates and initializes all model elements, made exception for clouds, that are only created and not filled.
     * @param players: an array of all playing players, already instantiated and owning a school with entrances yet to initialize
     * @param expert: flag that signals if the game is in expert mode or not
     */
    public GameManager(Player[] players, boolean expert){
        int size = players.length;
        this.players = players;

        bag = new Bag();
        professors = new Professors();
        board = new Board();
        clouds = new Cloud[size];
        for(int i = 0; i< size; i++){
            clouds[i] = new Cloud(size == 3 ? 4 : 3, bag);
        }

        if(expert){
            //todo: instantiate cards
            usedCards = new boolean[]{false, false, false};
            activeCards = null; // to change
        }
        else{
            activeCards = null;
            usedCards = null;
        }

        // initialize entrances and tables for each player, observers for view needed?
        for(Player player : players){
            School school = player.getSchool();
            school.initializeEntrances(bag, players.length == 3);
            StudentHolder tables = school.getStudentsAtTables();
            tables.attach(new ProfessorsObserver(player, professors));
            tables.attach(new CoinObserver(player));

            if(expert)
                player.addCoin();
        }
    }

    /**
     * @return the exact board instance
     */
    public Board getBoard() {return board;}

    /**
     * @return the array containing all clouds
     */
    public Cloud[] getClouds() {
        return clouds;
    }

    /**
     * @return the exact bag instance
     */
    public Bag getBag() {
        return bag;
    }

    /**
     * @return the array containing the cards that can be used in this game
     */
    public CharacterCard[] getActiveCards() {
        return activeCards;
    }

    /**
     * @return the array of boolean flags that correspond to the cards that have been activated this turn
     */
    public boolean[] getUsedCards() {
        return usedCards;
    }

    /**
     * @return the array that contains the playing player
     */
    public Player[] getPlayers() {
        return players;
    }

    /**
     * @return the list with islands used in the game
     */
    public List<Island> getIslands() {
        return board.getIslands();
    }

    /**
     * @return the exact professors instance
     */
    public Professors getProfessors(){
        return professors;
    }

    /**
     * @return the index of the island that contains MotherNature
     */
    public int getMotherNaturePosition() {
        return board.getMotherNaturePosition();
    }

    /**
     * @param islandIndex: the index that designates the island in the list
     * @return how many towers are contained in the island designated by the islandIndex
     */
    public int getTowerNumber(int islandIndex) {
        return board.getIslands().get(islandIndex).getTowerNumber();
    }

    /**
     * @param islandIndex: the index that designates the island in the list
     * @return the color of the tower in the island. If non are present, null is returned
     */
    public TowerColor getTowerColor(int islandIndex) {
        return board.getIslands().get(islandIndex).getTower();
    }

    /**
     * Moves all students from one cloud to the entrance of a player's school
     *
     * @param cloudIndex: the index of the cloud in the cloud array
     * @param player: the target player
     * @throws NoSpaceForStudentException if the school's entrance cannot hold all students contained in the cloud
     * @throws NoSuchStudentException if the cloud was empty
     */
    public void emptyCloudInPlayer(int cloudIndex, Player player) throws NoSpaceForStudentException, NoSuchStudentException {
        clouds[cloudIndex].moveAllStudents(player.getSchool());
    }

    /**
     * It refills all clouds, taking random students from the bag
     */
    public void refillClouds() {
        for(Cloud cloud : clouds){
            try{
                cloud.refill();
            }
            catch (NoSpaceForStudentException e){
                System.out.println("Someone called refill clouds without all of them being empty");
            }
        }
    }

    public void activateCharacterCars() {
        //todo
    }

    /**
     * It moves Mother Nature of "amount" steps, then performs the influence count
     * and places a tower if needed, checking for islands' merges.
     * It will change behaviour depending on the active cards (todo)
     *
     * @param amount: the steps that Mother Nature has to take
     * @throws IllegalArgumentException if the amount given is less than 1
     */
    public void moveMotherNature(int amount) throws IllegalArgumentException{
        board.moveMotherNature(amount);
        int position = board.getMotherNaturePosition();
        Island currentIsland = board.getIslands().get(position);

        if(activeCards == null){
            TowerColor newTC = board.calculateInfluence(position, professors);
            TowerColor previousTC = currentIsland.getTower();

            if(newTC == null || (previousTC != null && previousTC == newTC)){
                return;
            }

            if(previousTC != null){
                Player previousP = players[previousTC.ordinal()];
                previousP.setTowersNumber(previousP.getTowersLeft() + currentIsland.getTowerNumber());
            }
            else{
                currentIsland.addTower();
            }

            Player newP = players[newTC.ordinal()];
            // todo: controllare fine partita
            newP.setTowersNumber(newP.getTowersLeft() - currentIsland.getTowerNumber());

            currentIsland.setTowerColor(newTC);
            board.mergeIsland(position);

            Player[] winner = new Player[2];

            if(newP.getTowersLeft() <= 0){
                winner[0] = newP;
                winner[1] = null;
                if(players.length == 4)
                    winner[1] = players[newTC.ordinal() + 2];
            }
            //TODO mandare messaggio al controller

            //TODO controllare i casi di parità
            checkEndConditions();

        }
    }

    /**
     * It moves a single student from the entrance of a school to the designated island
     *
     * @param school: the designated school
     * @param student: the student to be moved
     * @param index: the index of the designated island
     * @throws NoSuchStudentException if the target school's entrance does not contain such student
     * @throws IllegalArgumentException if the island's index does not correspond to any island
     */
    public void moveStudentFromEntranceToIsland(School school, Color student, int index) throws NoSuchStudentException, IllegalArgumentException {
        if(index < 0 || index >= board.getNumberOfIslands()) throw new IllegalArgumentException("there is no island number " + index);
        try{
            school.getStudentsAtEntrance().moveStudentTo(student, board.getIslands().get(index));
        }
        catch (NoSpaceForStudentException e){
            System.out.println("An island does not have  a limit on the number of students it can hold");
        }
    }

    /**
     * It moves a student from a school's entrance to the school's tables
     *
     * @param school: the target school
     * @param student: the target student
     * @throws NoSuchStudentException if the target school's entrance does not contain such student
     * @throws NoSpaceForStudentException if the school's tables can no longer contain students of that color
     */
    public void moveStudentFromEntranceToTable(School school, Color student) throws NoSuchStudentException, NoSpaceForStudentException {
        school.getStudentsAtEntrance().moveStudentTo(student, school.getStudentsAtTables());
    }

    public void checkEndConditions() {
        Player[] winner = new Player[2];

        if(board.getNumberOfIslands() <= 3){
            int min = players[0].getTowersLeft();
            winner[0] = players[0];

            if(players.length == 3){
                for(int i = 1; i < 3; i++) {
                    if (players[i].getTowersLeft() < min) {
                        min = players[i].getTowersLeft();
                        winner[0] = players[i];
                    }
                }
            } else if (players.length == 4) {
                winner[1] = players[2];

                if(players[1].getTowersLeft() < min) {
                    min = players[1].getTowersLeft();
                    winner[0] = players[1];
                    winner[1] = players[3];
                }
            } else {
                if(players[1].getTowersLeft() < min) {
                    min = players[1].getTowersLeft();
                    winner[0] = players[1];
                    winner[1] = null;
                }

            }

        }

        //TODO: dovrebbe fare qualcosa con winner

    }



}
