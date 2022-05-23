package it.polimi.ingsw;

import it.polimi.ingsw.client.ClientMain;
import it.polimi.ingsw.server.Lobby;

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

        // todo: ask for server or client
        Scanner sc = new Scanner(System.in);
        System.out.println("");
    }
}
