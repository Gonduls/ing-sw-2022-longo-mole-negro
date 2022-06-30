package it.polimi.ingsw.client.view.gui.controller;

import it.polimi.ingsw.client.ClientController;
import it.polimi.ingsw.client.ClientModelManager;
import it.polimi.ingsw.client.view.gui.GUI;
import it.polimi.ingsw.client.view.gui.RedirectResources;
import it.polimi.ingsw.messages.GameEvent;
import it.polimi.ingsw.messages.Message;
import it.polimi.ingsw.messages.MessageType;
import it.polimi.ingsw.messages.Nack;
import it.polimi.ingsw.messages.events.ActivateCharacterCardEvent;
import it.polimi.ingsw.messages.events.ChooseColorEvent;
import it.polimi.ingsw.model.CharacterCard;
import it.polimi.ingsw.model.Color;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class CharacterCardsController implements Initializable{
    private ClientController cc = GUI.getInstance().getClientController();
    private ClientModelManager cmm = GUI.getInstance().getClientModelManager();

    @FXML
    private ImageView DescCC;

    @FXML
    private Label DESCRIPTIONCC;

    @FXML
    private Button ACTIVATECC;

    @FXML
    private Label MESSAGES;

    @FXML
    private AnchorPane COLORS;

    Parent root;
    Stage stage;
    Scene scene;

    private int choosenCCIndex = 0;
    private Color choosenColor = null;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        choosenCCIndex = GameBoardController.getInstance().getChoosenCC();
        String desc = CharacterCard.description(choosenCCIndex);
        Image CCImage = RedirectResources.characterCardsDescImages(choosenCCIndex);

        DescCC.setImage(CCImage);
        DESCRIPTIONCC.setText(desc);
        ACTIVATECC.setText("Activate");

        if(choosenCCIndex == 10 || choosenCCIndex == 11) {
            COLORS.setVisible(true);
            COLORS.setDisable(false);
        }
        if(cmm.wasActivated(choosenCCIndex))
            MESSAGES.setText("This card now costs +1 coin");

    }

    public void activateCC(ActionEvent actionEvent) throws IOException {
        int cardId = GameBoardController.getInstance().getChoosenCC();
        GameEvent gameEvent;
        gameEvent = new ActivateCharacterCardEvent(cardId, cc.getPlayingPlayer());

        Message answer = cc.performEvent(gameEvent);
        if(answer.getMessageType() == MessageType.NACK){
            MESSAGES.setText(((Nack) answer).errorMessage());
            MESSAGES.setVisible(true);
        } else {
            if((choosenCCIndex == 10 || choosenCCIndex == 11) && choosenColor != null) {
                gameEvent = new ChooseColorEvent(choosenColor,cc.getPlayingPlayer());
                answer = cc.performEvent(gameEvent);
                if(answer.getMessageType() == MessageType.NACK){
                    MESSAGES.setText(((Nack) answer).errorMessage());
                    MESSAGES.setVisible(true);
                }
            }

            root = FXMLLoader.load(getClass().getResource("/fxml/UpdatedGameBoard.fxml"));
            stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
            scene = new Scene((root));
            stage.setScene(scene);
            stage.setFullScreen(true);
            stage.setFullScreenExitHint("");
            stage.setResizable(false);
            stage.setHeight(768);
            stage.setWidth(1366);
            stage.show();
        }
    }

    @FXML
    public void returnToGame(MouseEvent event) throws IOException {

        root = FXMLLoader.load(getClass().getResource("/fxml/UpdatedGameBoard.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene((root));
        stage.setScene(scene);
        stage.setFullScreen(true);
        stage.setFullScreenExitHint("");
        stage.setResizable(false);
        stage.setHeight(768);
        stage.setWidth(1366);
        stage.show();

    }

    @FXML
    public void chooseColor(MouseEvent event) {
        choosenColor = Color.valueOf(((ImageView)event.getSource()).getId());
    }
}
