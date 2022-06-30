package it.polimi.ingsw.client.view.gui.controller;

import it.polimi.ingsw.client.ClientController;
import it.polimi.ingsw.client.ClientModelManager;
import it.polimi.ingsw.client.view.gui.GUI;
import it.polimi.ingsw.messages.EndGame;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class EndGameController implements Initializable {
    private Parent root;
    private Stage stage;
    private Scene scene;

    @FXML
    private ImageView BG;

    ClientController cc = GUI.getInstance().getClientController();
    ClientModelManager cmm = GUI.getInstance().getClientModelManager();


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        String[] winners = GUI.getInstance().getWinners();

        for (int i = 0; i < winners.length; i++) {
            Image image;
            if(cc.getPlayers()[cc.getPlayingPlayer()] == GUI.getInstance().getUsername() && winners.length == 1)
                image = new Image(String.valueOf(getClass().getResource("/images/Elements/EndSceneWin.png")));
            else if (winners.length == cmm.getPlayers().length)
                image = new Image(String.valueOf(getClass().getResource("/images/Elements/EndSceneTie.png")));
            else
                image = new Image(String.valueOf(getClass().getResource("/images/Elements/EndSceneLoss.png")));

            BG.setImage(image);

        }
    }

    public void returnToLobby(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("/fxml/StartMenu.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene((root));
        stage.setScene(scene);
        stage.show();

    }

    public void logout(ActionEvent event) throws IOException {
        GUI.getInstance().getClientController().logout();
        root = FXMLLoader.load(getClass().getResource("/fxml/Connection.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene((root));
        stage.setScene(scene);
        stage.show();
    }


}
