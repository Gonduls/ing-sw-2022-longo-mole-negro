package it.polimi.ingsw.client.view.gui;

import it.polimi.ingsw.client.ClientController;
import it.polimi.ingsw.client.ClientModelManager;
import it.polimi.ingsw.client.view.UI;
import it.polimi.ingsw.client.view.gui.controller.GameBoardController;
import it.polimi.ingsw.client.view.gui.controller.LobbyController;
import it.polimi.ingsw.messages.Message;
import it.polimi.ingsw.server.RoomInfo;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
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
    static GUI instance;
    private final AtomicBoolean gameRunning = new AtomicBoolean((false));
    public static GUI getInstance(){
        if(instance == null)
            instance = new GUI();

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
    public void killGame(){}

    @Override
    public void merge(int secondIsland) {

    }

    @Override
    public void printStatus() {
        //GameBoardController.getInstance.

    }

    @Override
    public void showPublicRooms(List<RoomInfo> rooms) {
        LobbyController.getInstance().setPublicRooms(rooms);

    }

    @Override
    public void showMessage(Message message) {

    }

    @Override
    public void createGame(int numberOfPlayers, boolean expert, ClientModelManager cmm) {
        this.cmm = cmm;
        /*if (expert) {
            switch (numberOfPlayers) {
                case (2) -> {
                    //todo:
                    //switcha a board per 2 players
                }
                case (3) -> {
                    //switcha a board per 3
                }
                case (4) -> {
                    //switcha a board per 4
                }
            }
        }*/
        Platform.runLater(new Runnable() {
            @Override
            public void run() {

                try {
                    Parent root;
                    Scene scene;
                    root = FXMLLoader.load(getClass().getResource("/fxml/UpdatedGameBoard.fxml"));
                    scene = new Scene(root, 1366, 768);
                    primaryStage.setScene(scene);
                    primaryStage.setFullScreen(true);
                    primaryStage.setResizable(false);
                    primaryStage.setHeight(768);
                    primaryStage.setWidth(1366);
                    primaryStage.show();
                } catch (IOException e) {
                    // boh
                }
            }
        });


        /*gameRunning.set(true);

        synchronized (gameRunning) {
            gameRunning.notifyAll();
        }*/

    }

    public void setStage(Stage stage) {
        primaryStage = stage;
    }



    public void createClientController(String ip, int port) throws IOException{
        cc = new ClientController(this, ip, port);
    }

    public ClientController getClientController() {
        return cc;
    }

    public void gamePhase() {
        //in realtà litiga coi thread
        Thread game;
        if (inARoom) {
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
            }
        } else
            return;
    }

    public void game() {
        //qua prende le varie azioni
    }

    public void setInARoom(boolean value) {
        this.inARoom = value;
    }

    public Stage getStage() {
        return primaryStage;
    }
}
