package it.polimi.ingsw.client.view.gui.controller;

import it.polimi.ingsw.client.view.gui.GUI;
import it.polimi.ingsw.server.RoomInfo;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

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
    private Label myRoom;

    @FXML
    private Button enterGame;

    private static List<RoomInfo> roomInfoList = new ArrayList<>();

    private static Integer roomIDChosen;

    public void setPublicRooms(List<RoomInfo> rooms) {
        System.out.print(rooms);
        roomInfoList = rooms;
        System.out.print(roomInfoList);
        System.out.println(this);

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        for(RoomInfo info : roomInfoList){
            roomInfoListView.getItems().add("ID: " + info.getId() + "   Players: " + info.getCurrentPlayers() + "/" + info.getNumberOfPlayers() +
                    "   Expert: " + info.getExpert());
        }

        roomInfoListView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {

            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                roomIDChosen = Integer.parseInt(roomInfoListView.getSelectionModel().getSelectedItem().substring(4,8));
                System.out.print(roomIDChosen);
                enterGame(roomIDChosen);
            }
        });
    }

    public void enterGame(int roomID) {
        GUI.getInstance().getClientController().accessRoom(roomID);

        //GUI.getInstance().getClientController().accessRoom();
    }

}
