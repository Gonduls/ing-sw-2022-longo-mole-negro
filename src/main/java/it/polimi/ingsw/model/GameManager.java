package it.polimi.ingsw.model;

import it.polimi.ingsw.exceptions.NoSpaceForStudentException;
import it.polimi.ingsw.exceptions.NoSuchStudentException;
import it.polimi.ingsw.server.ModelObserver;

import java.util.*;

public class GameManager {

    private final Board board;
    private final Cloud[] clouds;
    private final Bag bag;

    private final List<CharacterCard> activeCards;

    private int usedCard;
    private final Player[] players;
    private final Professors professors;

    ModelObserver modelObserver;
    private final boolean expert;

    /**
     * This constructor, having given it the array of initialized players and the expert mode flag,
     * creates and initializes all model elements, made exception for clouds, that are only created and not filled.
     * @param players: an array of all playing players, already instantiated and owning a school with entrances yet to initialize
     * @param expert: flag that signals if the game is in expert mode or not
     */
    public GameManager(Player[] players, boolean expert,ModelObserver modelObserver) {
        int size = players.length;
        this.players = players;
        this.expert=expert;
        bag = new Bag();
        professors = new Professors();

        this.setModelObserver(modelObserver);

        board = new Board(this.modelObserver);


        clouds = new Cloud[size];
        for (int i = 0; i < size; i++) {
            clouds[i] = new Cloud(size == 3 ? 4 : 3, bag);
        }
        activeCards = new ArrayList<>();
        usedCard = -1;
        //if we are in easy mode, the activeCards will be just an empty list
        if (expert) {
            int randomInt;
            Random randomGen = new Random();
            boolean skip;
            while (activeCards.size() < 3) {
                randomInt = randomGen.nextInt(12);
                skip = false;
                for (CharacterCard cc : activeCards) {
                    if (cc.getId() == randomInt) {
                        skip = true;
                        break;
                    }
                }

                if (!skip) {


                    modelObserver.notifyCharacterCard(randomInt);
                    switch (randomInt) {
                        case 0 -> activeCards.add(new CharacterCardZero(bag, modelObserver));
                        case 1 -> activeCards.add(new CharacterCardOne(modelObserver));
                        case 2 -> activeCards.add(new CharacterCardTwo(bag, modelObserver));
                        case 3 -> activeCards.add(new CharacterCardThree(modelObserver));
                        case 4 -> activeCards.add(new CharacterCardFour(modelObserver));
                        case 5 -> activeCards.add(new CharacterCardFive(modelObserver));
                        case 6 -> activeCards.add(new CharacterCardSix(modelObserver));
                        case 7 -> activeCards.add(new CharacterCardSeven(bag, modelObserver));
                        case 8 -> activeCards.add(new CharacterCardEight(modelObserver));
                        case 9 -> activeCards.add(new CharacterCardNine(modelObserver));
                        case 10 -> activeCards.add(new CharacterCardTen(modelObserver));
                        case 11 -> activeCards.add(new CharacterCardEleven(modelObserver));
                    }

                }
            }
        }


            // initialize entrances and tables for each player
            for (Player player : players) {
                School school = player.getSchool();
                school.initializeEntrances(bag, players.length == 3);

                for( Color color: Color.values()){
                    for(int i=0;i<player.getSchool().getStudentsAtEntrance().getStudentByColor(color);i++){
                        modelObserver.addStudentToEntrance(player.getPlayerNumber(), color);
                    }
                }

                StudentHolder tables = school.getStudentsAtTables();
                tables.attach(new ProfessorsObserver(player, professors,this));
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
     * @return how many towers are contained oonthe island designated by the islandIndex
     */
    public int getTowerNumber(int islandIndex) {
        return board.getIslands().get(islandIndex).getTowerNumber();
    }

    /**
     * @param islandIndex: the index that designates the island in the list
     * @return the color of the tower on the island. If non are present, null is returned
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
        for(Color c: Color.values()){
            for (int i =0; i<clouds[cloudIndex].getStudentByColor(c);i++){
                modelObserver.moveStudentFromCloudToPlayer(cloudIndex, player.getPlayerNumber(), c);
            }
        }
        clouds[cloudIndex].moveAllStudents(player);

    }

    /**
     * It refills all clouds, taking random students from the bag
     */
    public void refillClouds() {
        int i =0;
        for(Cloud cloud : clouds){
            try{
                cloud.refill();
                for(Color color: Color.values()){
                    for (int j =0; j < cloud.getStudentByColor(color); j++ ){

                        modelObserver.addStudentToCloud(i, color);
                    }

                }
            }
            catch (NoSpaceForStudentException e){
                System.out.println("Someone called refill clouds without all of them being empty");
            }
            i++; //this is dumb
        }
    }



    /**
     * It moves Mother Nature of "amount" steps, then performs the influence count
     * and places a tower if needed, checking for islands' merges.
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
            modelObserver.moveMotherNature(position);
            modelObserver.removeNoEntry(position);
            return;
        }

        TowerColor newTC;
        TowerColor previousTC = currentIsland.getTower();


        // if broken remove this line
        newTC = board.calculateInfluenceSmart(position, professors, findCardById(usedCard));

        //when points are tied newTC is null
        if(newTC == null || (previousTC != null && previousTC == newTC)){
            modelObserver.moveMotherNature(position);
            return;
        }

        //past this point we are for sure changing colorTower on the current island


        //if previousTC is not null it means that we have to put back the towers.
        Player newP = players[newTC.ordinal()];
        currentIsland.setTowerColor(newTC);

        if(previousTC != null){
            Player previousP = players[previousTC.ordinal()];
            previousP.setTowersNumber(previousP.getTowersLeft() + currentIsland.getTowerNumber());

            modelObserver.moveTowerToPlayer(previousP.getPlayerNumber(), position, currentIsland.getTowerNumber());
            modelObserver.moveTowerToIsland(newP.getPlayerNumber(), position, currentIsland.getTowerNumber());

        }
        // if it's null it means that the island had no towers on.
        else{
            // if it's null it means that the island had no towers on.
            currentIsland.addTower();
            modelObserver.moveTowerToIsland(newP.getPlayerNumber(), position,1);

        }


        newP.setTowersNumber(newP.getTowersLeft() - currentIsland.getTowerNumber());

        int positionAfterMerge = board.mergeIsland(position, modelObserver);
        board.setMotherNaturePosition(positionAfterMerge);

        modelObserver.moveMotherNature(positionAfterMerge);
        //used for debug, may be handy for future debug
      /*  System.out.println("the position of mother nature after the merge is: " + positionAfterMerge +
                ". The increment was " + amount + ", positionAfterMerge: " + positionAfterMerge + " position: " + position);
      */

        if (parseWinResult(checkEndConditions()).length >0 ){
            modelObserver.sendEndGame(parseWinResult(checkEndConditions()));
        }



    }

    /**
     * This method is called by  character card eight(8).
     * It calculates the influence on an island, without the need for mother nature to be on that
     * island.
     *
     * @param islandIndex
     */
    public void calculateInfluenceWithoutMovement(int islandIndex){
        Island currentIsland = board.getIslands().get(islandIndex);

        TowerColor newTC = board.calculateInfluenceSmart(islandIndex, professors,null);
        TowerColor previousTC = currentIsland.getTower();

        if(newTC == null || (previousTC != null && previousTC == newTC)){
            return;
        }
        //the comments made in calculateInfluence are valid also here
        Player newP = players[newTC.ordinal()];
        newP.setTowersNumber(newP.getTowersLeft() - currentIsland.getTowerNumber());
        currentIsland.setTowerColor(newTC);

        if(previousTC != null){
            Player previousP = players[previousTC.ordinal()];
            previousP.setTowersNumber(previousP.getTowersLeft() + currentIsland.getTowerNumber());
            modelObserver.moveTowerToPlayer(previousP.getPlayerNumber(), islandIndex,currentIsland.getTowerNumber());
            modelObserver.moveTowerToIsland(newP.getPlayerNumber(), islandIndex,currentIsland.getTowerNumber());

        }
        else{
            currentIsland.addTower();
            modelObserver.moveTowerToIsland(newP.getPlayerNumber(), islandIndex,1);

        }

        int offset = 0;
        int MN = getMotherNaturePosition();
        int NOI = board.getNumberOfIslands();
        int sx = ((islandIndex-1)+NOI)%NOI;
        int dx = ((islandIndex+1)%NOI);
        boolean jumpToLast = false;
        boolean jumpToFirst = false;
        if (MN ==0){ // MN is at the first island
            if (board.canBeMerged(islandIndex, dx) && dx == 0){ // l'isola zero viene mangiata dall'ultima
                //set MN to the last island
                jumpToLast=true;
            }
        } else if (MN == NOI -1){ // MN is at the last island
            if ( board.canBeMerged(sx,islandIndex) && sx == MN) { //l'ultima isola viene mangiata dalla prima
                //set MN to the first island
                jumpToFirst = true;
                
            } else {
                if (board.canBeMerged(islandIndex, sx) && sx < MN ) offset--;
                if (board.canBeMerged(islandIndex, dx) && dx <= MN ) offset--;
            }
        } else { // MN is at the middle
            if (board.canBeMerged(islandIndex, sx) && sx < MN ) offset--;
            if (board.canBeMerged(islandIndex, dx) && dx <= MN ) offset--;
        }




        board.mergeIsland(islandIndex, modelObserver);

        if (offset<0 || jumpToFirst || jumpToLast) {
            if (jumpToFirst) board.setMotherNaturePosition(0);
            else if (jumpToLast) board.setMotherNaturePosition(board.getNumberOfIslands() - 1);
            else board.setMotherNaturePosition(((board.getMotherNaturePosition() - offset) + board.getNumberOfIslands()) % board.getNumberOfIslands());

            modelObserver.moveMotherNature(board.getMotherNaturePosition());
        }

        if (parseWinResult(checkEndConditions()).length >0 ){
            modelObserver.sendEndGame(parseWinResult(checkEndConditions()));
        }

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
            modelObserver.moveFromEntranceToIsland(player.getPlayerNumber(), student, index);
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
        modelObserver.moveFromEntranceToTable(player.getPlayerNumber(),student);

    }

    public Player[] checkEndConditions() {

        Player[] winner = new Player[2];
        int t0 = players[0].getTowersLeft();
        int t1 = players[1].getTowersLeft();
        int t2 = 6;
        int p0 = countProfessors(0);
        int p1 = countProfessors(1);
        int p2 = 0;

        if(players.length == 3){
            t2 = players[2].getTowersLeft();
            p2 = countProfessors(2);
        }
        if(t0 <= 0){
            winner[0] = players[0];
            if(players.length == 4)
                winner[1] = players[2];
            return winner;
        }

        if(t1 <= 0){
            winner[0] = players[1];
            if(players.length == 4)
                winner[1] = players[3];
            return winner;
        }

        if(players.length == 3 && t2 <= 0){
            winner[0] = players[2];
            return winner;
        }

        // when the numbers of islands is 3 or less
        if(board.getNumberOfIslands() <= 3){
            if(players.length == 3){
                if(t0 < t1 && t0 < t2)
                    winner[0] = players[0];
                else if(t1 < t0 && t1 < t2)
                    winner[0] = players[1];
                else if(t2 < t0 && t2 < t1)
                    winner[0] = players[2];
                else if(t0 > t1){
                    winner[0] = players[1];
                    if(p1 < p2)
                        winner[0] = players[2];
                    else if (p1 == p2) {
                        winner[1] = players[2];
                    }
                } else if (t1 > t0) {
                    winner[0] = players[0];
                    if(p0 < p2)
                        winner[0] = players[2];
                    else if (p0 == p2) {
                        winner[1] = players[2];
                    }
                } else if (t2 > t0){
                    winner[0] = players[0];
                    if(p0 < p1)
                        winner[0] = players[1];
                    else if (p0 == p1) {
                        winner[1] = players[1];
                    }
                } else //should check again for professors but everyone wins
                    return players;

                return winner;
            }

            // two or four players
            if(t0 < t1){
                winner[0] = players[0];
            }else if(t0 > t1){
                winner[0] = players[1];
            } else if(p0 > p1){
                winner[0] = players[0];
            } else if(p1 > p0){
                winner[0] = players[1];
            } else
                return players;

            if(players.length == 4)
                winner[1] = players[winner[0].getPlayerNumber() + 2];
            return winner;
        }

        return new Player[3];
    }

    /**
     * Given a player, counts how many professors are owned by the team of the player
     * @param playerIndex The index of the player
     * @return the number of professors owned by the team of the player
     */
    private int countProfessors(int playerIndex){
        int result = 0;
        TowerColor tc = players[playerIndex].getTowerColor();
        EnumMap<Color, Player> pr = professors.getOwners();
        for(Color color : Color.values()){
            if(pr.get(color) != null && pr.get(color).getTowerColor() == tc)
                result ++;
        }
        return result;
    }

    public void setUsedCard(int usedCard, int playerNumber) {
        this.usedCard = usedCard;
        if(usedCard>=0) {
            modelObserver.activateCharacterCard(usedCard, playerNumber);
        }
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
        modelObserver.addNoEntry(index);

    }


    public ModelObserver getModelObserver() {
        return modelObserver;
    }

    public void setModelObserver(ModelObserver modelObserver) {
        this.modelObserver = modelObserver;
        professors.setModelObserver(modelObserver);
        for(Player p: players){
            p.setModelObserver(modelObserver);
        }


    }


    public int[] getIdCards(){
        int[] cardIndexes = new int[3];
        int j=0;
        for(int i=0; i<12;i++){
            if(isCardActive(i)){
                cardIndexes[j]=i;
                j++;
            }
        }

        return  Arrays.copyOf(cardIndexes,3);

    }

    public String[] parseWinResult(Player[] winners){
        return  Arrays.stream(winners).sequential().filter(Objects::nonNull).map(Player::getUsername).toArray(String[]::new);
        
    }


}
