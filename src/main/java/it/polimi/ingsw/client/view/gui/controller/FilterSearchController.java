package it.polimi.ingsw.client.view.gui.controller;

import it.polimi.ingsw.client.view.gui.GUI;
import it.polimi.ingsw.messages.GetPublicRooms;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Handles the initialization of the FilterSearch.FXMLL.
 * It allows the player to filter through available games.
 */
public class FilterSearchController implements Initializable {
    @FXML
    private Spinner<Integer> mySpinner;

    @FXML
    private CheckBox checkExpert;


    /**
     * Called to initialize a controller after its root element has been completely processed.
     * Initializes the filters' fields.
     * @param url  The location used to resolve relative paths for the root object, or null if the location is not known.
     * @param resourceBundle The resources used to localize the root object, or null if the root object was not localized.
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
        GUI.getInstance().changeScene("/fxml/Lobby.fxml", 477, 477);
    }

    /**
     * Switches to the Lobby scene without applying any filter.
     * @param event button click
     * @throws IOException handles FXMLLoader's possible exception
     */
    public void switchToLobbyNoFilters(ActionEvent event) throws IOException {
        GUI.getInstance().getClientController().getPublicRooms(new GetPublicRooms());
        GUI.getInstance().changeScene("/fxml/Lobby.fxml", 477, 477);
    }

    /**
     * Switches back to the previous scene.
     * @param event button click
     * @throws IOException handles FXMLLoader's possible exception
     */
    public void returnToPreviousScene(ActionEvent event) throws IOException {
        GUI.getInstance().changeScene("/fxml/StartMenu.fxml", 477, 477);
    }
}
