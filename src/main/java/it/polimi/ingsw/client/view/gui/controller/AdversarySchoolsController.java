package it.polimi.ingsw.client.view.gui.controller;

import it.polimi.ingsw.client.ClientController;
import it.polimi.ingsw.client.ClientModelManager;
import it.polimi.ingsw.client.view.gui.GUI;
import it.polimi.ingsw.client.view.gui.RedirectResources;
import it.polimi.ingsw.model.Color;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Map;
import java.util.Objects;
import java.util.ResourceBundle;

public class AdversarySchoolsController implements Initializable {
    Parent root;
    Stage stage;
    Scene scene;
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

    @FXML
    private AnchorPane SCHOOL2;

    @FXML
    private AnchorPane SCHOOL3;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        //enables and links adversaries school boards depending on the number of players
        /*switch (numberOfPlayers) {
            case (2) -> {
                SCHOOL2.setVisible(false);
                SCHOOL2.setDisable(true);
                SCHOOL3.setVisible(false);
                SCHOOL3.setDisable(true);
            }
            case (3) -> {
                SCHOOL3.setVisible(false);
                SCHOOL3.setDisable(true);
            }
        }*/

        ADVERSARYBOARD.getChildren().stream().filter(AnchorPane.class::isInstance).forEach(this::setSchool);

    }

    private void setSchool(Node node) {

        if(playerIndex >= cmm.getPlayers().length) {
            node.setVisible(false);
            return;
        }

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
                if(cc.getPlayingPlayer() == playerIndex) {
                    x.setVisible(true);
                }
            }
            else if (x.getId().startsWith("COINNUM")) {
                int coins = cmm.getCoins(playerIndex);
                ((Label)x).setText(String.valueOf(coins));
            }
        });

        playerIndex++;

    }

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

    private void setNumberOfStudents(Map<Color, Integer> entrance) {
        numberOfReds = entrance.get(Color.RED);
        numberOfBlues = entrance.get(Color.BLUE);
        numberOfGreens = entrance.get(Color.GREEN);
        numberOfYellows = entrance.get(Color.YELLOW);
        numberOfPinks = entrance.get(Color.PINK);
    }

    private void setProfessors(Node node) {
        String[] s = node.getId().split("_");
        if (cmm.getProfessors().get(Color.valueOf(s[0])) == playerIndex)
            node.setVisible(true);
    }

    private void setDiningRoom(Node node) {
        if (iteratorDR < currentDRColor) {
            node.setVisible(true);
        }

        iteratorDR++;

        if (iteratorDR == 10) {
            iteratorDR = 0;
        }
    }

    @FXML
    private void returnToGame(MouseEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("/fxml/UpdatedGameBoard.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene((root));
        stage.setScene(scene);
        stage.show();
    }
}
