package it.polimi.ingsw;

import it.polimi.ingsw.client.view.cli.CLI;
import it.polimi.ingsw.client.view.gui.GUI;
import it.polimi.ingsw.server.Lobby;
import javafx.application.Application;

import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Starts server or client
 */
public class Main {
    static final String ERROR_VALID = "\nERROR: please insert a valid integer.\n";

    /**
     * Main function, starts server or calls client
     * @param args The parameters passed from command line
     */
    public static void main(String[] args) {
        boolean server = false;
        Lobby lobby = null;
        Log.setDebug(false);

        for(String s : args){
            if(s.startsWith("-s")){
                server = true;
            } else if (s.startsWith("-c")) {
                new Main().client(args);
                return;
            } else if(server){
                try {
                    lobby = Lobby.getInstance(Integer.parseInt(s));
                } catch (NumberFormatException e){
                    lobby = Lobby.getInstance();
                }
            } else if(s.startsWith("-d"))
                Log.setDebug(true);

        }

        if(lobby != null)
            lobby.listen();

        System.out.println("Please enter if you are a client or a server");
        System.out.println("1. SERVER");
        System.out.println("2. CLIENT");
        Scanner input = new Scanner(System.in);
        int role = getRole(input);

        if(role == 1){
            try {
                lobby = Lobby.getInstance(9999);
            } catch (NumberFormatException e){
                lobby = Lobby.getInstance();
            }
            lobby.listen();
        }
        else {
            new Main().client(args);
        }
    }

    /**
     * Server program entry point, starts cli or gui
     * @param args used to choose between CLI and GUI.
     */
    public void client(String[] args) {
        boolean viewModeArg = false;
        int viewModeChoice = -1;

        if (args != null) {
            for (String currentArgument : args) {
                if ("-cli".equals(currentArgument)) {
                    if (!viewModeArg) {
                        viewModeArg = true;
                        viewModeChoice = 1;
                        System.out.println("CLIENT SETTINGS: <CLI> ON");
                    } else {
//                            Double mode selected - choice during runtime
                        viewModeArg = false;
                        viewModeChoice = -1;
                    }
                } else if ("-gui".equals(currentArgument)) {
                    if (!viewModeArg) {
                        viewModeArg = true;
                        viewModeChoice = 2;
                        System.out.println("CLIENT SETTINGS: <GUI> ON");
                    } else {
//                            Double mode selected - choice during runtime
                        viewModeArg = false;
                        viewModeChoice = -1;
                    }
                }
            }
        }

        if (!viewModeArg) {
            System.out.println("Please enter the interface you would like to play with");
            System.out.println("1. CLI");
            System.out.println("2. GUI (Suggested)");
            Scanner input = new Scanner(System.in);

            viewModeChoice = getRole(input);
        }

        switch (viewModeChoice) {
            case 1 ->
                //starts the cli
                    CLI.getInstance().start();
            case 2 -> Application.launch(GUI.class);
            default -> {
                System.out.println("ERROR: please insert a valid integer.");
                System.exit(0);
            }
        }
    }

    /**
     * Reads a "1" or a "2" from input, or loops until it does
     * @param input The Scanner where numbers are read, usually open on System.in
     * @return 1 or 2
     */
    private static int getRole(Scanner input) {
        boolean continueInput = true;
        int role = 0;
        do {
            try {
                role = input.nextInt();
                if (role != 1 && role != 2) {
                    System.out.println(ERROR_VALID);
                } else {
                    continueInput = false;
                }

            } catch (InputMismatchException e) {
                System.out.println(ERROR_VALID);
            }

        }
        while (continueInput);
        return role;
    }
}
