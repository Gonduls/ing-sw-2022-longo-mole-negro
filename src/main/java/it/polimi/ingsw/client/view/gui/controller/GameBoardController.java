package it.polimi.ingsw.client.view.gui.controller;


import it.polimi.ingsw.client.ClientController;
import it.polimi.ingsw.client.ClientModelManager;
import it.polimi.ingsw.client.view.gui.GUI;
import it.polimi.ingsw.client.view.gui.RedirectResources;
import it.polimi.ingsw.model.CharacterCard;
import it.polimi.ingsw.model.Color;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.Map;
import java.util.ResourceBundle;

public class GameBoardController implements Initializable {
    static GameBoardController instance;
    public static GameBoardController getInstance(){
        if(instance == null)
            instance = new GameBoardController();

        return instance;
    }
    @FXML
    private AnchorPane ISLANDS;

    @FXML
    private AnchorPane SCHOOL;

    @FXML
    private ImageView COIN;

    @FXML
    private Label OWNEDCOINS;

    @FXML
    private AnchorPane CHARACTERCARDS;

    private RedirectResources redirect;

    //used to change images of students
    private int numberOfReds = 0;
    private int numberOfBlues = 0;
    private int numberOfGreens = 0;
    private int numberOfYellows = 0;
    private int numberOfPinks = 0;



    //getting an instance of ClientModelManager and ClientController from an instance of GUI in order to initialize the game
    private ClientModelManager cmm = GUI.getInstance().getClientModelManager();
    private ClientController cc = GUI.getInstance().getClientController();
    private int numberOfPlayers = cmm.getPlayers().length;
    private boolean expert = cmm.isExpert();

    private int studentNumber = 0;
    private Integer[] indexes = cmm.getCharactersIndexes().toArray(Integer[]::new);
    private int indexesIterator = 0;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //setting board for game start
        if (!expert) {
            //disables and sets as invisible all elements of the game that are part of the expert game mode

            //no entries
            ISLANDS.getChildren().stream().filter(x -> x instanceof AnchorPane).forEach(this::notExpert);

            //coins
            COIN.setVisible(false);
            COIN.setDisable(true);
            OWNEDCOINS.setVisible(false);
            OWNEDCOINS.setDisable(true);

            //character cards
            CHARACTERCARDS.setVisible(false);
            CHARACTERCARDS.setDisable(true);
        }

        //enables and links adversaries school boards depending on the number of players
        switch (numberOfPlayers) {
            case (2) -> {
                //player.left & right .setVisible(false)
                //devo mappare il player 2 alla scuola centrale
            }
            case (3) -> {
                //player.centrale .setVisible(false)
            }
            case (4) -> {
                //default
            }
        }

        ISLANDS.getChildren().stream().filter(x -> x instanceof AnchorPane).forEach(this::setBoard);
    }

    //calls all the methods that initialize or update the board depending on which part of the board we're considering
    private void setBoard(Node node) {
        if(node.getId().startsWith("ISLAND"))
            setIslands((AnchorPane) node);
        else if(node.getId().startsWith("CLOUDS"))
            ((AnchorPane)node).getChildren().stream().filter(x -> x instanceof AnchorPane).forEach(this::setClouds);
        else if(node.getId().startsWith("CHARACTERCARDS"))
            ((AnchorPane)node).getChildren().stream().filter(x -> x instanceof ImageView).forEach(this::setCharacterCards);

    }

    //sets the chosen character cards with their respective images and SHs
    private void setCharacterCards(Node node) {
        if (node.getId().startsWith("CC")) {
            Image image = redirect.characterCardsImages(indexes[indexesIterator]);
            ((ImageView) node).setImage(image);
        } else if (node.getId().startsWith("STUDENTS")) {
            if (CharacterCard.hasStudentHolder(indexes[indexesIterator])) {
                Map<Color,Integer> sh = cmm.getCharacterStudents(indexes[indexesIterator]);
                ((AnchorPane) node).getChildren().stream().filter(x -> x instanceof ImageView).forEach(b -> setStudents(b, sh));
            } else {
                node.setDisable(true);
                node.setVisible(false);
            }
            indexesIterator++;
        }
    }

    private void setClouds(Node node) {
        int cloudsNumber = numberOfPlayers;
        int cloudIndex = Integer.parseInt(node.getId().replaceAll("[\\D]", ""));
        if (cloudIndex < cloudsNumber) {
            node.setVisible(true);
            ((AnchorPane) node).getChildren().stream().filter(x -> x instanceof ImageView).forEach(b -> {
                studentNumber++;
                if ((studentNumber == 4) && (numberOfPlayers == 2 || numberOfPlayers == 4)) {
                    b.setVisible(false);
                    studentNumber = 0;
                } else {
                    for (Color color : Color.values()) {
                        cmm.getCloud(cloudIndex).get(color);
                    }
                }

            });
        }

    }

    private void setIslands(AnchorPane node) {
        int islandIndex = Integer.parseInt(node.getId().replaceAll("[\\D]", ""));
        int noEntriesNum = cmm.getIslands().get(islandIndex).getNoEntry();
        node.getChildren().stream().filter(x -> x instanceof Node).forEach(b -> boardSwitch(b, islandIndex, noEntriesNum));
    }

    public void boardSwitch(Node node, int islandIndex, int noEntriesNum) {
        switch (node.getId().replaceAll("[^A-Za-z]+", "")) {
            case ("RED"):
                ((Label)node).setText(cmm.getIslands().get(islandIndex).getStudents().get(Color.RED).toString());
            case ("YELLOW"):
                ((Label)node).setText(cmm.getIslands().get(islandIndex).getStudents().get(Color.YELLOW).toString());
            case ("PINK"):
                ((Label)node).setText(cmm.getIslands().get(islandIndex).getStudents().get(Color.PINK).toString());
            case ("GREEN"):
                ((Label)node).setText(cmm.getIslands().get(islandIndex).getStudents().get(Color.GREEN).toString());
            case ("BLUE"):
                ((Label)node).setText(cmm.getIslands().get(islandIndex).getStudents().get(Color.BLUE).toString());
            case ("TOWERNUM"):
                ((Label)node).setText(String.valueOf(cmm.getIslands().get(islandIndex).getTowers()));
                break;
            case ("TOWER"): {
                if (cmm.getIslands().get(islandIndex).getTowers() > 0) {
                    node.setVisible(true);
                    switch (cmm.getIslands().get(islandIndex).getTc().toString()) {
                        case ("BLACK"):
                            Image image = new Image(String.valueOf(getClass().getResource("/images/Elements/MinBlackTower.png")));
                            ((ImageView) node).setImage(image);
                        case ("WHITE"):
                            Image image2 = new Image(String.valueOf(getClass().getResource("/images/Elements/MinWhiteTower.png")));
                            ((ImageView) node).setImage(image2);
                        case ("GREY"):
                            Image image3 = new Image(String.valueOf(getClass().getResource("/images/Elements/MinGreyTower.png")));
                            ((ImageView) node).setImage(image3);
                        default:
                            //System.out.println("def tower switch");
                    }
                } else {
                    return;
                }
            }
            case ("NOENTRIESNUM"):
                ((Label)node).setText(String.valueOf(noEntriesNum));
            case ("NOENTRY"):
                if(noEntriesNum > 0) {
                    node.setVisible(true);}
            case ("MOTHERNATURE"):
                node.setVisible(cmm.getMotherNature() == islandIndex);
            default:
                //nothing

        }

    }

    public void notExpert(Node node) {
        if (node.getId().startsWith("NOENTRY")) {
            node.setVisible(false);
            node.setDisable(true);
        }

    }

    public void setStudents(Node node, Map<Color,Integer> sh) {
        setNumberOfStudents(sh);

        if (numberOfReds > 0) {
            Image image = redirect.studentsImages("RED");
            ((ImageView) node).setImage(image);
            numberOfReds--;
        } else if (numberOfBlues > 0) {
            Image image = redirect.studentsImages("BLUE");
            ((ImageView) node).setImage(image);
            numberOfBlues--;
        } else if (numberOfGreens > 0) {
            Image image = redirect.studentsImages("GREEN");
            ((ImageView) node).setImage(image);
            numberOfGreens--;
        } else if (numberOfPinks > 0) {
            Image image = redirect.studentsImages("PINK");
            ((ImageView) node).setImage(image);
            numberOfPinks--;
        } else if (numberOfYellows > 0) {
            Image image = redirect.studentsImages("YELLOW");
            ((ImageView) node).setImage(image);
            numberOfYellows--;
        }

    }

    private void setNumberOfStudents(Map<Color,Integer> sh) {
        //initializing numbers of students per color from the given sh
        numberOfReds = sh.get(Color.RED);
        numberOfBlues = sh.get(Color.BLUE);
        numberOfGreens = sh.get(Color.GREEN);
        numberOfYellows = sh.get(Color.YELLOW);
        numberOfPinks = sh.get(Color.PINK);
    }

}
