package it.polimi.ingsw.client.view.gui.controller;

import it.polimi.ingsw.client.view.gui.GUI;
import it.polimi.ingsw.messages.CreateRoom;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class GameCreationController implements Initializable {
    private Parent root;
    private Stage stage;
    private Scene scene;

    @FXML
    private AnchorPane anchorText;

    @FXML
    private Spinner<Integer> mySpinner;

    @FXML
    private CheckBox checkPrivate;

    @FXML
    private CheckBox checkExpert;

    @FXML
    private Button continueButton;

    @FXML
    private Label errorAccessRoom;

    private static Integer roomID;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(2,4);
        mySpinner.setValueFactory(valueFactory);

    }

    public void generateRoomID(ActionEvent event) {

        boolean expertGame = checkExpert.isSelected();
        boolean privateGame = checkPrivate.isSelected();
        CreateRoom createRoom = new CreateRoom(mySpinner.getValue(), expertGame, privateGame);
        roomID  = (GUI.getInstance().getClientController().createRoom(createRoom));
        if(roomID < 0) {
            errorAccessRoom.setText("Could not access Room. Try Again.");
            errorAccessRoom.setVisible(true);
        } else {
            errorAccessRoom.setText("Room ID: " + roomID + " Waiting for players...");
            errorAccessRoom.setVisible(true);
            GUI.getInstance().setInARoom(true);
            GUI.getInstance().refresh();
        }
    }

}
