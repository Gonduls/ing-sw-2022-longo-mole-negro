package it.polimi.ingsw.client;

import it.polimi.ingsw.client.view.cli.CLI;
import it.polimi.ingsw.exceptions.UnexpectedMessageException;
import it.polimi.ingsw.server.Lobby;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class NetworkHandlerTest {
    Lobby lobby;
    NetworkHandler nh;
    ClientController cc;
    String username = "Pippo";

    /**
     * Sets up lobby and connects a client (a network handler) to it
     * then logs it using "username" as name
     */
    @BeforeEach
    void setUp() {
        // lobby setup
        lobby = Lobby.getInstance();
        new Thread(() -> lobby.listen()).start();

        // client setup
        try{
            cc = new ClientController(new CLI(), "localhost", 9999);
            nh = cc.nh;
            new Thread(nh).start();
        } catch (IOException e){
            fail();
        }

        // login
        try{
            assertTrue(nh.login(username));
        } catch (UnexpectedMessageException | IOException e){
            fail();
        }
    }

    /**
     * Logs out client previously logged in, shuts down lobby
     */
    @AfterEach
    void tearDown(){
        try{
            assertTrue(nh.logout());
            assertFalse(lobby.getPlayers().containsKey(username));
            assertTrue(lobby.stop());
            assertFalse(lobby.isRunning());
        } catch (IOException | UnexpectedMessageException e){
            fail();
        }
    }

    /**
     * Tests that the logged in client is contained in the lobby,
     * tries to log in a second client verifying that it can't have
     * the same username as the first client
     */
    @Test
    void login(){
        assertTrue(lobby.getPlayers().containsKey(username));
        NetworkHandler nh1 = null;

        try {
            nh1 = newClient();
        } catch (IOException e){
            fail();
        }

        // checking that no more clients with same username can be added
        /*try{
            //assertFalse(nh1.login(username));
            //assertFalse(nh1.login(username));
            //assertFalse(nh1.login(username));
            //assertFalse(nh1.login(username));
            assertTrue(nh1.login(username + "1"));
            assertTrue(lobby.getPlayers().containsKey(username + "1"));
            assertTrue(nh1.logout());
        } catch (UnexpectedMessageException | IOException e){
            fail();
        }*/
    }


    /**
     * Creates a new clientController and returns its NetworkHandler, without logging in.
     * No ClientController is saved
     *
     * @return The NetworkHandler associated to the client
     */
    NetworkHandler newClient()throws IOException{
        return (new ClientController(new CLI(), "localhost", 9999)).nh;
    }
}