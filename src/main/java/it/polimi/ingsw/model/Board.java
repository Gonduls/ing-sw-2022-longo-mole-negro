package it.polimi.ingsw.model;

import java.util.ArrayList;
import java.util.EnumMap;

public class Board {

    private final ArrayList<Island> islands;
    private int motherNaturePosition;
    private int numberOfIslands;

    public Board(){
        numberOfIslands = 12;
        motherNaturePosition = 0;
        islands = new ArrayList<>(12);
        Bag initialBag = new Bag(2);

        // Initializing islands with students according to their position in the list
        for (int i = 0; i< 12; i++) {
            Island current = new Island();
            if(i%6 != 0){
                try{
                    current.addStudent(initialBag.extractRandomStudent());
                }catch (NoSpaceForStudentException e){
                    System.out.println("Initializing island has gone wrong");
                }
            }
            islands.add(current);

        }
    }

    ArrayList<Island> getIslands() {
        return islands;
    }

    void moveMotherNature(int amount)throws IllegalArgumentException {
        if(amount < 1)
            throw new IllegalArgumentException("Mother nature can only be moved forward");

        motherNaturePosition = (motherNaturePosition + amount) % numberOfIslands;
    }

    int getMotherNaturePosition() {
        return motherNaturePosition;
    }

    boolean canBeMerged(int indexCurrentIsland, int indexOtherIsland) {
        return islands.get(indexCurrentIsland).getTower() == islands.get(indexOtherIsland).getTower();
    }

    void mergeIsland(int indexCurrentIsland){
        int indexPreviousIsland = (indexCurrentIsland+ numberOfIslands-1) % numberOfIslands ;
        if (canBeMerged(indexCurrentIsland, indexPreviousIsland)){
            islands.get(indexCurrentIsland).unify(islands.get(indexPreviousIsland));
            islands.remove(indexPreviousIsland);
            numberOfIslands--;

            // indexCurrentIsland has to be corrected before next merge check
            indexCurrentIsland = indexPreviousIsland < indexCurrentIsland ? indexCurrentIsland - 1 : indexCurrentIsland;
        }

        int indexNextIsland = (indexCurrentIsland+ numberOfIslands+1) % numberOfIslands;
        if (canBeMerged(indexCurrentIsland, indexNextIsland)){
            islands.get(indexCurrentIsland).unify(islands.get(indexNextIsland));
            islands.remove(indexNextIsland);
            numberOfIslands--;
        }

    }

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
}

