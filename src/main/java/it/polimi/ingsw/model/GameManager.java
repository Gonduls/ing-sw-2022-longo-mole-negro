package it.polimi.ingsw.model;

import it.polimi.ingsw.exceptions.NoSpaceForStudentException;
import it.polimi.ingsw.exceptions.NoSuchStudentException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class GameManager {

    private final Board board;
    private final Cloud[] clouds;
    private final Bag bag;

    private final List<CharacterCard> activeCards;

    private int usedCard;
    private final Player[] players;
    private final Professors professors;



    /**
     * This constructor, having given it the array of initialized players and the expert mode flag,
     * creates and initializes all model elements, made exception for clouds, that are only created and not filled.
     * @param players: an array of all playing players, already instantiated and owning a school with entrances yet to initialize
     * @param expert: flag that signals if the game is in expert mode or not
     */
    public GameManager(Player[] players, boolean expert) {
        int size = players.length;
        this.players = players;

        bag = new Bag();
        professors = new Professors();
        board = new Board();
        clouds = new Cloud[size];
        for (int i = 0; i < size; i++) {
            clouds[i] = new Cloud(size == 3 ? 4 : 3, bag);
        }

        if (expert) {
            // todo: instantiate cards

            usedCard = -1;

            activeCards = new ArrayList<>();// to change
            int randomInt;

            Random randomGen = new Random();

            while (activeCards.size() < 3) {
                randomInt = randomGen.nextInt(12);
                for (CharacterCard cc : activeCards) {
                    if (cc.getId() == randomInt) {
                        continue;
                    }

                    switch (randomInt) {
                        case 0:
                            activeCards.add(new CharacterCardZero(bag));
                            break;
                        case 1:
                            //activeCards.add(new CharacterCardOne(bag));
                            break;
                        case 2:
                            activeCards.add(new CharacterCardTwo(bag));
                            break;
                        case 3:
                            activeCards.add(new CharacterCardThree());
                            break;
                        case 4:
                            //todo
                            break;
                        case 5:
                            activeCards.add(new CharacterCardFive());
                            break;
                        case 6:
                            activeCards.add(new CharacterCardSix());
                            break;
                        case 7:
                            activeCards.add(new CharacterCardSeven(bag));
                            break;
                        case 8:
                            activeCards.add(new CharacterCardEight());
                            break;
                        case 9:
                            activeCards.add(new CharacterCardNine());
                            break;
                        case 10:
                            activeCards.add(new CharacterCardTen());
                            break;
                        case 11:
                            activeCards.add(new CharacterCardEleven());
                            break;
                    }

                }
            }

        }
        else{
                activeCards = null;
                usedCard = -1;
            }

            // initialize entrances and tables for each player, observers for view needed?
            for (Player player : players) {
                School school = player.getSchool();
                school.initializeEntrances(bag, players.length == 3);
                StudentHolder tables = school.getStudentsAtTables();
                tables.attach(new ProfessorsObserver(player, professors));
                tables.attach(new CoinObserver(player));

                if (expert)
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
    public List<CharacterCard> getActiveCards() {
        return activeCards;
    }

    /**
     * @return the id the card activated this turn
     */
    public int getUsedCard() {
        return usedCard;
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
        clouds[cloudIndex].moveAllStudents(player);
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

        if(board.getIslands().get(position).getNoEntry()>0){
            board.getIslands().get(position).removeNoEntry();
            return;
        }

        TowerColor newTC = null;
        TowerColor previousTC = currentIsland.getTower();

        if (usedCard == 6){
            CharacterCardSix cc6 = (CharacterCardSix) findCardById(6);
            newTC = board.calculateInfluence(position, professors,cc6.getTowerColor());

        } else if (usedCard == 9) {
            newTC = board.calculateInfluenceNoTowers(position, professors);

        } else if (usedCard == 10){
            CharacterCardTen cc10 = (CharacterCardTen) findCardById(10);
            newTC = board.calculateInfluenceNoColor(position, professors, cc10.getColor());
        } else {
            newTC = board.calculateInfluence(position, professors);
        }

        //when points are tied newTC is null
        if(newTC == null || (previousTC != null && previousTC == newTC)){
            return;
        }

       //if it's not null it means that we have to put back the towers.
        if(previousTC != null){
            Player previousP = players[previousTC.ordinal()];
            previousP.setTowersNumber(previousP.getTowersLeft() + currentIsland.getTowerNumber());
            //todo-> moveTower from currentIsland to PreviousPlayer
            //todo-> moveTower from newPlayer to currentIsland
        }
        else{
            // if it's null it means that the island had no towers on.
            //todo -> moveTower from newP to currentIsland
            currentIsland.addTower();

        }

        Player newP = players[newTC.ordinal()];
        newP.setTowersNumber(newP.getTowersLeft() - currentIsland.getTowerNumber());
        currentIsland.setTowerColor(newTC);
        board.mergeIsland(position);
        checkEndConditions();


        /*
        if(usedCard != 6 && usedCard != 9 && usedCard != 10){ // the card number six is the one that changes the influence math
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
            newP.setTowersNumber(newP.getTowersLeft() - currentIsland.getTowerNumber());
            currentIsland.setTowerColor(newTC);
            board.mergeIsland(position);
            checkEndConditions();

        } else if (usedCard==6) {  // this is the card that add +2 to the current player
            // this a stupid copy and paste -> a little bit of  refactoring could help
            CharacterCardSix cc6 = (CharacterCardSix) findCardById(6);
            TowerColor newTC = board.calculateInfluence(position, professors,cc6.getTowerColor());
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
            newP.setTowersNumber(newP.getTowersLeft() - currentIsland.getTowerNumber());
            currentIsland.setTowerColor(newTC);
            board.mergeIsland(position);
            checkEndConditions();



        } else if (usedCard == 9){ // this is the card that doesn't count the towers!

            TowerColor newTC = board.calculateInfluenceNoTowers(position, professors);
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
            newP.setTowersNumber(newP.getTowersLeft() - currentIsland.getTowerNumber());
            currentIsland.setTowerColor(newTC);
            board.mergeIsland(position);
            checkEndConditions();

        }

         */


    }

    /**
     * This method is called by  character card eight(8)
     *
     * @param islandIndex
     */
    public void calculateInfluenceWithoutMovement(int islandIndex){
        Island currentIsland = board.getIslands().get(islandIndex);

        TowerColor newTC = board.calculateInfluence(islandIndex, professors);
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
        newP.setTowersNumber(newP.getTowersLeft() - currentIsland.getTowerNumber());
        currentIsland.setTowerColor(newTC);
        board.mergeIsland(islandIndex);

        checkEndConditions();

    }

    /**
     * It moves a single student from the entrance of a school to the designated island
     *
     * @param player: the player that owns the designated school
     * @param student: the student to be moved
     * @param index: the index of the designated island
     * @throws NoSuchStudentException if the target school's entrance does not contain such student
     * @throws IllegalArgumentException if the island's index does not correspond to any island
     */
    public void moveStudentFromEntranceToIsland(Player player, Color student, int index) throws NoSuchStudentException, IllegalArgumentException {
        if(index < 0 || index >= board.getNumberOfIslands()) throw new IllegalArgumentException("there is no island number " + index);
        try{
            player.getSchool().getStudentsAtEntrance().moveStudentTo(student, board.getIslands().get(index));
        }
        catch (NoSpaceForStudentException e){
            System.out.println("An island does not have  a limit on the number of students it can hold");
        }
    }

    /**
     * It moves a student from a school's entrance to the school's tables
     *
     * @param player: the  player that owns the target school
     * @param student: the target student
     * @throws NoSuchStudentException if the target school's entrance does not contain such student
     * @throws NoSpaceForStudentException if the school's tables can no longer contain students of that color
     */
    public void moveStudentFromEntranceToTable(Player player, Color student) throws NoSuchStudentException, NoSpaceForStudentException {
        player.getSchool().getStudentsAtEntrance().moveStudentTo(student, player.getSchool().getStudentsAtTables());
    }

    public Player[] checkEndConditions() {

        Player[] winner = new Player[3];
        int position = board.getMotherNaturePosition();
        Island currentIsland = board.getIslands().get(position);
        TowerColor currentTC = currentIsland.getTower();
        Player newP = players[currentTC.ordinal()];

        if(newP.getTowersLeft() <= 0){
            winner[0] = newP;
            winner[1] = null;
            winner[2] = null;
            if(players.length == 4)
                winner[1] = players[currentTC.ordinal() + 2];
        }
        //TODO mandare messaggio al controller

        else if(board.getNumberOfIslands() <= 3){
            int min = players[0].getTowersLeft();
            winner[0] = players[0];

            if(players.length == 3){
                for (int i = 1; i < 3; i++) {
                    if (players[i].getTowersLeft() < min) {
                        min = players[i].getTowersLeft();
                    }
                }

                int k = 0;

                for (int j = 0; j < 3; j++) {
                    if (players[j].getTowersLeft() == min) {
                        winner[k] = players[j];
                        k++;
                    }
                }
            } else if (players.length == 4) {
                winner[1] = players[2];

                if(players[1].getTowersLeft() < min) {
                    min = players[1].getTowersLeft();
                    winner[0] = players[1];
                    winner[1] = players[3];
                } else if (players[1].getTowersLeft() == min) {
                    winner[1] = players[1];
                }
            } else {
                if(players[1].getTowersLeft() < min ) {
                    min = players[1].getTowersLeft();
                    winner[0] = players[1];
                    winner[1] = null;
                } else if (players[1].getTowersLeft() == min) {
                    winner[1] = players[1];
                }

            }

        }

        return winner;

    }




    public void setUsedCard(int usedCard) {
        this.usedCard = usedCard;
    }

    public boolean isCardActive(int id){
        for(CharacterCard cc : activeCards){
            if(cc.getId()==id) return true;
        }
        return false;
    }

    public CharacterCard findCardById(int id){
        for(CharacterCard cc : activeCards){
            if (id == cc.getId()){
                return cc;
            }
        }
        return null;
    }

    public void addNoEntry (int index){
        board.getIslands().get(index).addNoEntry();

    }

}
