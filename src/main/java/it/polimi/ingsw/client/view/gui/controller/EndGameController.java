package it.polimi.ingsw.client.view.gui.controller;

import it.polimi.ingsw.client.ClientController;
import it.polimi.ingsw.client.view.gui.GUI;
import it.polimi.ingsw.messages.EndGame;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Handles the initialization of EndGame.FXML.
 * It shows either a "win", "lose" or "tie" background depending on the current player status.
 * From this scene, the user can decide to go back to the lobby or to logout from the game.
 */
public class EndGameController implements Initializable {

    @FXML
    private ImageView BG;

    private static EndGame message;


    /**
     * Called to initialize a controller after its root element has been completely processed.
     * Initializes the scene by setting the correct background
     * @param url The location used to resolve relative paths for the root object, or null if the location is not known.
     * @param resourceBundle The resources used to localize the root object, or null if the root object was not localized.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        String[] winners;
        if(message == null) {
            winners = new String[1];
        } else {
            winners = message.winners();
        }
        ClientController cc = GUI.getInstance().getClientController();

        Image image = new Image(String.valueOf(getClass().getResource("/images/Elements/EndSceneLoss.png")));

        for (String winner : winners) {
            if (winner.equals(GUI.getInstance().getUsername()))
                image = new Image(String.valueOf(getClass().getResource("/images/Elements/EndSceneWin.png")));
        }

        if(winners.length == cc.getPlayers().length)
            image = new Image(String.valueOf(getClass().getResource("/images/Elements/EndSceneTie.png")));

        BG.setImage(image);
    }

    /**
     * Switches to the StartMenu scene in order to create or join a new game.
     * @param event button click
     * @throws IOException handles FXMLLoader's possible exception
     */
    public void returnToLobby(ActionEvent event) throws IOException {
        GUI.getInstance().changeScene("/fxml/StartMenu.fxml", 477, 477);
        GUI.getInstance().setSetScene(false);
        GUI.getInstance().getClientController().startOver();
        event.consume();
    }

    /**
     * Logs out of the game.
     * @param event button click
     * @throws IOException handles FXMLLoader's possible exception
     */
    public void logout(ActionEvent event) throws IOException {
        GUI.getInstance().getClientController().logout();
        GUI.getInstance().changeScene("/fxml/Connection.fxml", 477, 477);
        GUI.getInstance().setSetScene(false);
        GUI.getInstance().getClientController().logout();
        GUI.getInstance().getClientController().startOver();
        event.consume();
    }

    /**
     * Set static value message with endGame message
     * @param message The endGame message
     */
    public static void setEndgame(EndGame message){
        EndGameController.message = message;
    }
}
