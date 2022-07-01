package it.polimi.ingsw.client.view.gui;

import it.polimi.ingsw.Log;
import it.polimi.ingsw.client.ClientController;
import it.polimi.ingsw.client.ClientModelManager;
import it.polimi.ingsw.client.view.UI;
import it.polimi.ingsw.client.view.gui.controller.*;
import it.polimi.ingsw.messages.EndGame;
import it.polimi.ingsw.messages.Message;
import it.polimi.ingsw.messages.MessageType;
import it.polimi.ingsw.messages.PlayerDisconnect;
import it.polimi.ingsw.server.RoomInfo;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Application class that implements UI in order to handle the user actions if the mode selected is GUI
 */
public class GUI extends Application implements UI{
    ClientController cc;
    ClientModelManager cmm;
    static boolean inARoom = false;
    private static Stage primaryStage;
    private String username;
    private final AtomicBoolean gameRunning = new AtomicBoolean((false));
    static GUI instance;
    private final boolean[] merged = new boolean[12];
    private boolean setScene = false;
    private boolean fullscreen = false;

    public static GUI getInstance(){
        if(instance == null) {
            instance = new GUI();
            new Log("GuiLog.txt");
        }

        return instance;
    }

    /**
     * It starts the application given the stage.
     * It opens the window, sets the stage and shows the first scene
     * @param stage the initial stage
     * @throws IOException handles FXMLLoader's possible exception
     */
    @Override
    public void start(Stage stage) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/fxml/Connection.fxml")));
        Scene scene = new Scene(root);
        stage.setTitle("Eriantys");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.getIcons().add(new Image(String.valueOf(getClass().getResource("/images/Elements/Logo2.png"))));
        stage.show();
        primaryStage = stage;
    }

    /**
     * Stops the application from running when the user exits the window
     */
    @Override
    public void stop(){
        Log.logger.info("Application stopping");
        Platform.exit();
        System.exit(0);
    }

    /**
     * Handles the merge of islands
     * @param secondIsland The index of the island that was removed in the model
     */
    @Override
    public void merge(int secondIsland) {
        merged[getIsland12Index(secondIsland)] = true;
    }

    /**
     * Refreshes the Game Board scene in order to show updates of the model
     */
    @Override
    public void refresh() {
        if(cc.getPlayingPlayer() == -1)
            return;


        if(setScene){
            GameBoardController.getInstance().reprint();
            (new AdversarySchoolsController()).reprint();
            return;
        }

        Platform.runLater(() -> {

            try {
                changeScene("/fxml/UpdatedGameBoard.fxml", 768, 1366);
                setScene = true;
            } catch (IOException e) {
                Log.logger.severe(e.getMessage());
            }
        });
    }

    /**
     * Sets the list of public rooms in Lobby
     * @param rooms The room info relative to the public rooms
     */
    @Override
    public void showPublicRooms(List<RoomInfo> rooms) {
        LobbyController.getInstance().setPublicRooms(rooms);

    }

    /**
     * Changes the scene to handle EndGame and Player Disconnect messages
     * @param message the message to be printed
     */
    @Override
    public void showMessage(Message message) {
        if(message.getMessageType() == MessageType.END_GAME) {
            EndGameController.setEndgame((EndGame) message);
            Platform.runLater(() -> {
                try {
                    changeScene("/fxml/EndGame.fxml", 768, 1366);
                } catch (IOException e) {
                    Log.logger.severe(e.getMessage());
                }
            });
        }
        if(message.getMessageType() == MessageType.PLAYER_DISCONNECT) {
            DisconnectedController.setDisconnectMessage(((PlayerDisconnect) message));
            Platform.runLater(() -> {
                try{
                    changeScene("/fxml/Disconnected.fxml", 768, 1366);
                }catch (IOException e){
                    Log.logger.severe(e.getMessage());
                }
            });
        }
    }

    /**
     * Sets game starting parameters
     * @param numberOfPlayers Either 2, 3, or 4
     * @param expert True if game is expert mode, false otherwise
     * @param cmm The ClientModelManager connected to the starting game
     */
    @Override
    public void createGame(int numberOfPlayers, boolean expert, ClientModelManager cmm) {
        this.cmm = cmm;

        for(int i = 0; i< 12; i ++)
            merged[i] = false;

        gameRunning.set(true);

        synchronized (gameRunning) {
            gameRunning.notifyAll();
        }

    }

    /**
     * Creates a new Client Controller linked to this UI, Server Ip and port
     * @param ip the Server IP
     * @param port the Server port
     * @throws IOException handles possible exception in creating the ClientController
     */
    public void createClientController(String ip, int port) throws IOException{
        cc = new ClientController(this, ip, port);
    }

    /**
     * @return the created Client Controller
     */
    public ClientController getClientController() {
        return cc;
    }

    /**
     * @return the Client Model Manager
     */
    public ClientModelManager getClientModelManager() { return cmm; }

    /**
     * Sets true if the user is in the room
     * @param value a boolean that determines if the user is in a room or not
     */
    public static void setInARoom(boolean value) {
        inARoom = value;
    }

    /**
     * Sets the username to the one given as parameter
     * @param username the string containing the username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * @return the username given
     */
    public String getUsername() {
        return username;
    }

    /**
     * @param island12Index The index of the target island (from 0 to 12)
     * @return False if the island is supposed to be printed, true otherwise
     */
    public boolean wasMerged(int island12Index){
        return merged[island12Index];
    }

    /**
     * Given the index of an island (from 0 to # of islands in model), it returns the corresponding
     * index if no islands were merged
     * @param islandModelIndex The index of an island (from 0 to # of islands in model)
     * @return The corresponding index from the start of the game
     */
    public int getIsland12Index(int islandModelIndex){
        int i = 0;
        while(i < 12){
            if(merged[i])
                islandModelIndex++;
            else if (i == islandModelIndex) {
                return islandModelIndex;
            }
            i ++;
        }
        return -1;
    }

    /**
     * Given the index of an island (from 0 to 12), it returns the corresponding
     * index with merged islands, -1 if the island itself was merged
     * @param island12Index The index of an island (from 0 to 12)
     * @return The corresponding index with islands merged, -1 if the island itself was merged
     */
    public int getIslandModelIndex(int island12Index){
        if(merged[island12Index])
            return -1;

        int result = island12Index;
        for(int i = 0; i < island12Index; i++){
            if(merged[i])
                result --;
        }
        return result;
    }

    /**
     * Changes setScene to "value": false is needed when joining a new game
     * @param value The value to set setScene to
     */
    public void setSetScene(boolean value){
        instance.setScene = value;
    }

    /**
     * Changes scene, must be run inside Application thread
     * @param resource The file containing the fxml
     * @param height The height of the scene
     * @param width The width of the scene
     * @throws IOException If FXMLLoader does
     */
    public void changeScene(String resource, int height, int width) throws IOException{
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource(resource)));
        Scene scene = new Scene(root, height, width);

        primaryStage.setScene(scene);
        primaryStage.setResizable(true);
        primaryStage.setMaxHeight(height + 30.0);
        primaryStage.setHeight(height + 30.0);
        if(fullscreen){
            primaryStage.setMaxHeight(height);
            primaryStage.setHeight(height);
        }

        primaryStage.setMinHeight(height);
        primaryStage.setWidth(width);
        primaryStage.setMaxWidth(width);
        primaryStage.setMinWidth(width);

        scene.setOnKeyPressed(event -> {
            if(event.getCode() == KeyCode.F11) {
                fullscreen = !fullscreen;
                primaryStage.setHeight(height);
                primaryStage.setFullScreen(!fullscreen);
                primaryStage.setFullScreen(fullscreen);
                event.consume();
            }
        });

        primaryStage.setFullScreenExitHint("");
        primaryStage.show();
    }
}
