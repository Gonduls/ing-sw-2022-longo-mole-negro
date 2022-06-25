package it.polimi.ingsw.client.view.gui.controller;


import it.polimi.ingsw.Log;
import it.polimi.ingsw.client.ClientController;
import it.polimi.ingsw.client.ClientModelManager;
import it.polimi.ingsw.client.view.gui.GUI;
import it.polimi.ingsw.client.view.gui.RedirectResources;
import it.polimi.ingsw.messages.GameEvent;
import it.polimi.ingsw.messages.Message;
import it.polimi.ingsw.messages.MessageType;
import it.polimi.ingsw.messages.Nack;
import it.polimi.ingsw.messages.events.*;
import it.polimi.ingsw.model.AssistantCard;
import it.polimi.ingsw.model.CharacterCard;
import it.polimi.ingsw.model.Color;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.effect.BlendMode;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;
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
    private ImageView ASSISTANTCARD;

    @FXML
    private ImageView ASSISTANTCARDDECK;

    @FXML
    private Label OWNEDCOINS;

    @FXML
    private AnchorPane CHARACTERCARDS;

    @FXML
    private Label MESSAGES;

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

    private static int player;


    //getting an instance of ClientModelManager and ClientController from an instance of GUI in order to initialize the game
    private ClientModelManager cmm = GUI.getInstance().getClientModelManager();
    private ClientController cc = GUI.getInstance().getClientController();
    private int numberOfPlayers = cmm.getPlayers().length;
    private boolean expert = cmm.isExpert();

    private static int studentNumber = 0;
    private Integer[] indexes;
    private static int indexesIterator = 0;

    private static ArrayList<Image> deck;
    private static int ACindex = 0;
    private static int totalStudentsNumber = 0;

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

        //parses through every element of the board
        BOARD.getChildren().stream().filter(AnchorPane.class::isInstance).forEach(this::setBoard);
        
    }

    private void reprint() {
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

        if(GUI.getInstance().wasMerged(islandIndex)){
            merge(islandIndex);
            return;
        }

        int modelIndex = GUI.getInstance().getIslandModelIndex(islandIndex);
        int noEntriesNum = cmm.getIslands().get(modelIndex).getNoEntry();
        node.getChildren().stream().filter(Objects::nonNull).forEach(b -> boardSwitch(b, modelIndex, noEntriesNum));
    }

    public void boardSwitch(Node node, int islandIndex, int noEntriesNum) {
        switch (node.getId().replaceAll("[^A-Za-z]+", "")) {
            case("R") -> setStudentPieceInIsland(node, Color.RED, islandIndex);
            case("B") -> setStudentPieceInIsland(node, Color.BLUE, islandIndex);
            case("G") -> setStudentPieceInIsland(node, Color.GREEN, islandIndex);
            case("P") -> setStudentPieceInIsland(node, Color.PINK, islandIndex);
            case("Y") -> setStudentPieceInIsland(node, Color.YELLOW, islandIndex);
            case ("RED") -> setStudentNumberInIsland(node, Color.RED, islandIndex);
            case ("YELLOW") -> setStudentNumberInIsland(node, Color.YELLOW, islandIndex);
            case ("PINK") -> setStudentNumberInIsland(node, Color.PINK, islandIndex);
            case ("GREEN") -> setStudentNumberInIsland(node, Color.GREEN, islandIndex);
            case ("BLUE") -> setStudentNumberInIsland(node, Color.BLUE, islandIndex);
            case ("TOWERNUM") -> {
                int num = cmm.getIslands().get(islandIndex).getTowers();
                if(num < 2)
                    break;
                ((Label) node).setText(String.valueOf(num));
                node.setVisible(true);
            }
            case ("TOWER") -> {
                if (cmm.getIslands().get(islandIndex).getTowers() <= 0)
                    break;

                node.setVisible(true);
                switch (cmm.getIslands().get(islandIndex).getTc()) {
                    case BLACK -> {
                        Image image = RedirectResources.towersImages("BLACK");
                        ((ImageView) node).setImage(image);
                    }
                    case WHITE -> {
                        Image image2 = RedirectResources.towersImages("WHITE");
                        ((ImageView) node).setImage(image2);
                    }
                    case GREY -> {
                        Image image3 = RedirectResources.towersImages("GREY");
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

    // if no students of that color are present => don't show student piece
    public void setStudentPieceInIsland(Node node, Color color, int islandIndex){
        int num = cmm.getIslands().get(islandIndex).getStudents().get(color);
        if(num == 0) {
            node.setVisible(false);
        }
    }

    // if non number is needed for that color => don't show number, else set number
    public void setStudentNumberInIsland(Node node, Color color, int islandIndex){
        int num = cmm.getIslands().get(islandIndex).getStudents().get(color);
        if(num < 2) {
            node.setVisible(false);
            return;
        }
        ((Label) node).setText(Integer.toString(num));
    }


    //Sets the correct number of clouds per number of players
    private void setClouds(Node node) {
        int cloudsNumber = numberOfPlayers;
        int cloudIndex = Integer.parseInt(node.getId().replaceAll("\\D", ""));

        if(cloudIndex >= cloudsNumber)
            return;

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

    //Sets all the elements of the Players' schools
    private void setSchool(Node node) {
        if (node.getId().startsWith("DININGROOM")) {
            ((AnchorPane)node).getChildren().stream().filter(AnchorPane.class::isInstance).forEach(b -> {
                String[] s = b.getId().split("_");
                currentDRColor = cmm.getDiningRooms(getThisPlayerIndex()).get(Color.valueOf(s[1]));
                ((AnchorPane) b).getChildren().stream().filter(ImageView.class::isInstance).forEach(this::setDiningRoom);
            });
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
                Image image = RedirectResources.minTowersImages("BLACK");
                ((ImageView) node).setImage(image);
            }
            case 1 -> {
                Image image = RedirectResources.minTowersImages("WHITE");
                ((ImageView) node).setImage(image);
            }
            case 2 -> {
                Image image;
                if (numberOfPlayers == 3) {
                    image = RedirectResources.minTowersImages("GREY");
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
            } else if(indexesIterator == 5) {
                /*((AnchorPane) node).getChildren().stream().filter(ImageView.class::isInstance).forEach(
                        b -> ((ImageView) b).setImage(RedirectResources.getNoEntryImage()));*/
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
            switch(getThisPlayerIndex()) {
                case 0 -> {
                    Image image = RedirectResources.getDeckImages(0);
                    ASSISTANTCARDDECK.setImage(image);}
                case 1 -> {
                    Image image = RedirectResources.getDeckImages(1);
                    ASSISTANTCARDDECK.setImage(image);}
                case 2 -> {
                    Image image = RedirectResources.getDeckImages(2);
                    ASSISTANTCARDDECK.setImage(image);
                }
                case 3 -> {
                    Image image = RedirectResources.getDeckImages(3);
                    ASSISTANTCARDDECK.setImage(image);
                }
            }
        }else if (node.getId().startsWith("ASSISTANTCARD")) {
            initializeDeck();
            showAssistantCard(node);
        }
    }

    //Creates an ArrayList with Assistant Cards' images
    private void initializeDeck() {
        List<AssistantCard> assistantCards = cmm.getDeck();

        deck  =new ArrayList<>();
        for (AssistantCard ac : assistantCards) {
            deck.add(RedirectResources.ACImages(ac.getValue()));
        }
    }

    //Shows the currently selected Assistant Card
    private void showAssistantCard(Node node) {
        ((ImageView)node).setImage(deck.get(ACindex % deck.size()));
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
        } else{
            node.setVisible(false);
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

    //All functions called to manage player's actions

    //Shows the next Assistant Card
    @FXML
    private void nextAssistantCard(MouseEvent e) {
        if(ACindex == deck.size() -1) {
            ACindex = -1;
        }
        ACindex++;
        showAssistantCard(ASSISTANTCARD);
        e.consume();
    }

    //Shows previous Assistant Card
    @FXML
    private void prevAssistantCard(MouseEvent event) {
        if(ACindex == 0) {
            ACindex = deck.size();
        }
        ACindex--;
        showAssistantCard(ASSISTANTCARD);
        event.consume();
    }

    @FXML
    private void selectAssistantCard(MouseEvent event) {
        String action = "Play AC";
        String url = deck.get(ACindex).getUrl();
        String index = url.substring(url.length() - 5, url.length() - 4);
        if(index.equals("0"))
            index = "10";
        dealWithAction(action, index, null);
        event.consume();
    }

    @FXML
    private void studentSelected(MouseEvent event) {
        String s = ((ImageView)event.getSource()).getImage().getUrl();
    }

    @FXML
    private void destinationIsland(MouseEvent event) {

    }

    /*First click on the source of the drag.
    *It saves in the dragboard the Id of the node that is being moved (Source).
    */
    @FXML
    private void startDrag(MouseEvent event) {
        Dragboard db = ((Node)event.getSource()).startDragAndDrop(TransferMode.MOVE);

        ClipboardContent content = new ClipboardContent();
        String url = ((ImageView) event.getSource()).getImage().getUrl();
        String s = RedirectResources.fromURLtoElement(url);

        content.putString(s);
        db.setContent(content);

        event.consume();
    }

    /*Target node knows what to accept depending on the TransferMode*/
    @FXML
    private void dragOver(DragEvent event) {
        if (event.getGestureSource() != event.getSource() &&
                event.getDragboard().hasString()) {
            /* allow for both copying and moving, whatever user chooses */
            event.acceptTransferModes(TransferMode.MOVE);
        }

        event.consume();
    }

    /*Graphic feedback of the accessible destination
     * for the element that's being moved
     * */
    @FXML
    private void hover(DragEvent event) {
        /* the drag-and-drop gesture entered the target */
        /* show to the user that it is an actual gesture target */
        if (event.getGestureSource() != event.getSource() &&
                event.getDragboard().hasString()) {
            ((Node)event.getSource()).setBlendMode(BlendMode.SOFT_LIGHT);
        }

        event.consume();
    }

    /*Graphic feedback of when the mouse leaves the possible destination*/
    @FXML
    private void exitHover(DragEvent event) {
        /* mouse moved away, remove the graphical cues */
        ((Node)event.getSource()).setBlendMode(BlendMode.SRC_OVER);

        event.consume();

    }

    @FXML
    private void dragDrop(DragEvent event) {
        /* data dropped */
        /* if there is a string data on dragboard, read it and use it */
        Dragboard db = event.getDragboard();
        boolean success = false;
        if(((Node)event.getSource()).getId().startsWith("ISLAND")) {
            if(((Node)event.getGestureSource()).getParent().getId().startsWith("ENTRANCE")) {
                String action = "Move S from E to I";
                String islandIndex = ((Node)event.getSource()).getId().replaceAll("\\D", "");
                int actualIsland = GUI.getInstance().getIslandModelIndex(Integer.parseInt(islandIndex));
                if(actualIsland == -1) {
                    System.out.println("Selected a nonvalid island");
                    return;
                }

                dealWithAction(action, db.getString(), Integer.toString(actualIsland));
            }
            else if (((Node)event.getGestureSource()).getId().startsWith("SCC")) {
                String action = "Move S from CC to I";
                //dealWithAction(action, );
            }
            else if (((Node)event.getGestureSource()).getParent().getId().startsWith("ISLAND")) {
                String action = "Move MN";
                String currIndex = ((Node) event.getSource()).getId().replaceAll("\\D", "");
                String prevIndex = ((Node) event.getGestureSource()).getId().replaceAll("\\D", "");
                dealWithAction(action, currIndex, prevIndex);
            }
        }
        else if (((Node)event.getSource()).getId().startsWith("DININGROOM")) {
            if (((Node)event.getGestureSource()).getParent().getId().startsWith("ENTRANCE")) {
                String action = "Move S from E to DR";
                dealWithAction(action, db.getString(), null);
            }
            else if (((Node)event.getGestureSource()).getId().startsWith("SCC")) {
                String action = "Move S from CC to DR";
            }

        }
        if (db.hasString()) {
            //event.getGestureSource();
            //deve chiamare la funzione che faccia quello che deve fare -> cambiare numero studenti etc
            //((Node)event.getSource());
            success = true;
        }
        /* let the source know whether the string was successfully
         * transferred and used */
        event.setDropCompleted(success);

        event.consume();
    }

    @FXML
    private void dragDone(DragEvent event) {
        /* the drag and drop gesture ended */
        /* if the data was successfully moved, clear it */
        if (event.getTransferMode() == TransferMode.MOVE) {
            ((Node)event.getSource()).setVisible(false);
        }
        event.consume();
    }

    @FXML
    private void chooseCloud(MouseEvent event) {
        String action = "Choose cloud";
        String cloudIndex = ((Node)event.getSource()).getId().replaceAll("\\D", "");
        dealWithAction(action, cloudIndex, null);
    }

    private void dealWithAction(String action, String param1, String param2) {
        GameEvent gameEvent;

        switch (action) {
            case "Play AC" -> {
                int index = Integer.parseInt(param1);
                gameEvent = new PlayAssistantCardEvent(AssistantCard.values()[index - 1], cc.getPlayingPlayer());
            }
            case "Move S from E to I" -> {
                Color color = Color.valueOf(param1);
                int islandIndex = Integer.parseInt(param2);
                gameEvent = new MoveStudentFromEntranceToIslandEvent(color, islandIndex, cc.getPlayingPlayer());
            }
            case "Move S from E to DR" -> {
                Color color = Color.valueOf(param1);
                gameEvent = new MoveStudentFromEntranceToTableEvent(color, cc.getPlayingPlayer());
            }
            case "Move MN" -> {
                int currIndex = Integer.parseInt(param1);
                int prevIndex = Integer.parseInt(param2);
                gameEvent = new MoveMotherNatureEvent(currIndex-prevIndex, cc.getPlayingPlayer());
            }
            case "Choose cloud" -> {
                int cloudIndex = Integer.parseInt(param1);
                gameEvent = new ChooseCloudTileEvent(cloudIndex, cc.getPlayingPlayer());
            }
            /*case "Activate CC" -> {
                gameEvent = new ActivateCharacterCardEvent();
            }*/
            default -> gameEvent = null;

        }

        if (gameEvent != null) {
            Message answer = cc.performEvent(gameEvent);
            reprint();
            if(answer.getMessageType() == MessageType.NACK){
                MESSAGES.setText(((Nack) answer).getErrorMessage());
                MESSAGES.setVisible(true);
            }
        }
    }

    public void merge(int secondIsland) {
        ISLANDS.getChildren().stream().filter(AnchorPane.class::isInstance).forEach(b -> {
            int currentIsland = Integer.parseInt(b.getId().replaceAll("\\D", ""));
            if(currentIsland == secondIsland) {
                b.setVisible(false);
                b.setDisable(true);
            }
        });
    }
}
