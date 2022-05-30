package it.polimi.ingsw;

import it.polimi.ingsw.client.ClientMain;
import it.polimi.ingsw.server.Lobby;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        boolean server = false;


        for(String s : args){
            if(s.startsWith("-s")){
                server = true;


            } else if (s.startsWith("-c")) {
                ClientMain.notmain(args);
                return;
            } else if(server){
                Lobby lobby;
                try {
                    System.out.println(s);
                    lobby = Lobby.getInstance(Integer.parseInt(s));
                } catch (NumberFormatException e){
                    lobby = Lobby.getInstance();
                }
                lobby.listen();
            }
        }

        final String ERROR_VALID = "\nERROR: please insert a valid integer.\n";
        System.out.println("Please enter if you are a client or a server");
        System.out.println("1. SERVER");
        System.out.println("2. CLIENT");
        Scanner input = new Scanner(System.in);
        int role = 0;

        boolean continueInput = true;
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

        if(role == 1){
            Lobby lobby;
            try {
                lobby = Lobby.getInstance(9999);
            } catch (NumberFormatException e){
                lobby = Lobby.getInstance();
            }
            lobby.listen();
        }
        else {
            ClientMain.notmain(args);
        }
    }
}
