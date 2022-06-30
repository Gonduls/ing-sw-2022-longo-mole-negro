package it.polimi.ingsw.client.view.gui.controller;

        import it.polimi.ingsw.client.view.gui.GUI;
        import it.polimi.ingsw.messages.PlayerDisconnect;
        import javafx.event.ActionEvent;
        import javafx.fxml.FXML;
        import javafx.fxml.FXMLLoader;
        import javafx.fxml.Initializable;
        import javafx.scene.Node;
        import javafx.scene.Parent;
        import javafx.scene.Scene;
        import javafx.scene.control.Label;
        import javafx.stage.Stage;

        import java.io.IOException;
        import java.net.URL;
        import java.util.Objects;
        import java.util.ResourceBundle;

public class DisconnectedController implements Initializable {
    private Parent root;
    private Stage stage;
    private Scene scene;
    private static PlayerDisconnect message;

    @FXML
    private Label MESSAGES;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if(message == null) return;
        MESSAGES.setText("Player " + message.username() + " has left the room");
    }

    public void returnToLobby(ActionEvent event) throws IOException {
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/fxml/StartMenu.fxml")));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root, 477.0, 477.0);
        stage.setScene(scene);
        stage.show();
        GUI.getInstance().setSetScene(false);
    }

    public void logout(ActionEvent event) throws IOException {
        GUI.getInstance().getClientController().logout();
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/fxml/Connection.fxml")));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root, 477.0, 477.0);
        stage.setScene(scene);
        stage.show();
        GUI.getInstance().setSetScene(false);
    }

    public static void setDisconnectMessage(PlayerDisconnect message){
        DisconnectedController.message = message;
    }
}
