package it.polimi.ingsw.model;

import java.util.List;

public class GameManager {

    private final Board board;
    private final Cloud[] clouds;
    private final Bag bag;
    private CharacterCard[] activeCards;
    private boolean[] usedCards;
    private final Player[] players;
    private final Professors professors;

    public GameManager(Player[] players, boolean expert){
        this.players = players;
        board = new Board();

        int size = players.length;
        clouds = new Cloud[size];
        for(int i = 0; i< size; i++){
            clouds[i] = new Cloud(size == 3 ? 4 : 3, this);
        }

        bag = new Bag();
        professors = new Professors();

        if(expert){
            //todo: instantiate cards
            usedCards = new boolean[]{false, false, false};

        }
        else{
            activeCards = null;
            usedCards = null;

        }
    }

    public Board getBoard() {
        return board;
    }

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

    public void emptyCloudInPlayer(int cloudIndex, Player player) throws NoSpaceForStudentException, NoSuchStudentException{
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
        //todo: decidere come gestire le carte, possibile gestione:
        // mettere un flag in gamemanager, da controllare prima di calcolare l'influenza

        // todo: l'if sotto è una bozza, da cambiare (ad esempio: questo course of action è lo stesso se non ci sono carte attive)
        if(activeCards == null){
            TowerColor tc = board.calculateInfluence(position, professors);
            board.getIslands().get(position).setTowerColor(tc);
            board.mergeIsland(position);
        }
    }



}
