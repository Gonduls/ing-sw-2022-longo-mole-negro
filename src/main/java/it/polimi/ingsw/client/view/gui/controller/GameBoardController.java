package it.polimi.ingsw.client.view.gui.controller;


import it.polimi.ingsw.client.ClientModelManager;
import it.polimi.ingsw.client.view.gui.GUI;
import it.polimi.ingsw.model.Color;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ResourceBundle;

public class GameBoardController implements Initializable {
    @FXML
    private AnchorPane ISLANDS;

    @FXML
    private AnchorPane CLOUDS;



    private ClientModelManager cmm = GUI.getInstance().getClientModelManager();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //setting board for game start
        ISLANDS.getChildren().stream().filter(x -> x instanceof AnchorPane).forEach(this::setBoard);
    }

    private void setBoard(Node node) {
        //todo: conoscere quale nuvola sto prendendo in considerazione
        //chiedi in base a cosa decidiamo di mostrare le nuvole
        //int cloudIndex = cmm.getCloud().get()
        if(node.getId().startsWith("ISLAND"))
            setIsland((AnchorPane) node);
        else if(node.getId().startsWith("CLOUDS")) {
            //(node).getChildren().stream().filter(x -> x instanceof ImageView).forEach(b -> {});

        }


    }

    private void setIsland(AnchorPane node) {
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

}
