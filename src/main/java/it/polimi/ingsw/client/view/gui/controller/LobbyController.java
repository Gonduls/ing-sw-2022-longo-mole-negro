package it.polimi.ingsw.client.view.gui.controller;

import it.polimi.ingsw.server.RoomInfo;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class LobbyController implements Initializable {

    List<RoomInfo> roomInfoList;

    static LobbyController instance;
    public static LobbyController getInstance(){
        if(instance == null)
            instance = new LobbyController();

        return instance;
    }

    @FXML
    private ListView<String> roomInfoListView;

    @FXML
    private Label myRoom;




    //TODO: non so bene da dove arriva la lista di Roominfo

    public void setPublicRooms(List<RoomInfo> rooms) {
        System.out.print(rooms);
        //System.out.print(roomInfo);


    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //GetPublicRooms getPublicRooms = new GetPublicRooms();
        //GUI.getInstance().getClientController().getPublicRooms(getPublicRooms);
        roomInfoListView.getItems().addAll(roomInfoList.toString());
    }
}
