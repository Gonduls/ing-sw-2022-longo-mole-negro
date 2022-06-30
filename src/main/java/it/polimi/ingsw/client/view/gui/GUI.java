package it.polimi.ingsw.client.view.gui;

import it.polimi.ingsw.Log;
import it.polimi.ingsw.client.ClientController;
import it.polimi.ingsw.client.ClientModelManager;
import it.polimi.ingsw.client.view.UI;
import it.polimi.ingsw.client.view.gui.controller.GameBoardController;
import it.polimi.ingsw.client.view.gui.controller.LobbyController;
import it.polimi.ingsw.messages.Message;
import it.polimi.ingsw.messages.MessageType;
import it.polimi.ingsw.server.RoomInfo;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;


public class GUI extends Application implements UI{
    ClientController cc;
    ClientModelManager cmm;
    static boolean inARoom = false, kill;
    private static Stage primaryStage;
    private String username;
    private final AtomicBoolean gameRunning = new AtomicBoolean((false));
    static GUI instance;
    private final boolean[] merged = new boolean[12];
    private boolean setScene = false; //todo: when exiting game, set back to false

    public static GUI getInstance(){
        if(instance == null) {
            instance = new GUI();
            new Log("GuiLog.txt");
        }

        return instance;
    }

    @Override
    public void start(Stage stage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/Connection.fxml"));
        Scene scene = new Scene(root);
        stage.setTitle("Eriantys");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.getIcons().add(new Image(String.valueOf(getClass().getResource("/images/Elements/Logo2.png"))));
        stage.show();
        primaryStage = stage;
    }

    @Override
    public void merge(int secondIsland) {
        merged[getIsland12Index(secondIsland)] = true;
    }

    @Override
    public void refresh() {
        //GameBoardController.getInstance.
        if(cc.getPlayingPlayer() == -1)
            return;


        if(setScene){
            GameBoardController.getInstance().reprint();
            return;
        }

        Platform.runLater(new Runnable() {
            @Override
            public void run() {

                try {
                    Parent root;
                    Scene scene;
                    root = FXMLLoader.load(getClass().getResource("/fxml/UpdatedGameBoard.fxml"));
                    scene = new Scene(root, 1366, 768);
                    primaryStage.setScene(scene);
                    primaryStage.setFullScreen(false);
                    primaryStage.setResizable(false);
                    primaryStage.setHeight(768);
                    primaryStage.setWidth(1366);
                    primaryStage.show();
                    setScene = true;
                } catch (IOException e) {
                    System.out.println(e.getMessage());
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void showPublicRooms(List<RoomInfo> rooms) {
        LobbyController.getInstance().setPublicRooms(rooms);

    }

    @Override
    public void showMessage(Message message) {
        if(message.getMessageType() == MessageType.END_GAME) {
            try {
                Parent root;
                Scene scene;
                root = FXMLLoader.load(getClass().getResource("/fxml/EndGame.fxml"));
                scene = new Scene(root, 1366, 768);
                primaryStage.setScene(scene);
                primaryStage.setFullScreen(false);
                primaryStage.setResizable(false);
                primaryStage.setHeight(768);
                primaryStage.setWidth(1366);
                primaryStage.show();

            } catch (IOException e) {
                System.out.println(e.getMessage());
                e.printStackTrace();
            }

        }

    }

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

    public void createClientController(String ip, int port) throws IOException{
        cc = new ClientController(this, ip, port);
    }

    public ClientController getClientController() {
        return cc;
    }

    public ClientModelManager getClientModelManager() { return cmm; }

    public void gamePhase() {
        Thread game;
        if(!inARoom)
            return;

        kill = false;
        game = new Thread(this::game);
        synchronized (gameRunning) {
            if (!gameRunning.get()) {
                try {
                    gameRunning.wait();
                } catch (InterruptedException e) {
                    //todo pop-up errore?
                    Thread.currentThread().interrupt();
                    return;
                }
            }
        }

        game.start();
        try {
            game.join();
        } catch (InterruptedException e) {
            //todo pop up thread closing
            Thread.currentThread().interrupt();
        } finally {
            inARoom = false;
        }
    }

    private void game() {
    }


    public void setInARoom(boolean value) {
        this.inARoom = value;
    }

    public Stage getStage() {
        return primaryStage;
    }

    public void setStage(Stage stage) {
        primaryStage = stage;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public boolean wasMerged(int island12Index){
        return merged[island12Index];
    }

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
}
