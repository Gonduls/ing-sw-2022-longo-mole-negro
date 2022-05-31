package it.polimi.ingsw.client.view.gui.controller;

import it.polimi.ingsw.client.view.gui.GUI;
import it.polimi.ingsw.exceptions.UnexpectedMessageException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginController {
    private Parent root;
    private Stage stage;
    private Scene scene;

    @FXML
    private TextField textUsername;

    @FXML
    private Label unableToLoginId;

    public void switchToStartMenuScene(ActionEvent event) throws IOException {
        try {
            boolean verifiedUsername = GUI.getInstance().getClientController().login(textUsername.getText());
            if(verifiedUsername) {
                root = FXMLLoader.load(getClass().getResource("/fxml/StartMenu.fxml"));
                stage = (Stage)((Node)event.getSource()).getScene().getWindow();
                scene = new Scene((root));
                stage.setScene(scene);
                stage.show();
            }
            else
                unableToLoginId.setVisible(true);

        }catch (UnexpectedMessageException e) {
            //TODO exception
        }

    }
}
