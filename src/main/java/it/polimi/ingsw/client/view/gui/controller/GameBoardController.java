package it.polimi.ingsw.client.view.gui.controller;


import it.polimi.ingsw.Log;
import it.polimi.ingsw.client.ClientController;
import it.polimi.ingsw.client.ClientModelManager;
import it.polimi.ingsw.client.view.gui.GUI;
import it.polimi.ingsw.client.view.gui.RedirectResources;
import it.polimi.ingsw.model.AssistantCard;
import it.polimi.ingsw.model.CharacterCard;
import it.polimi.ingsw.model.Color;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.*;

public class GameBoardController implements Initializable {
    static GameBoardController instance;
    public static GameBoardController getInstance(){
        if(instance == null)
            instance = new GameBoardController();

        return instance;
    }
    @FXML
    private AnchorPane BOARD;

    @FXML
    private AnchorPane ISLANDS;

    @FXML
    private ImageView COIN;

    @FXML
    private ImageView NEXTBUTTON;

    @FXML
    private ImageView ASSISTANTCARD;

    @FXML
    private Label OWNEDCOINS;

    @FXML
    private AnchorPane CHARACTERCARDS;

    //used to change images of students
    private static int numberOfReds = 0;
    private static int numberOfBlues = 0;
    private static int numberOfGreens = 0;
    private static int numberOfYellows = 0;
    private static int numberOfPinks = 0;

    private static int currentDRColor = 0;
    private static int iteratorDR = 0;

    private static int iteratorTowers = 0;
    private static int currentTowerNumber = 0;



    //getting an instance of ClientModelManager and ClientController from an instance of GUI in order to initialize the game
    private ClientModelManager cmm = GUI.getInstance().getClientModelManager();
    private ClientController cc = GUI.getInstance().getClientController();
    private int numberOfPlayers = cmm.getPlayers().length;
    private boolean expert = cmm.isExpert();

    private static int studentNumber = 0;
    private Integer[] indexes;
    private static int indexesIterator = 0;

    private static ArrayList<Image> deck = new ArrayList<>();
    private static int ACindex = 0;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        //setting board for game start
        if (!expert) {
            //disables and sets as invisible all elements of the game that are part of the expert game mode

            //no entries
            ISLANDS.getChildren().stream().filter(AnchorPane.class::isInstance).forEach(this::notExpert);

            //coins
            COIN.setVisible(false);
            COIN.setDisable(true);
            OWNEDCOINS.setVisible(false);
            OWNEDCOINS.setDisable(true);

            //character cards
            CHARACTERCARDS.setVisible(false);
            CHARACTERCARDS.setDisable(true);
        } else{
            indexes = cmm.getCharactersIndexes().toArray(Integer[]::new);
        }

        OWNEDCOINS.setText(String.valueOf(cmm.getCoins(getThisPlayerIndex())));

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

        /*EventHandler<MouseEvent> nextACHandler = mouseEvent -> {
            nextAssistantCard(mouseEvent);
            mouseEvent.consume();
        };

        NEXTBUTTON.setOnMouseClicked(nextACHandler);*/


        //parses through every element of the board
        BOARD.getChildren().stream().filter(AnchorPane.class::isInstance).forEach(this::setBoard);
        
    }


    // Calls all the methods that initialize or update the board depending on which part of the board we're considering
    private void setBoard(Node node) {
        if(node.getId().startsWith("ISLANDS"))
            ((AnchorPane)node).getChildren().stream().filter(AnchorPane.class::isInstance).forEach(x -> setIslands((AnchorPane) x));
        else if(node.getId().startsWith("CLOUDS")) {
            ((AnchorPane) node).getChildren().stream().filter(AnchorPane.class::isInstance).forEach(this::setClouds);
            studentNumber = 0;
        }
        else if(node.getId().startsWith("SCHOOL")) {
            ((AnchorPane) node).getChildren().stream().filter(AnchorPane.class::isInstance).forEach(this::setSchool);
        }
        else if(expert && node.getId().startsWith("CHARACTERCARDS")) {
            ((AnchorPane) node).getChildren().stream().filter(Objects::nonNull).forEach(this::setCharacterCards);
        }
        else if(node.getId().startsWith("ASSISTANT")) {
            ((AnchorPane)node).getChildren().stream().filter(ImageView.class::isInstance).forEach(this::setDeck);
        }
    }

    private void setIslands(AnchorPane node) {
        int islandIndex = Integer.parseInt(node.getId().replaceAll("\\D", ""));
        int noEntriesNum = cmm.getIslands().get(islandIndex).getNoEntry();
        node.getChildren().stream().filter(Objects::nonNull).forEach(b -> boardSwitch(b, islandIndex, noEntriesNum));
    }

    public void boardSwitch(Node node, int islandIndex, int noEntriesNum) {
        switch (node.getId().replaceAll("[^A-Za-z]+", "")) {
            case ("RED") -> ((Label) node).setText(cmm.getIslands().get(islandIndex).getStudents().get(Color.RED).toString());
            case ("YELLOW") -> ((Label) node).setText(cmm.getIslands().get(islandIndex).getStudents().get(Color.YELLOW).toString());
            case ("PINK") -> ((Label) node).setText(cmm.getIslands().get(islandIndex).getStudents().get(Color.PINK).toString());
            case ("GREEN") -> ((Label) node).setText(cmm.getIslands().get(islandIndex).getStudents().get(Color.GREEN).toString());
            case ("BLUE") -> ((Label) node).setText(cmm.getIslands().get(islandIndex).getStudents().get(Color.BLUE).toString());
            case ("TOWERNUM") -> ((Label) node).setText(String.valueOf(cmm.getIslands().get(islandIndex).getTowers()));
            case ("TOWER") -> {
                if (cmm.getIslands().get(islandIndex).getTowers() <= 0)
                    break;

                node.setVisible(true);
                switch (cmm.getIslands().get(islandIndex).getTc()) {
                    case BLACK -> {
                        Image image = new Image(String.valueOf(getClass().getResource("/images/Elements/MinBlackTower.png")));
                        ((ImageView) node).setImage(image);
                    }
                    case WHITE -> {
                        Image image2 = new Image(String.valueOf(getClass().getResource("/images/Elements/MinWhiteTower.png")));
                        ((ImageView) node).setImage(image2);
                    }
                    case GREY -> {
                        Image image3 = new Image(String.valueOf(getClass().getResource("/images/Elements/MinGreyTower.png")));
                        ((ImageView) node).setImage(image3);
                    }
                }
            }
            case ("NOENTRIESNUM") -> ((Label) node).setText(String.valueOf(noEntriesNum));
            case ("NOENTRY") -> node.setVisible(noEntriesNum > 0);
            case ("MOTHERNATURE") -> node.setVisible(cmm.getMotherNature() == islandIndex);
            default -> Log.logger.info("Unexpected node id: " + node.getId().replaceAll("[^A-Za-z]+", ""));

        }
    }

    //Sets the correct number of clouds per number of players
    private void setClouds(Node node) {
        int cloudsNumber = numberOfPlayers;
        int cloudIndex = Integer.parseInt(node.getId().replaceAll("\\D", ""));
        if (cloudIndex < cloudsNumber) {
            setNumberOfStudents(cmm.getCloud(cloudIndex));
            node.setVisible(true);
            node.setDisable(false);
            ((AnchorPane) node).getChildren().stream().filter(ImageView.class::isInstance).forEach(b -> {
                if (b.getId().startsWith("STUDENT")) {
                    studentNumber++;
                    if ((studentNumber == 4) && (numberOfPlayers == 2 || numberOfPlayers == 4)) {
                        b.setVisible(false);
                        studentNumber = 0;
                    } else {
                        setStudents(b);
                    }
                }
            });
        }
    }

    //Sets all the elements of the Players' schools
    private void setSchool(Node node) {
        if (node.getId().startsWith("DR")) {
            String[] s = node.getId().split("_");
            currentDRColor = cmm.getDiningRooms(getThisPlayerIndex()).get(Color.valueOf(s[1]));
            ((AnchorPane) node).getChildren().stream().filter(AnchorPane.class::isInstance).forEach(this::setDiningRoom);
        }
        else if (node.getId().startsWith("PROFESSORS"))
            ((AnchorPane) node).getChildren().stream().filter(ImageView.class::isInstance).forEach(this::setProfessors);
        else if (node.getId().startsWith("ENTRANCE")){
            setNumberOfStudents(cmm.getEntrance(getThisPlayerIndex()));
            ((AnchorPane) node).getChildren().stream().filter(ImageView.class::isInstance).forEach(x -> {
                if(studentNumber > 6 && numberOfPlayers != 3)
                    return;
                studentNumber++;
                setStudents(x);
                x.setVisible(true);
                x.setDisable(false);
            });
            studentNumber = 0;
        }
        else if (node.getId().startsWith("TOWERS")) {
            ((AnchorPane)node).getChildren().stream().filter(ImageView.class::isInstance).forEach(this::setTowers);
            iteratorTowers = 0;
        }

    }

    //Shows the towers still present in the player's school
    private void setTowers(Node node) {
        switch (getThisPlayerIndex()) {
            case 0 -> {
                Image image = RedirectResources.minTowersImages("WHITE");
                ((ImageView) node).setImage(image);
            }
            case 1 -> {
                Image image;
                if (numberOfPlayers == 3)
                    image = RedirectResources.minTowersImages("GREY");
                else
                    image = RedirectResources.minTowersImages("BLACK");

                ((ImageView) node).setImage(image);
            }
            case 2 -> {
                Image image;
                if (numberOfPlayers == 3) {
                    image = RedirectResources.minTowersImages("BLACK");
                    ((ImageView) node).setImage(image);
                } else
                    return;
            }
            case 3 -> {
                return;
            }
        }

        currentTowerNumber = cmm.getTowers(getThisPlayerIndex());
        if (iteratorTowers < currentTowerNumber) {
            node.setVisible(true);
            iteratorTowers++;
        } else {
            node.setDisable(true);
        }
    }

    //Checks and shows the professors a Player has
    private void setProfessors(Node node) {
        if (cmm.getProfessors().get(Color.valueOf(node.getId())) == getThisPlayerIndex())
            node.setVisible(true);
    }

    //Shows the state of the DiningRoom
    private void setDiningRoom(Node node) {
        if (iteratorDR < currentDRColor) {
            node.setVisible(true);
        }

        iteratorDR++;

        if (iteratorDR == 10) {
            iteratorDR = 0;
        }
    }

    //Sets the chosen character cards with their respective images and SHs
    private void setCharacterCards(Node node) {
        if (node.getId().startsWith("CC")) {
            //Changes the image of the card
            Image image = RedirectResources.characterCardsImages(indexes[indexesIterator]);
            ((ImageView) node).setImage(image);
        } else if (node.getId().startsWith("STUDENTS")) {
            //If the current card has a sh, sets the sh
            if (CharacterCard.hasStudentHolder(indexes[indexesIterator])) {
                Map<Color,Integer> sh = cmm.getCharacterStudents(indexes[indexesIterator]);
                setNumberOfStudents(sh);
                ((AnchorPane) node).getChildren().stream().filter(ImageView.class::isInstance).forEach(this::setStudents);
            } else {
                node.setDisable(true);
                node.setVisible(false);
            }

            //Next CC
            indexesIterator++;
            if(indexesIterator > 2)
                indexesIterator = 0;
        }
    }

    //Sets the deck's back and currently shown card
    private void setDeck(Node node) {
        if (node.getId().startsWith("ASSISTANTCARDDECK")){
            //cambia il colore del deck
        }else if (node.getId().startsWith("ASSISTANTCARD")) {
            initializeDeck();
            showAssistantCard(node);
        }
    }

    //Creates an ArrayList with Assistant Cards' images
    private void initializeDeck() {
        List<AssistantCard> assistantCards = cmm.getDeck();
        for (AssistantCard ac : assistantCards) {
            deck.add(RedirectResources.ACImages(ac.getValue()));
        }
    }

    //Shows the currently selected Assistant Card
    private void showAssistantCard(Node node) {
        ((ImageView)node).setImage(deck.get(ACindex));
    }

    //Shows the next Assistant Card
    @FXML
    private void nextAssistantCard(MouseEvent e) {
        showAssistantCard(ASSISTANTCARD);
        e.consume();
        ACindex++;
        if(ACindex == deck.size() -1)
            ACindex = 0;
    }

    //Shows previous Assistant Card
    @FXML
    private void prevAssistantCard(MouseEvent event) {
        ACindex--;
        if(ACindex == 0)
            ACindex = deck.size() - 1;
        showAssistantCard(ASSISTANTCARD);
        event.consume();
    }

    public void notExpert(Node node) {
        if (node.getId().startsWith("NOENTRY")) {
            node.setVisible(false);
            node.setDisable(true);
        }

    }

    //Sets the correct image for the Students when they're choosen randomly
    public void setStudents(Node node) {
        if (numberOfReds > 0) {
            Image image = RedirectResources.studentsImages("RED");
            ((ImageView) node).setImage(image);
            numberOfReds--;
        } else if (numberOfBlues > 0) {
            Image image = RedirectResources.studentsImages("BLUE");
            ((ImageView) node).setImage(image);
            numberOfBlues--;
        } else if (numberOfGreens > 0) {
            Image image = RedirectResources.studentsImages("GREEN");
            ((ImageView) node).setImage(image);
            numberOfGreens--;
        } else if (numberOfPinks > 0) {
            Image image = RedirectResources.studentsImages("PINK");
            ((ImageView) node).setImage(image);
            numberOfPinks--;
        } else if (numberOfYellows > 0) {
            Image image = RedirectResources.studentsImages("YELLOW");
            ((ImageView) node).setImage(image);
            numberOfYellows--;
        }
    }

    //Initializes numbers of students per color from the given sh
    private void setNumberOfStudents(Map<Color,Integer> sh) {
        numberOfReds = sh.get(Color.RED);
        numberOfBlues = sh.get(Color.BLUE);
        numberOfGreens = sh.get(Color.GREEN);
        numberOfYellows = sh.get(Color.YELLOW);
        numberOfPinks = sh.get(Color.PINK);
    }

    //Returns the current board's player
    private int getThisPlayerIndex() {
        String[] players = cc.getPlayers();
        for (int i = 0; i < players.length; i++) {
            if(GUI.getInstance().getUsername().equals(players[i]))
                return i;
        }
        return -1;
    }

}
