package it.polimi.ingsw.client;


import it.polimi.ingsw.client.view.cli.CLI;
import it.polimi.ingsw.client.view.gui.GUI;
import it.polimi.ingsw.client.view.gui.Login;
import javafx.application.Application;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

import static javafx.application.Application.launch;

public class ClientMain {

    private final static String ERROR_VALID = "\nERROR: please insert a valid integer.\n";

    /**
     * Server program entry point, starts cli or gui
     * @param args used to choose between CLI and GUI.
     */
    public static void notmain(String[] args) {
        boolean viewModeArg = false;
        int viewModeChoice = -1;

        ArrayList<String> argsToForward = new ArrayList<>();

        if (args != null) {
            for (String currentArgument : args) {
                switch (currentArgument) {
                    case "-cli":
                        if (!viewModeArg) {
                            viewModeArg = true;
                            viewModeChoice = 1;
                            System.out.println("CLIENT SETTINGS: <CLI> ON");
                        } else {
//                            Double mode selected - choice during runtime
                            viewModeArg = false;
                            viewModeChoice = -1;
                        }
                        break;
                    case "-gui":
                        if (!viewModeArg) {
                            viewModeArg = true;
                            viewModeChoice = 2;
                            System.out.println("CLIENT SETTINGS: <GUI> ON");
                        } else {
//                            Double mode selected - choice during runtime
                            viewModeArg = false;
                            viewModeChoice = -1;
                        }
                        break;
                }
            }
        }

        if (!viewModeArg) {
            System.out.println("Please enter the interface you would like to play with");
            System.out.println("1. CLI");
            System.out.println("2. GUI (Suggested)");
            Scanner input = new Scanner(System.in);

            boolean continueInput = true;
            do {
                try {
                    viewModeChoice = input.nextInt();
                    if (viewModeChoice != 1 && viewModeChoice != 2) {
                        System.out.println(ERROR_VALID);
                    } else {
                        continueInput = false;
                    }

                } catch (InputMismatchException e) {
                    System.out.println(ERROR_VALID);
                }

            }
            while (continueInput);
        }

        String[] argsString = new String[argsToForward.size()];
        argsToForward.toArray(argsString);

        switch (viewModeChoice) {
            case 1:
                //starts the cli
                CLI cli = new CLI();
                cli.start();
                break;
            case 2:
                launch(Login.class, argsString);
                break;
            default:
                System.out.println("ERROR: please insert a valid integer.");
                System.exit(0);
                break;
        }
    }
}
