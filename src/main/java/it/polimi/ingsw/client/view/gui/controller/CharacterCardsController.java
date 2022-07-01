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
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Handles the initialization of CharacterCards.FXML.
 * It shows the card chosen, its description and handles its activation.
 * Allows you to choose a color for the two cards that require it.
 */
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

    private int chosenCCIndex = 0;


    /**
     * Called to initialize a controller after its root element has been completely processed.
     * @param url The location used to resolve relative paths for the root object, or null if the location is not known.
     * @param resourceBundle The resources used to localize the root object, or null if the root object was not localized.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        chosenCCIndex = GameBoardController.getInstance().getChoosenCC();
        String desc = CharacterCard.description(chosenCCIndex);
        Image CCImage = RedirectResources.characterCardsDescImages(chosenCCIndex);

        DescCC.setImage(CCImage);
        DESCRIPTIONCC.setText(desc);
        ACTIVATECC.setText("Activate");

        if(chosenCCIndex == 10 || chosenCCIndex == 11) {
            COLORS.setVisible(true);
            COLORS.setDisable(false);
        }
        if(cmm.wasActivated(chosenCCIndex))
            MESSAGES.setText("This card now costs +1 coin");

    }

    /**
     * Gets called when the Activate button is clicked.
     * It creates a new ActivateCharacterCardEvent and lets the ClientControl perform it.
     * If the answer is a NACK, it shows an error message, else it brings the user back to the game board,
     * so they can execute the actions required by the chosen card.
     * Cards 10 and 11 are dealt with differently, since they require the user to pick a color.
     * @param actionEvent the click of the button
     * @throws IOException handles possible FXMLLoader's exception
     */
    public void activateCC(ActionEvent actionEvent) throws IOException {
        actionEvent.consume();
        int cardId = GameBoardController.getInstance().getChoosenCC();
        GameEvent gameEvent;
        gameEvent = new ActivateCharacterCardEvent(cardId, cc.getPlayingPlayer());

        Message answer = cc.performEvent(gameEvent);
        if(answer.getMessageType() == MessageType.NACK){
            MESSAGES.setText(((Nack) answer).errorMessage());
            MESSAGES.setVisible(true);
        } else {
            if(chosenCCIndex == 10 || chosenCCIndex == 11) {
                ACTIVATECC.setVisible(false);
                ACTIVATECC.setDisable(true);
                COLORS.setVisible(true);
                COLORS.setDisable(false);
                MESSAGES.setText("Please choose a color");
                MESSAGES.setVisible(true);
            } else {
                GUI.getInstance().changeScene("/fxml/UpdatedGameBoard.fxml", 790, 1366);
            }
        }
    }

    /**
     * Switches back to the GameBoard scene
     * @param event the mouse click on the return button
     * @throws IOException handles FXMLLoader's possible exception
     */
    @FXML
    public void returnToGame(MouseEvent event) throws IOException {
        GUI.getInstance().changeScene("/fxml/UpdatedGameBoard.fxml", 790, 1366);
        event.consume();
    }

    /**
     * Allows the user to pick a color and creates and handles the ChooseColorEvent.
     * If successful, returns to the GameBoard scene to continue the game.
     * @param event the mouse click on a color
     * @throws IOException handles FXMLLoader's possible exception
     */
    @FXML
    public void chooseColor(MouseEvent event) throws IOException{
        Color chosenColor = Color.valueOf(((ImageView) event.getSource()).getId());
        GameEvent gameEvent;
        gameEvent = new ChooseColorEvent(chosenColor, cc.getPlayingPlayer());

        Message answer = cc.performEvent(gameEvent);
        if (answer.getMessageType() == MessageType.NACK) {
            MESSAGES.setText(((Nack) answer).errorMessage());
            MESSAGES.setVisible(true);
        } else {
            GUI.getInstance().changeScene("/fxml/UpdatedGameBoard.fxml", 790, 1366);
        }
    }
}
