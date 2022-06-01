package it.polimi.ingsw.client.view.gui.controller;

import it.polimi.ingsw.client.view.gui.GUI;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class ConnectionController {

    private Parent root;
    private Scene scene;
    private Stage stage;

    @FXML
    private ImageView myImageView;

    @FXML
    private AnchorPane anchorTextButton;

    @FXML
    private Label labelServerIP;

    @FXML
    private TextField textServerIP;

    @FXML
    private Label labelServerPort;

    @FXML
    private TextField textServerPort;

    @FXML
    private Button continueButton;

    public void switchToLoginScene(ActionEvent event) throws IOException {
        //Image image = new Image(String.valueOf(getClass().getResource("/images/Elements/Eriantys.png")));

        try{
            //myImageView.setImage(image);

            GUI.getInstance().createClientController(textServerIP.getText(), Integer.parseInt(textServerPort.getText()));


        }catch (IOException e){
            return;

        }

        root = FXMLLoader.load(getClass().getResource("/fxml/Login.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene((root));
        stage.setScene(scene);
        stage.show();
    }



}
