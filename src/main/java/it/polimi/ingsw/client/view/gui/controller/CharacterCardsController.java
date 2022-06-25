package it.polimi.ingsw.client.view.gui.controller;

import it.polimi.ingsw.client.ClientController;
import it.polimi.ingsw.client.view.gui.GUI;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.util.ResourceBundle;

public class CharacterCardsController implements Initializable {
    private ClientController cc = GUI.getInstance().getClientController();


    @FXML
    private ImageView DescCC;

    @FXML
    private Label DESCRIPTIONCC;

    @FXML
    private Button ACTIVATECC;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ACTIVATECC.setText("Activate");
        DESCRIPTIONCC.setText("buh");

    }


    public void activateCC(ActionEvent actionEvent) {

    }
}
