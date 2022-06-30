package it.polimi.ingsw.model;

import it.polimi.ingsw.exceptions.NoSpaceForStudentException;
import it.polimi.ingsw.server.ModelObserver;

import java.util.ArrayList;
import java.util.EnumMap;

public class Board {

    private final ArrayList<Island> islands;
    private int motherNaturePosition;
    private int numberOfIslands;

    private ModelObserver modelObserver;

    /**
     * Constructor for Board.
     * Also creates and initializes Islands, giving them a student according to their position
     */
    public Board(ModelObserver observer){
        numberOfIslands = 12;
        motherNaturePosition = 0;
        islands = new ArrayList<>(12);
        Bag initialBag = new Bag(2);
        Color colorTemp;

        // Initializing islands with students according to their position in the list
        for (int i = 0; i< 12; i++) {
            Island current = new Island();
            if(i%6 != 0){
                try{
                    colorTemp = initialBag.extractRandomStudent();
                    current.addStudent(colorTemp);
                    observer.addStudentToIsland(i, colorTemp);
                }catch (NoSpaceForStudentException e){
                    System.out.println("Initializing island has gone wrong");
                }
            }
            islands.add(current);
        }
    }



    /**
     * @return the actual list containing islands
     */
    ArrayList<Island> getIslands() {
        return islands;
    }

    /**
     * Changes the position of motherNature by the amount of steps given, looping back if needed
     * @param amount: the number of steps to take
     * @throws IllegalArgumentException if the amount given is less than 1
     */
    void moveMotherNature(int amount)throws IllegalArgumentException {
        if(amount < 1)
            throw new IllegalArgumentException("Mother nature can only be moved forward");

        motherNaturePosition = (motherNaturePosition + amount) % numberOfIslands;
    }

    /**
     * @return the index representing motherNature position in the islands' list
     */
    int getMotherNaturePosition() {
        return motherNaturePosition;
    }

    void setMotherNaturePosition(int motherNaturePosition){
        this.motherNaturePosition = motherNaturePosition;
    }

    /**
     * Checks if the indexes provided correspond to two islands with the same (not null) towerColor
     *
     * @param islandAIndex is the first island's index
     * @param islandBIndex is the second island's index
     * @return true if the two islands can be merged, false otherwise
     */
    boolean canBeMerged(int islandAIndex, int islandBIndex) {
        return islands.get(islandAIndex).getTower() == islands.get(islandBIndex).getTower() && islands.get(islandAIndex).getTower() != null;
    }

    /**
     * Performs the act of merging islands, by checking compatibility,
     * then adding all elements from an island to the one that it merges with,
     * and finally removing the exceeding island from the list.
     * Checks for both previous and next island for merge, could merge with both at the same time.
     *
     * @param indexCurrentIsland: the index of the island that could be merged with the previous or next island in list
     */
    int mergeIsland(int indexCurrentIsland, ModelObserver modelObserver){

        int indexPreviousIsland = (indexCurrentIsland+ numberOfIslands-1) % numberOfIslands ;
        int noEntryToAdd =0;
        if (canBeMerged(indexCurrentIsland, indexPreviousIsland)){
            noEntryToAdd += islands.get(indexPreviousIsland).getNoEntry();
            islands.get(indexCurrentIsland).unify(islands.get(indexPreviousIsland));
            islands.remove(indexPreviousIsland);
            numberOfIslands--;
            //the null check is useful when testing the model
            if(modelObserver!=null) modelObserver.mergeIslands(indexCurrentIsland, indexPreviousIsland);
            // indexCurrentIsland has to be corrected before next merge check
            indexCurrentIsland = indexPreviousIsland < indexCurrentIsland ? indexCurrentIsland - 1 : indexCurrentIsland;
        }

        int indexNextIsland = (indexCurrentIsland+ numberOfIslands+1) % numberOfIslands;
        if (canBeMerged(indexCurrentIsland, indexNextIsland)){
            noEntryToAdd += islands.get(indexNextIsland).getNoEntry();
            islands.get(indexCurrentIsland).unify(islands.get(indexNextIsland));
            islands.remove(indexNextIsland);
            numberOfIslands--;
            //the null check is useful when testing the model
            if(modelObserver!=null)  modelObserver.mergeIslands(indexCurrentIsland, indexNextIsland);
        }

             for (int i = 0; i < noEntryToAdd; i++) {
                 islands.get(indexCurrentIsland).addNoEntry();
             }
        //the new position of motherNature
        return indexCurrentIsland;
    }

    /**
     * Calculates how much influence a certain TowerColor has over an island
     *
     * @param index: the index of the island to consider
     * @param professors: needed to know which Player controls which professor
     * @return the TowerColor that has the most influence over the islands, null in case of a tie
     * @deprecated
     */
    TowerColor calculateInfluence(int index, Professors professors) {
        EnumMap<TowerColor, Integer> points = new EnumMap<>(TowerColor.class);
        EnumMap<Color, Player> profOwners = professors.getOwners();

        for(TowerColor towerC : TowerColor.values()){
            points.put(towerC, 0);
        }

        if(islands.get(index).getTower() != null){
            points.put(islands.get(index).getTower(), islands.get(index).getTowerNumber());
        }

        for(Color color : Color.values()){
            if(profOwners.get(color) != null){
                TowerColor ownerTC = profOwners.get(color).getTowerColor();
                int finalAmount = points.get(ownerTC) + islands.get(index).getStudentByColor(color);
                points.put(ownerTC, finalAmount);
            }
        }

        int blackP = points.get(TowerColor.BLACK);
        int whiteP = points.get(TowerColor.WHITE);
        int greyP = points.get(TowerColor.GREY);

        if(blackP > whiteP && blackP > greyP)
            return TowerColor.BLACK;

        if(whiteP > blackP && whiteP > greyP)
            return TowerColor.WHITE;

        if(greyP > blackP && greyP > whiteP)
            return TowerColor.GREY;

        return null;
    }


    /**
     * Modified version of CalculateInfluence called when card number 6 is active.
     * It adds 2 influence points to the modifiedTower color
     * @param index
     * @param professors
     * @param modifiedTower
     * @return
     * @deprecated
     */
    TowerColor calculateInfluence(int index, Professors professors, TowerColor modifiedTower) {
        EnumMap<TowerColor, Integer> points = new EnumMap<>(TowerColor.class);
        EnumMap<Color, Player> profOwners = professors.getOwners();

        for(TowerColor towerC : TowerColor.values()){
            points.put(towerC, 0);
        }

        if(islands.get(index).getTower() != null){
            points.put(islands.get(index).getTower(), islands.get(index).getTowerNumber());
        }

        for(Color color : Color.values()){
            if(profOwners.get(color) != null){
                TowerColor ownerTC = profOwners.get(color).getTowerColor();
                int finalAmount = points.get(ownerTC) + islands.get(index).getStudentByColor(color);
                points.put(ownerTC, finalAmount + (ownerTC==modifiedTower?2:0) ); // this is where it differs from the normal method
            }
        }

        int blackP = points.get(TowerColor.BLACK)  ;
        int whiteP = points.get(TowerColor.WHITE);
        int greyP = points.get(TowerColor.GREY);

        if(blackP > whiteP && blackP > greyP)
            return TowerColor.BLACK;

        if(whiteP > blackP && whiteP > greyP)
            return TowerColor.WHITE;

        if(greyP > blackP && greyP > whiteP)
            return TowerColor.GREY;

        return null;
    }

    /**
     * Modified version of CalculateInfluence called when card number 9 is active.
     * It doesn't count the number of towers.
     *
     * @param index
     * @param professors
     * @return
     * @deprecated
     */
    TowerColor calculateInfluenceNoTowers(int index, Professors professors) {
        EnumMap<TowerColor, Integer> points = new EnumMap<>(TowerColor.class);
        EnumMap<Color, Player> profOwners = professors.getOwners();

        for(TowerColor towerC : TowerColor.values()){
            points.put(towerC, 0);
        }


        for(Color color : Color.values()){
            if(profOwners.get(color) != null){
                TowerColor ownerTC = profOwners.get(color).getTowerColor();
                int finalAmount = points.get(ownerTC) + islands.get(index).getStudentByColor(color);
                points.put(ownerTC, finalAmount);
            }
        }

        int blackP = points.get(TowerColor.BLACK);
        int whiteP = points.get(TowerColor.WHITE);
        int greyP = points.get(TowerColor.GREY);

        if(blackP > whiteP && blackP > greyP)
            return TowerColor.BLACK;

        if(whiteP > blackP && whiteP > greyP)
            return TowerColor.WHITE;

        if(greyP > blackP && greyP > whiteP)
            return TowerColor.GREY;

        return null;
    }


    TowerColor calculateInfluenceNoColor(int index, Professors professors, Color colorToIgnore) {
        EnumMap<TowerColor, Integer> points = new EnumMap<>(TowerColor.class);
        EnumMap<Color, Player> profOwners = professors.getOwners();

        for(TowerColor towerC : TowerColor.values()){
            points.put(towerC, 0);
        }

        if(islands.get(index).getTower() != null){
            points.put(islands.get(index).getTower(), islands.get(index).getTowerNumber());
        }

        for(Color color : Color.values()){
            if (color == colorToIgnore) continue;
            if(profOwners.get(color) != null){
                TowerColor ownerTC = profOwners.get(color).getTowerColor();
                int finalAmount = points.get(ownerTC) + islands.get(index).getStudentByColor(color);
                points.put(ownerTC, finalAmount);
            }
        }

        int blackP = points.get(TowerColor.BLACK);
        int whiteP = points.get(TowerColor.WHITE);
        int greyP = points.get(TowerColor.GREY);

        if(blackP > whiteP && blackP > greyP)
            return TowerColor.BLACK;

        if(whiteP > blackP && whiteP > greyP)
            return TowerColor.WHITE;

        if(greyP > blackP && greyP > whiteP)
            return TowerColor.GREY;

        return null;
    }


    public TowerColor calculateInfluenceSmart(int index, Professors professors, CharacterCard card){
        TowerColor modifiedTowerColor = null;
        Color modifiedStudentColor = null;


        EnumMap<TowerColor, Integer> points = new EnumMap<>(TowerColor.class);
        EnumMap<Color, Player> profOwners = professors.getOwners();

        for(TowerColor towerC : TowerColor.values()){
            points.put(towerC, 0);
        }

        if(islands.get(index).getTower() != null){
            points.put(islands.get(index).getTower(), islands.get(index).getTowerNumber());
        }


        if(card != null) {
            switch (card.getId()) {
                case 6 -> modifiedTowerColor = ((CharacterCardSix) card).getTowerColor();
                case 9 -> points.put(islands.get(index).getTower(), 0);
                case 10 -> modifiedStudentColor = ((CharacterCardTen) card).getColor();
                default -> {
                    // no action to be taken, normal course of action
                }
            }
        }


        for(Color color : Color.values()){
            if (color == modifiedStudentColor) continue;
            if(profOwners.get(color) != null){
                TowerColor ownerTC = profOwners.get(color).getTowerColor();
                int finalAmount = points.get(ownerTC) + islands.get(index).getStudentByColor(color);
                points.put(ownerTC, finalAmount + (ownerTC==modifiedTowerColor?2:0)  );
            }
        }

        int blackP = points.get(TowerColor.BLACK);
        int whiteP = points.get(TowerColor.WHITE);
        int greyP = points.get(TowerColor.GREY);

        if(blackP > whiteP && blackP > greyP)
            return TowerColor.BLACK;

        if(whiteP > blackP && whiteP > greyP)
            return TowerColor.WHITE;

        if(greyP > blackP && greyP > whiteP)
            return TowerColor.GREY;

        return null;

    }



    /**
     * @return the number of Islands in the list
     */
    public int getNumberOfIslands(){return numberOfIslands;}
}

