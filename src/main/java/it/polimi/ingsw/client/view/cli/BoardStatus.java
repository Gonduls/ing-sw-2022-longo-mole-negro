package it.polimi.ingsw.client.view.cli;

import it.polimi.ingsw.client.ClientModelManager;

import java.util.ArrayList;
import java.util.List;

public class BoardStatus {
    private final Coordinates[] clouds, schools;
    private final Coordinates infoTurn, infoCards;
    private final List<Coordinates> islands;
    private final int[] islandsRemove;

public BoardStatus(int numberOfPlayers, boolean expert){
        islands = new ArrayList<>();
        clouds = new Coordinates[numberOfPlayers];
        schools = new Coordinates[numberOfPlayers];
        islandsRemove = new int[11];
        infoTurn = new Coordinates(2, 8);

        if(expert){
            infoCards = new Coordinates(19, 8);
        } else{
            infoCards = new Coordinates(25, 8);
        }

        for(int i = 0; i<12; i++){
            switch (i){
                case 0 -> {
                    islands.add(new Coordinates(3, 60));
                    islandsRemove[i] = 0;
                }
                case 1 -> {
                    islands.add(new Coordinates(3, 77));
                    islandsRemove[i] = 1;
                }
                case 2 -> {
                    islands.add(new Coordinates(3, 94));
                    islandsRemove[i] = 0;
                }
                case 3 -> {
                    islands.add(new Coordinates(3, 111));
                    islandsRemove[i] = 0;
                }
                case 4 -> {
                    islands.add(new Coordinates(10, 121));
                    islandsRemove[i] = 4;
                }
                case 5 -> {
                    islands.add(new Coordinates(18, 121));
                    islandsRemove[i] = 4;
                }
                case 6 -> {
                    islands.add(new Coordinates(25, 111));
                    islandsRemove[i] = 2;
                }
                case 7 -> {
                    islands.add(new Coordinates(25, 94));
                    islandsRemove[i] = 7;
                }
                case 8 -> {
                    islands.add(new Coordinates(25, 77));
                    islandsRemove[i] = 5;
                }
                case 9 -> {
                    islands.add(new Coordinates(25, 60));
                    islandsRemove[i] = 3;
                }
                case 10 -> {
                    islands.add(new Coordinates(18, 49));
                    islandsRemove[i] = 1;
                }
                default -> islands.add(new Coordinates(10, 49));

            }
        }

        for(int i = 0; i < numberOfPlayers; i++){
            schools[i] = new Coordinates(1 + 8*i, 146);
        }

        Coordinates[] cloudsC = new Coordinates[]{new Coordinates(11, 73), new Coordinates(12, 97), new Coordinates(17, 73), new Coordinates(19, 97)};
        if(numberOfPlayers == 4){
            clouds[0] = cloudsC[0];
            clouds[1] = cloudsC[1];
            clouds[2] = cloudsC[2];
            clouds[3] = cloudsC[3];
        } else {
            clouds[0] = cloudsC[1];
            clouds[1] = cloudsC[2];
            if (numberOfPlayers == 3)
                clouds[2] = cloudsC[3];
        }
    }

    public void merge(){
        islands.remove(islandsRemove[islands.size() - 2]);
    }

    public List<Coordinates> getIslands() {
        return islands;
    }

    public void printStatus(ClientModelManager cmm){
        printClouds(cmm);
        printIslands(cmm);
        printSchools(cmm);
        printInfo(cmm);
        printCards(cmm);
    }

    public void printIslands(ClientModelManager cmm){}
    public void printSchools(ClientModelManager cmm){}
    public void printClouds(ClientModelManager cmm){}
    public void printInfo(ClientModelManager cmm){}
    public void printCards(ClientModelManager cmm){}
}
