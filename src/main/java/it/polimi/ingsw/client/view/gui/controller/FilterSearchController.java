package it.polimi.ingsw.client.view.gui.controller;

import it.polimi.ingsw.client.view.gui.GUI;
import it.polimi.ingsw.messages.GetPublicRooms;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class FilterSearchController implements Initializable {
    private Parent root;
    private Stage stage;
    private Scene scene;

    @FXML
    private Spinner<Integer> mySpinner;

    @FXML
    private CheckBox checkExpert;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(2,4);
        mySpinner.setValueFactory(valueFactory);

    }

    public void switchToLobby(ActionEvent event) throws IOException {
        GetPublicRooms publicRooms = new GetPublicRooms(mySpinner.getValue(), checkExpert.isSelected());
        GUI.getInstance().getClientController().getPublicRooms(publicRooms);
        root = FXMLLoader.load(getClass().getResource("/fxml/Lobby.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene((root));
        stage.setScene(scene);
        stage.show();
    }
}
