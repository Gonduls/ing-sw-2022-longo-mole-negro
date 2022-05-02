package it.polimi.ingsw.model;

import it.polimi.ingsw.exceptions.NoSpaceForStudentException;
import it.polimi.ingsw.exceptions.NoSuchStudentException;

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
     * @return the exact board object
     */
    public Board getBoard() {return board;}

    public Cloud[] getClouds() {
        return clouds;
    }

    public Bag getBag() {
        return bag;
    }

    public CharacterCard[] getActiveCards() {
        return activeCards;
    }

    public boolean[] getUsedCards() {
        return usedCards;
    }

    public Player[] getPlayers() {
        return players;
    }

    public List<Island> getIslands() {
        return board.getIslands();
    }

    public Professors getProfessors(){
        return professors;
    }

    public int getMotherNaturePosition() {
        return board.getMotherNaturePosition();
    }

    public int getTowerNumber(int islandIndex) {
        return board.getIslands().get(islandIndex).getTowerNumber();
    }

    public TowerColor getTowerColor(int islandIndex) {
        return board.getIslands().get(islandIndex).getTower();
    }

    public void emptyCloudInPlayer(int cloudIndex, Player player) throws NoSpaceForStudentException, NoSuchStudentException {
        clouds[cloudIndex].moveAllStudents(player.getSchool());
    }

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

    public void moveMotherNature(int amount) throws IllegalArgumentException{
        board.moveMotherNature(amount);
        int position = board.getMotherNaturePosition();
        Island currentIsland = board.getIslands().get(position);
        //todo: decidere come gestire le carte, possibile gestione:
        // mettere un flag in gamemanager, da controllare prima di calcolare l'influenza

        // todo: l'if sotto è una bozza, da cambiare (ad esempio: questo course of action è lo stesso se non ci sono carte attive)
        // todo: aggiungere effettivamente la prima torre (il metodo addTower non è mai stato chiamato)
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
        }
    }


    public void moveStudentFromEntranceToIsland(School school, Color c, int index) throws NoSuchStudentException, NoSpaceForStudentException {
        if(index < board.getNumberOfIslands()) throw new IllegalArgumentException("there is no island number " + index);
        school.getStudentsAtEntrance().moveStudentTo(c, board.getIslands().get(index));
    }

    public void moveStudentFromEntranceToTable(School school, Color c) throws NoSuchStudentException, NoSpaceForStudentException {
        school.getStudentsAtEntrance().moveStudentTo(c, school.getStudentsAtTables());
    }


}
