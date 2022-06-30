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

/**
 * Handles the initialization of the FilterSearch.FXMLL.
 * It allows the player to filter through available games.
 */
public class FilterSearchController implements Initializable {
    private Parent root;
    private Stage stage;
    private Scene scene;

    @FXML
    private Spinner<Integer> mySpinner;

    @FXML
    private CheckBox checkExpert;


    /**
     * Initializes the filters' fields.
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(2,4);
        mySpinner.setValueFactory(valueFactory);

    }

    /**
     * Calls GetPublicRooms with the filters chosen by the user.
     * Then switches scene to the Lobby.
     * @param event button click
     * @throws IOException handles FXMLLoader's possible exception
     */
    public void switchToLobby(ActionEvent event) throws IOException {
        GetPublicRooms publicRooms = new GetPublicRooms(mySpinner.getValue(), checkExpert.isSelected());
        GUI.getInstance().getClientController().getPublicRooms(publicRooms);
        root = FXMLLoader.load(getClass().getResource("/fxml/Lobby.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene((root));
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Switches to the Lobby scene without applying any filter.
     * @param event button click
     * @throws IOException handles FXMLLoader's possible exception
     */
    public void switchToLobbyNoFilters(ActionEvent event) throws IOException {
        GUI.getInstance().getClientController().getPublicRooms(new GetPublicRooms());
        root = FXMLLoader.load(getClass().getResource("/fxml/Lobby.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene((root));
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Switches back to the previous scene.
     * @param event button click
     * @throws IOException handles FXMLLoader's possible exception
     */
    public void returnToPreviousScene(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("/fxml/StartMenu.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene((root));
        stage.setScene(scene);
        stage.show();

    }
}
