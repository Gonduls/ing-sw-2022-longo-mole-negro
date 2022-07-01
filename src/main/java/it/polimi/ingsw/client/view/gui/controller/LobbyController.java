package it.polimi.ingsw.client.view.gui.controller;

import it.polimi.ingsw.client.view.gui.GUI;
import it.polimi.ingsw.server.RoomInfo;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Handles the initialization of Lobby.FXML.
 * It shows a list of currently available games from which the user can choose which one to enter.
 */
public class LobbyController implements Initializable {

    static LobbyController instance;
    public static LobbyController getInstance(){
        if(instance == null)
            instance = new LobbyController();

        return instance;
    }

    @FXML
    private ListView<String> roomInfoListView;

    @FXML
    private Button enterGame;

    private static List<RoomInfo> roomInfoList = new ArrayList<>();

    private static Integer roomIDChosen;

    /**
     * Sets the local var that holds the PublicRoomsList to the given list of rooms
     * @param rooms the list of rooms
     */
    public void setPublicRooms(List<RoomInfo> rooms) {
        roomInfoList = rooms;
    }

    /**
     * Called to initialize a controller after its root element has been completely processed.
     * It shows all the information for each available Room.
     * @param url The location used to resolve relative paths for the root object, or null if the location is not known.
     * @param resourceBundle The resources used to localize the root object, or null if the root object was not localized.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        for(RoomInfo info : roomInfoList){
            roomInfoListView.getItems().add("ID: " + info.getId() + "   Players: " + info.getCurrentPlayers() + "/" + info.getNumberOfPlayers() +
                    "   Expert: " + info.getExpert());
        }

        roomInfoListView.getSelectionModel().selectedItemProperty().addListener((observableValue, s, t1) -> roomIDChosen = Integer.parseInt(roomInfoListView.getSelectionModel().getSelectedItem().substring(4,8)));
    }

    /**
     * It tries to access the chosen room.
     * It prints an error in case it wasn't possible to access the Room, else it waits for the other players to join.
     * @param actionEvent button click
     */
    public void enterGame(ActionEvent actionEvent) {
        if(GUI.getInstance().getClientController().accessRoom(roomIDChosen)) {
            GUI.setInARoom(true);
            GUI.getInstance().refresh();
            enterGame.setVisible(false);
            enterGame.setDisable(true);
        }
        else
            System.out.println("error entering room");
    }

    /**
     * Switches back to the previous scene
     * @param event button click
     * @throws IOException handles FXMLLoader's possible exception
     */
    public void returnToPreviousScene(ActionEvent event) throws IOException {
        GUI.getInstance().changeScene("/fxml/FilterSearch.fxml", 500, 477);
    }
}
