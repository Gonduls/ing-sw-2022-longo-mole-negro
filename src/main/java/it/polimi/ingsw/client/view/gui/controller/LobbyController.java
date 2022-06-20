package it.polimi.ingsw.client.view.gui.controller;

import it.polimi.ingsw.client.view.gui.GUI;
import it.polimi.ingsw.server.RoomInfo;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class LobbyController implements Initializable {
    private Parent root;
    private Stage stage;
    private Scene scene;

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
        roomInfoList = rooms;

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
            }
        });
    }

    public void enterGame(ActionEvent actionEvent) {
        if(GUI.getInstance().getClientController().accessRoom(roomIDChosen)) {
            GUI.getInstance().setInARoom(true);
        }
        else
            System.out.println("error entering room");
    }

}
