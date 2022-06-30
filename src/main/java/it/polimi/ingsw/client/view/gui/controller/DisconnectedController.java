package it.polimi.ingsw.client.view.gui.controller;

        import it.polimi.ingsw.client.view.gui.GUI;
        import it.polimi.ingsw.messages.PlayerDisconnect;
        import javafx.event.ActionEvent;
        import javafx.fxml.FXML;
        import javafx.fxml.Initializable;
        import javafx.scene.control.Label;

        import java.io.IOException;
        import java.net.URL;
        import java.util.ResourceBundle;

public class DisconnectedController implements Initializable {
    private static PlayerDisconnect message;

    @FXML
    private Label MESSAGES;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if(message == null) return;
        MESSAGES.setText("Player " + message.username() + " has left the room");
    }

    public void returnToLobby(ActionEvent event) throws IOException {
        GUI.getInstance().changeScene("/fxml/StartMenu.fxml", 500, 477);
        GUI.getInstance().setSetScene(false);
        GUI.getInstance().getClientController().startOver();
    }

    public void logout(ActionEvent event) throws IOException{
        GUI.getInstance().changeScene("/fxml/Connection.fxml", 500, 477);
        GUI.getInstance().setSetScene(false);
        GUI.getInstance().getClientController().logout();
        GUI.getInstance().getClientController().startOver();
    }

    public static void setDisconnectMessage(PlayerDisconnect message){
        DisconnectedController.message = message;
    }
}
