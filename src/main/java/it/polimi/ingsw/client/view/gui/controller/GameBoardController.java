package it.polimi.ingsw.client.view.gui.controller;


import it.polimi.ingsw.client.ClientController;
import it.polimi.ingsw.client.ClientModelManager;
import it.polimi.ingsw.client.view.gui.GUI;
import it.polimi.ingsw.client.view.gui.RedirectResources;
import it.polimi.ingsw.controller.GamePhase;
import it.polimi.ingsw.messages.GameEvent;
import it.polimi.ingsw.messages.Message;
import it.polimi.ingsw.messages.MessageType;
import it.polimi.ingsw.messages.Nack;
import it.polimi.ingsw.messages.events.*;
import it.polimi.ingsw.model.AssistantCard;
import it.polimi.ingsw.model.CharacterCard;
import it.polimi.ingsw.model.Color;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.BlendMode;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.*;

public class GameBoardController implements Initializable {
    private static GameBoardController instance;
    public static GameBoardController getInstance(){
        if(instance == null)
            instance = new GameBoardController();

        return instance;
    }

    Parent root;
    Stage stage;
    Scene scene;

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
    @FXML
    private Label NACKLABEL;
    @FXML
    private Button ENDACTION;
    @FXML
    private Label USERNAME;
    @FXML
    private Label PHASE;
    @FXML
    private ImageView TURN;

    //used to change images of students
    private int numberOfReds = 0;
    private int numberOfBlues = 0;
    private int numberOfGreens = 0;
    private int numberOfYellows = 0;
    private int numberOfPinks = 0;

    private int currentDRColor = 0;
    private int iteratorDR = 0;

    private int iteratorTowers = 0;


    //getting an instance of ClientModelManager and ClientController from an instance of GUI in order to initialize the game
    private final ClientModelManager cmm = GUI.getInstance().getClientModelManager();
    private final ClientController cc = GUI.getInstance().getClientController();
    private final int numberOfPlayers = cmm.getPlayers().length;
    private final boolean expert = cmm.isExpert();

    private int studentNumber = 0;
    private Integer[] indexes;
    private int indexesIterator = 0;

    private ArrayList<Image> deck;
    private int ACindex;
    private static int choosenCCindex  = 0;
    private final String[] swap = new String[2];

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        instance = new GameBoardController();
        instance.BOARD = this.BOARD;
        instance.ISLANDS = this.ISLANDS;
        instance.COIN = this.COIN;
        instance.OWNEDCOINS = this.OWNEDCOINS;
        instance.ASSISTANTCARD = this.ASSISTANTCARD;
        instance.ASSISTANTCARDDECK = this.ASSISTANTCARDDECK;
        instance.CHARACTERCARDS = this.CHARACTERCARDS;
        instance.NACKLABEL = this.NACKLABEL;
        instance.MESSAGES = this.MESSAGES;
        instance.ENDACTION = this.ENDACTION;
        instance.PHASE = this.PHASE;
        instance.TURN = this.TURN;
        instance.USERNAME = this.USERNAME;

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
        }

        initializeDeck();
        instance.reprint();
    }

    public void reprint() {
        // Avoid throwing IllegalStateException by running from a non-JavaFX thread.
        initializeDeck();
        Platform.runLater( () -> {
                    ENDACTION.setVisible(false);
                    USERNAME.setText(GUI.getInstance().getUsername());
                    cc.getActions().forEach(string -> {
                        if(string.endsWith("End selections"))
                            ENDACTION.setVisible(true);

                    });
                    TURN.setVisible(Objects.equals(cc.getPlayers()[cc.getPlayingPlayer()], GUI.getInstance().getUsername()));
                    PHASE.setText(changePhaseName(cc.getPhase()));
                    BOARD.getChildren().stream().filter(AnchorPane.class::isInstance).forEach(this::setBoard);
                    showAssistantCard();
                    if(cc.getCardActions() > 0){
                        MESSAGES.setVisible(true);
                        switch (cc.getActiveCharacterCard()){
                            case 0 -> MESSAGES.setText("Move a student from the activated card to any island");
                            case 2 -> MESSAGES.setText("Click a student in the activated card and in the entrance of your school to swap them, click end selection to stop using this card effect");
                            case 3 -> MESSAGES.setText("Click a student in your dining room and in the entrance of your school to swap them, click end selection to stop using this card effect");
                            case 5 -> MESSAGES.setText("Click on any island to place a no entry");
                            case 7 -> MESSAGES.setText("Move a student from the activated card to your dining room");
                            case 8 -> MESSAGES.setText("Click on any island to calculate influence");
                            default -> MESSAGES.setVisible(false);
                        }
                    } else {
                        MESSAGES.setVisible(false);
                    }
                }
        );
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

        }
        OWNEDCOINS.setText(String.valueOf(cmm.getCoins(getThisPlayerIndex())));
        OWNEDCOINS.setVisible(true);

    }

    // if no students of that color are present => don't show student piece
    public void setStudentPieceInIsland(Node node, Color color, int islandIndex){
        int num = cmm.getIslands().get(islandIndex).getStudents().get(color);
        node.setVisible(num != 0);
    }

    // if non number is needed for that color => don't show number, else set number
    public void setStudentNumberInIsland(Node node, Color color, int islandIndex){
        int num = cmm.getIslands().get(islandIndex).getStudents().get(color);
        if(num < 2) {
            node.setVisible(false);
            return;
        }
        node.setVisible(true);
        ((Label) node).setText(Integer.toString(num));
    }

    //Sets the correct number of clouds per number of players
    private void setClouds(Node node) {
        int cloudIndex = Integer.parseInt(node.getId().replaceAll("\\D", ""));

        if(cloudIndex >= numberOfPlayers)
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
            default -> {
                return;
            }
        }

        int currentTowerNumber = cmm.getTowers(getThisPlayerIndex());
        if (iteratorTowers < currentTowerNumber) {
            node.setVisible(true);
            iteratorTowers++;
        } else {
            node.setVisible(false);
            node.setDisable(true);
        }
    }

    //Checks and shows the professors a Player has
    private void setProfessors(Node node) {
        node.setVisible(cmm.getProfessors().get(Color.valueOf(node.getId())) == getThisPlayerIndex());
    }

    //Shows the state of the DiningRoom
    private void setDiningRoom(Node node) {
        node.setVisible(iteratorDR < currentDRColor);

        iteratorDR++;

        if (iteratorDR == 10) {
            iteratorDR = 0;
        }
    }

    //Sets the chosen character cards with their respective images and SHs
    private void setCharacterCards(Node node) {
        if(indexes == null)
            indexes = cmm.getCharactersIndexes().toArray(Integer[]::new);

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
            } else if(indexes[indexesIterator] == 5) {
                System.out.println("Helooooo");
                //If the card is the fifth, swaps students with no entry tiles
                node.setVisible(true);
                node.setDisable(false);
                ((AnchorPane) node).getChildren().stream().filter(ImageView.class::isInstance).forEach(
                        b -> {
                            System.out.println(b.getId());
                            int number = Integer.parseInt(b.getId().substring(4));
                            b.setVisible(number <= cmm.getNoEntries());
                            ((ImageView) b).setImage(RedirectResources.getNoEntryImage());});

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
    private void showAssistantCard() {
        if(deck.isEmpty()) return;
        ASSISTANTCARD.setImage(deck.get((ACindex) % deck.size()));
    }


    public void notExpert(Node node) {
        if (node.getId().startsWith("NOENTRY")) {
            node.setVisible(false);
            node.setDisable(true);
        }
    }

    //Sets the correct image for the Students when they're chosen randomly
    public void setStudents(Node node) {
        node.setVisible(true);
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

    // Returns a more user-friendly phase name
    private String changePhaseName(GamePhase phase){
        return switch (phase){
            case PLANNING_PHASE -> "Planning";
            case ACTION_PHASE_ONE -> "Move Students";
            case ACTION_PHASE_TWO -> "Move Mother Nature";
            case ACTION_PHASE_THREE -> "Choose Cloud";
        };
    }


    //All functions called to manage player's actions

    //Shows the next Assistant Card
    @FXML
    private void nextAssistantCard(MouseEvent e) {
        if(ACindex == deck.size() -1) {
            ACindex = -1;
        }
        ACindex++;
        showAssistantCard();
        e.consume();
    }

    //Shows previous Assistant Card
    @FXML
    private void prevAssistantCard(MouseEvent event) {
        if(ACindex == 0) {
            ACindex = deck.size();
        }
        ACindex--;
        showAssistantCard();
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
        showAssistantCard();
    }

    @FXML
    private void studentSelected(MouseEvent event) {
        String url = ((ImageView)event.getSource()).getImage().getUrl();
        int card = cc.getActiveCharacterCard();
        if(card == 2 || card == 3)
            instance.putSwap(RedirectResources.fromURLtoElement(url), ((ImageView) event.getSource()).getId());
    }

    @FXML
    private void chooseIsland(MouseEvent event) {
        String id = ((AnchorPane)event.getSource()).getId();
        int islandIdx = Integer.parseInt((id.replaceAll("\\D", "")));
        int actualIsland = GUI.getInstance().getIslandModelIndex(islandIdx);
        GameEvent gameEvent = new ChooseIslandEvent(actualIsland, cc.getPlayingPlayer());

        Message answer = cc.performEvent(gameEvent);
        if (answer.getMessageType() == MessageType.NACK) {
            NACKLABEL.setText(((Nack) answer).errorMessage());
            NACKLABEL.setVisible(true);
        }
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

                dealWithAction(action, db.getString(), Integer.toString(actualIsland));
            }
            else if (((Node)event.getGestureSource()).getId().startsWith("SCC")) {
                if(db.getString().equals("NOENTRY")) {
                    String action = "Move NE from CC to I";
                    String islandIndex = ((Node) event.getSource()).getId().replaceAll("\\D", "");
                    int actualIsland = GUI.getInstance().getIslandModelIndex(Integer.parseInt(islandIndex));
                    dealWithAction(action, Integer.toString(actualIsland), null);
                } else {
                    String action = "Move S from CC to I";
                    String islandIndex = ((Node) event.getSource()).getId().replaceAll("\\D", "");
                    int actualIsland = GUI.getInstance().getIslandModelIndex(Integer.parseInt(islandIndex));
                    dealWithAction(action, db.getString(), Integer.toString(actualIsland));
                }
            }
            else if (((Node)event.getGestureSource()).getParent().getId().startsWith("ISLAND")) {
                String action = "Move MN";
                String currIndex = ((Node) event.getSource()).getId().replaceAll("\\D", "");
                String prevIndex = ((Node) event.getGestureSource()).getId().replaceAll("\\D", "");
                int actualCurrIsland = GUI.getInstance().getIslandModelIndex(Integer.parseInt(currIndex));
                int actualPrevIsland = GUI.getInstance().getIslandModelIndex(Integer.parseInt(prevIndex));
                dealWithAction(action, Integer.toString(actualCurrIsland), Integer.toString(actualPrevIsland));
            }
        }
        else if (((Node)event.getSource()).getId().startsWith("DININGROOM")) {
            if (((Node)event.getGestureSource()).getParent().getId().startsWith("ENTRANCE")) {
                String action = "Move S from E to DR";
                dealWithAction(action, db.getString(), null);
            }
            else if (((Node)event.getGestureSource()).getId().startsWith("SCC")) {
                String action = "Move S from CC to DR";
                dealWithAction(action, db.getString(),null);
            }

        }
        if (db.hasString()) {
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
        reprint();
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
                int amount = currIndex-prevIndex;
                amount = amount > 0 ? amount : amount + cmm.getIslands().size();
                gameEvent = new MoveMotherNatureEvent(amount, cc.getPlayingPlayer());
            }
            case "Choose cloud" -> {
                int cloudIndex = Integer.parseInt(param1);
                gameEvent = new ChooseCloudEvent(cloudIndex, cc.getPlayingPlayer());
            }
            case "Move S from CC to DR" -> {
                Color color = Color.valueOf(param1);
                gameEvent = new MoveStudentFromCardToTableEvent(color, cc.getPlayingPlayer());
            }
            case "Move S from CC to I" -> {
                Color color = Color.valueOf(param1);
                gameEvent = new MoveStudentFromCardToIslandEvent(color, Integer.parseInt(param2), cc.getPlayingPlayer());
            }
            case ("Swap X from CC with Y from E") -> {
                Color x = Color.valueOf(param1);
                Color y = Color.valueOf(param2);
                gameEvent = new SwapStudentCardEntranceEvent(x, y, cc.getPlayingPlayer());
            }
            case ("Swap X from E with Y from DR") -> {
                Color x = Color.valueOf(param1);
                Color y = Color.valueOf(param2);
                gameEvent = new SwapStudentEntranceTableEvent(x, y, cc.getPlayingPlayer());
            }
            case "Move NE from CC to I" -> {
                int idx = Integer.parseInt(param1);
                gameEvent = new ChooseIslandEvent(idx, cc.getPlayingPlayer());
            }
            default -> gameEvent = null;

        }

        if (gameEvent != null) {
            Message answer = cc.performEvent(gameEvent);
            reprint();
            if(answer.getMessageType() == MessageType.NACK){
                NACKLABEL.setText(((Nack) answer).errorMessage());
                NACKLABEL.setVisible(true);
            } else {
                NACKLABEL.setVisible(false);
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

    //Picking a CC shows a new scene with that card's info
    public void chooseCC(MouseEvent event) throws IOException {
        String CCurl = ((ImageView)event.getSource()).getImage().getUrl();
        String CCnumber = RedirectResources.fromURLtoElement(CCurl);
        choosenCCindex = Integer.parseInt(CCnumber.replaceAll("\\D", ""));

        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/fxml/CharacterCards.fxml")));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene((root));
        stage.setScene(scene);
        stage.setFullScreen(true);
        stage.setResizable(false);
        stage.setFullScreenExitHint("");
        stage.setHeight(768);
        stage.setWidth(1366);
        stage.show();

    }

    public int getChoosenCC() {
        return choosenCCindex;
    }

    @FXML
    public void openSchools(MouseEvent event) throws IOException{
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/fxml/AdversarySchools.fxml")));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene((root));
        stage.setScene(scene);
        stage.setFullScreen(true);
        stage.setResizable(false);
        stage.setFullScreenExitHint("");
        stage.setHeight(768);
        stage.setWidth(1366);
        stage.show();
    }

    @FXML
    public void endAction() {
        GameEvent gameEvent= new EndSelection(cc.getPlayingPlayer());

        Message answer = cc.performEvent(gameEvent);
        if (answer.getMessageType() == MessageType.NACK) {
            NACKLABEL.setText(((Nack) answer).errorMessage());
            NACKLABEL.setVisible(true);
        } else
            reprint();
    }

    private void putSwap(String color, String id){
        switch (cc.getActiveCharacterCard()){
            case 2 -> {
                swap[0] = id.startsWith("SCC") ? color : swap[0];
                swap[1] = id.startsWith("E_STUDENT_") ? color : swap[1];
                if(swap[0] != null && swap[1] != null){
                    System.out.println(Arrays.toString(swap));
                    dealWithAction("Swap X from CC with Y from E", swap[0], swap[1]);
                    clearSwap();
                }
            }
            case 3 -> {
                swap[0] = id.startsWith("E_STUDENT_") ? color : swap[0];
                swap[1] = id.startsWith("DR_") ? color : swap[1];
                if(swap[0] != null && swap[1] != null){
                    dealWithAction("Swap X from E with Y from DR", swap[0], swap[1]);
                    clearSwap();
                }
            }
            default -> clearSwap();
        }
    }

    public void clearSwap(){
        instance.swap[0] = null;
        instance.swap[1] = null;
    }
}
