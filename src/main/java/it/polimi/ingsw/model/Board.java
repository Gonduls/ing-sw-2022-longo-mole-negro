package it.polimi.ingsw.model;

import java.util.ArrayList;
import java.util.ListIterator;

public class Board {

    private final ArrayList<Island> islands;
    private final ListIterator<Island> iterator;

    private final Professors professors;

    private int motherNaturePosition;

    private int numberOfIslands;

    public Board(GameManager gameManager){
        this.professors = gameManager.getProfessors();
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
        iterator = islands.listIterator();
    }

    public ArrayList<Island> getIslands() {
        return islands;
    }

    public int getMotherNaturePosition() {
        return motherNaturePosition;
    }

    boolean canBeMerged(int indexCurrentIsland, int indexOtherIsland) {
        return islands.get(indexCurrentIsland).getTower() == islands.get(indexOtherIsland).getTower();
    }

    void mergeIsland(int indexCurrentIsland){
        int indexPreviousIsland = (indexCurrentIsland+ numberOfIslands-1) % numberOfIslands ;
        int indexNextIsland = (indexCurrentIsland+ numberOfIslands+1) % numberOfIslands ;
        if (canBeMerged(indexCurrentIsland, indexPreviousIsland)){
            islands.get(indexCurrentIsland).unify(islands.get(indexPreviousIsland));
            islands.remove(indexPreviousIsland);
            numberOfIslands--;
        }

        if (canBeMerged(indexCurrentIsland, indexNextIsland)){
            islands.get(indexCurrentIsland).unify(islands.get(indexNextIsland));
            islands.remove(indexNextIsland);
            numberOfIslands--;
        }


    }

    TowerColor calculateInfluence(int index) {







        return TowerColor.BLACK;
    }
}

