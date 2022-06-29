package it.polimi.ingsw.client.view.gui.controller;

import it.polimi.ingsw.client.ClientController;
import it.polimi.ingsw.client.view.gui.GUI;
import it.polimi.ingsw.client.view.gui.RedirectResources;
import it.polimi.ingsw.model.CharacterCard;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class CharacterCardsController implements Initializable{
    private ClientController cc = GUI.getInstance().getClientController();

    @FXML
    private ImageView DescCC;

    @FXML
    private Label DESCRIPTIONCC;

    @FXML
    private Button ACTIVATECC;

    Parent root;
    Stage stage;
    Scene scene;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        int choosenCCIndex = GameBoardController.getInstance().getChoosenCC();
        String desc = CharacterCard.description(choosenCCIndex);
        Image CCImage = RedirectResources.characterCardsDescImages(choosenCCIndex);

        DescCC.setImage(CCImage);
        DESCRIPTIONCC.setText(desc);
        ACTIVATECC.setText("Activate");
    }

    public void activateCC(ActionEvent actionEvent) throws IOException {

        root = FXMLLoader.load(getClass().getResource("/fxml/UpdatedGameBoard.fxml"));
        stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        scene = new Scene((root));
        stage.setScene(scene);
        stage.show();

    }

    @FXML
    public void returnToGame(MouseEvent event) throws IOException {

        root = FXMLLoader.load(getClass().getResource("/fxml/UpdatedGameBoard.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene((root));
        stage.setScene(scene);
        stage.show();

    }


}
