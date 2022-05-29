package it.polimi.ingsw.client.view.gui.controller;

import it.polimi.ingsw.messages.Message;
import it.polimi.ingsw.messages.PublicRooms;
import it.polimi.ingsw.server.RoomInfo;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class LobbyController implements Initializable {
    @FXML
    private ListView<PublicRooms> roomInfoListView;

    @FXML
    private Label myRoom;

    //PublicRooms pbRooms = new PublicRooms();
    //List<RoomInfo> rooms = pbRooms.getRooms();

    //TODO: non so bene da dove arriva la lista di Roominfo

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //roomInfoListView.getItems().addAll(rooms);
        //probabilmente serve un toString

    }
}
