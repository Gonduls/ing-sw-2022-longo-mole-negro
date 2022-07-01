package it.polimi.ingsw.client.view.gui.controller;

import it.polimi.ingsw.client.ClientController;
import it.polimi.ingsw.client.ClientModelManager;
import it.polimi.ingsw.client.view.gui.GUI;
import it.polimi.ingsw.client.view.gui.RedirectResources;
import it.polimi.ingsw.model.Color;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.util.Map;
import java.util.Objects;
import java.util.ResourceBundle;

/**
 * Handles the initialization of AdversarySchools.FXML.
 * It shows the schools of all the players, their coins and last played Assistant card.
 */
public class AdversarySchoolsController implements Initializable {
    ClientModelManager cmm = GUI.getInstance().getClientModelManager();
    ClientController cc = GUI.getInstance().getClientController();
    int numberOfPlayers = cmm.getPlayers().length;
    private int playerIndex = 0;

    private int currentDRColor = 0;
    private int iteratorDR = 0;

    private int studentNumber = 0;

    private int numberOfReds = 0;
    private int numberOfBlues = 0;
    private int numberOfGreens = 0;
    private int numberOfYellows = 0;
    private int numberOfPinks = 0;

    private int iteratorTowers = 0;

    @FXML
    private AnchorPane ADVERSARYBOARD;
    private static AnchorPane AB;

    @FXML
    private ImageView bg;

    /**
     * Initializes the entire scene.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        if(numberOfPlayers == 4) {
            Image image = new Image(String.valueOf(getClass().getResource("/images/Elements/BoardTeams.png")));
            bg.setImage(image);
        }

        ADVERSARYBOARD.getChildren().stream().filter(AnchorPane.class::isInstance).forEach(this::setSchool);
        AB = ADVERSARYBOARD;
    }

    public void reprint(){
        if(AB == null)
            return;
        Platform.runLater( () ->
                AB.getChildren().stream().filter(AnchorPane.class::isInstance).forEach(this::setSchool)
        );
    }

    /**
     * Sets every field of the school passed as a parameter
     * @param node The school node
     */
    private void setSchool(Node node) {

        //shows only as many schools as players
        if(playerIndex >= cmm.getPlayers().length) {
            node.setVisible(false);
            return;
        }

        //initializes element by element the school
        ((AnchorPane)node).getChildren().stream().filter(Objects::nonNull).forEach(x -> {
            if (x.getId().startsWith("DR")) {
                ((AnchorPane)x).getChildren().stream().filter(AnchorPane.class::isInstance).forEach(b -> {
                    String[] s = b.getId().split("_");
                    currentDRColor = cmm.getDiningRooms(playerIndex).get(Color.valueOf(s[1]));
                    ((AnchorPane) b).getChildren().stream().filter(ImageView.class::isInstance).forEach(this::setDiningRoom);
                });
            }
            else if (x.getId().startsWith("PROFESSORS"))
                ((AnchorPane) x).getChildren().stream().filter(ImageView.class::isInstance).forEach(this::setProfessors);
            else if (x.getId().startsWith("ENTRANCE")){
                setNumberOfStudents(cmm.getEntrance(playerIndex));
                ((AnchorPane) x).getChildren().stream().filter(ImageView.class::isInstance).forEach(c -> {
                    if(studentNumber > 6 && numberOfPlayers != 3)
                        return;
                    studentNumber++;
                    setStudents(c);
                });
                studentNumber = 0;
            }
            else if (x.getId().startsWith("TOWERS")) {
                ((AnchorPane)x).getChildren().stream().filter(ImageView.class::isInstance).forEach(this::setTowers);
                iteratorTowers = 0;
            }
            else if (x.getId().startsWith("USERNAME")) {
                ((Label)x).setText(cmm.getPlayers()[playerIndex]);
                x.setVisible(true);
            }
            else if (x.getId().startsWith("TURN")) {
                x.setVisible(cc.getPlayingPlayer() == playerIndex);
            }
            else if (x.getId().startsWith("COINNUM")) {
                int coins = cmm.getCoins(playerIndex);
                ((Label)x).setText(String.valueOf(coins));
            }
            else if (x.getId().startsWith("ACPLAYED")) {
                int ACplayed = cc.getAssistantCardsPlayed()[playerIndex];
                if(ACplayed != -1) {
                    Image image = RedirectResources.ACImages(ACplayed);
                    ((ImageView) x).setImage(image);
                    x.setVisible(true);
                }
            }

        });

        playerIndex++;

    }

    /**
     * Chooses the correct color and show the correct number of towers given the player's index.
     * @param node the ImageView of the tower for the given school
     */
    private void setTowers(Node node) {
        switch (playerIndex) {
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

        int currentTowerNumber = cmm.getTowers(playerIndex);

        if (iteratorTowers < currentTowerNumber) {
            node.setVisible(true);
            iteratorTowers++;
        } else {
            node.setVisible(false);
            node.setDisable(true);
        }
    }

    /**
     * Shows the correct number and color of each student for the Entrance
     * @param node the ImageView corresponding to the given student
     */
    private void setStudents(Node node) {
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

    /**
     * Sets the number of students for each color in the student holder given as parameter
     * @param entrance the Map<Color, Integer> of the current school Entrance
     */
    private void setNumberOfStudents(Map<Color, Integer> entrance) {
        numberOfReds = entrance.get(Color.RED);
        numberOfBlues = entrance.get(Color.BLUE);
        numberOfGreens = entrance.get(Color.GREEN);
        numberOfYellows = entrance.get(Color.YELLOW);
        numberOfPinks = entrance.get(Color.PINK);
    }

    /**
     * Shows the currently owned Professors by the given player
     * @param node the ImageView of the corresponding professor
     */
    private void setProfessors(Node node) {
        String[] s = node.getId().split("_");
        if (cmm.getProfessors().get(Color.valueOf(s[0])) == playerIndex)
            node.setVisible(true);
    }

    /**
     * Shows the students currently in the Dining Room
     * @param node the ImageView corresponding to the single student at each Table
     */
    private void setDiningRoom(Node node) {
        if (iteratorDR < currentDRColor) {
            node.setVisible(true);
        }

        iteratorDR++;

        if (iteratorDR == 10) {
            iteratorDR = 0;
        }
    }

    /**
     * Switches back to the GameBoard scene
     * @param event the mouse click on the return button
     * @throws IOException handles FXMLLoader's possible exception
     */
    @FXML
    private void returnToGame(MouseEvent event) throws IOException {
        GUI.getInstance().changeScene("/fxml/UpdatedGameBoard.fxml", 768, 1366);
        event.consume();
    }
}
