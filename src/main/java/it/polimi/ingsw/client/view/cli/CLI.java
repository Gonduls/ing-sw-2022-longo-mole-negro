package it.polimi.ingsw.client.view.cli;
import java.io.*;
import java.util.*;
import java.util.concurrent.TimeUnit;

import it.polimi.ingsw.client.ClientController;
import it.polimi.ingsw.client.ClientModelManager;
import it.polimi.ingsw.client.view.UI;
import it.polimi.ingsw.exceptions.UnexpectedMessageException;
import it.polimi.ingsw.messages.*;
import it.polimi.ingsw.model.Color;
import it.polimi.ingsw.server.RoomInfo;
import org.fusesource.jansi.Ansi;
import org.fusesource.jansi.AnsiConsole;


import static org.fusesource.jansi.Ansi.ansi;


public class CLI implements UI {
    private ClientController clientController;
    private final PrintStream output;
    private final Scanner input;
    private String username;

    public CLI() {
        this.output = System.out;
        this.input = new Scanner(System.in);
    }

    public void start(){
        //todo: de-commentare sotto
        AnsiConsole.systemInstall();
        printClear();

        System.out.println("Welcome to /n");
        gameTitle();
        String ipAddress;
        int serverPort;
        do{
            System.out.println("Insert the Server IP: ");
            ipAddress = input.nextLine();
            System.out.println("Insert the Server Port: ");
            serverPort = input.nextInt();
            input.nextLine();
            try {
                clientController = new ClientController(this, ipAddress, serverPort);
            } catch (IOException e){
                System.out.println("Could not connect");
            }
        } while (clientController == null);

        boolean login = false;
        do {
            System.out.println("What's your nickname? ");
            username = input.nextLine();
            try {
                login = clientController.login(username);
                System.out.println(login);
            } catch (UnexpectedMessageException e){
                System.out.println("Could not login, unexpected message exception");
                continue;
            }
        } while (!login);

        do {
            preGame();
            game();
        } while(true);
        //todo: createGameView();
        //where do I get the infos?



    }

    public void preGame(){
        boolean inARoom = false;
        do{
            System.out.println("""
                What would you like to do?\s
                1. ACCESS ROOM\s
                2. CREATE GAME\s
                3. LOGOUT\s
                4. REFRESH AVAILABLE PUBLIC ROOMS\s
                """);
            String chosenAction = input.nextLine();
            switch (chosenAction) {
                case("1"):
                    System.out.println("Enter the room ID: ");
                    int id = Integer.parseInt(input.nextLine());
                    if(clientController.accessRoom(id)) {
                        inARoom = true;
                    }
                    else {
                        System.out.println("Could not enter room, please try again");
                    }
                    break;
                case("2"):
                    System.out.println("Enter the number of players (2/3/4): ");
                    int playersNumber = input.nextInt();
                    System.out.println("Is the mode expert? (true/false) ");
                    boolean isExpert = input.nextBoolean();
                    System.out.println("Is your game private? (true/false) ");
                    boolean isPrivate = input.nextBoolean();

                    CreateRoom message = new CreateRoom(playersNumber,isExpert,isPrivate);
                    int roomID = clientController.createRoom(message);
                    if(roomID < 0){
                        System.out.println("Could not create room, please try again");
                        break;
                    }
                    printClear();
                    System.out.println("Your room ID is: " + roomID);
                    inARoom = true;
                    break;
                case("3"):
                    clientController.logout();
                    break;

                case("4"):
                    System.out.println("Do you want to specify the number of players (2/3/4/ default: all games): ");
                    try{
                        playersNumber = Integer.parseInt(input.nextLine());
                    } catch (NumberFormatException e){
                        playersNumber = 0;
                    }
                    System.out.println("Is the mode expert? (true/false/ default: all games) ");

                    String expert = input.nextLine();

                    if(expert.startsWith("true")){
                        if(playersNumber == 0){
                            clientController.getPublicRooms(new GetPublicRooms(true));
                            break;
                        }
                        clientController.getPublicRooms(new GetPublicRooms(playersNumber, true));
                        break;
                    }
                    if(expert.startsWith("false")){
                        if(playersNumber == 0){
                            clientController.getPublicRooms(new GetPublicRooms(false));
                            break;
                        }
                        clientController.getPublicRooms(new GetPublicRooms(playersNumber, false));
                        break;
                    }

                    if(playersNumber == 0){
                        clientController.getPublicRooms(new GetPublicRooms());
                        break;
                    }
                    clientController.getPublicRooms(new GetPublicRooms(playersNumber));
                    break;

                default:
                    System.out.println("Please enter a valid choice");
            }



        }while(!inARoom);
    }

    public void game() {
        try{
            TimeUnit.SECONDS.sleep(20);
        } catch (InterruptedException ignored){
            System.out.println("problems in waiting");
        }
    }

    /*public void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }*/

    public void gameTitle() {
        //System.out.println(ANSIColors.CYAN_BRIGHT);
        AnsiConsole.systemInstall();
        System.out.print("""
                                                                                                                                                                     \s
                        ..:^~~!~!!??!!77!!!!!!!!!7.                      ^!!!~.                                                                                      \s
                     .!?YYYYJJJGPB#P??JJ??Y5GBBBBG?                     7GGBBPP                                             .^                                       \s
                   .?YPJ~:.   ?G#&&?        .:!G&#P7                    ?@&&&&G                                            .PB.                                      \s
                  ^PP#!      JG&&&&?            :YBGJ                   .JGG5!.                                           :5G#.                                      \s
                  PG#J      :##&&&&7   :^~!!!~.   .!Y. .:::.    .^::.    .^^:            ..:^^^^::     .::.    .:^:.   .^75B&B~^~.  .^~~.     .!?!^    .:~~~~^::^!:  \s
                 .P##~      :B&&&&&~.^?5PGPY555^     .~?5PP?  ^?YPP5J: ^?YPP5J.       :~JYYJJ5PPPP.  ^7YP5J  :7YPP5Y^ :JYG#&&GYJ?..~?PBGY~.  ~PB&#57. ~?5YYY5PP55Y.  \s
                 .5#B!      .G&&&&#7?P#BJ^.  ?B!    ~?JB&##5^75B&&&&B^~!^Y&&&G:     ^75BB7:.^#&&&P.:??G&&&P~!5B#&&&#?  .:G&&&G: .!JG#&@@&G7. ^J#&&&G?~G&P~....!GG:   \s
                  J##J.     .G&&#&BP&B?.     ~?.    :.^B&##5JY~:P&&G~    ?&&&B:   .7Y#&G:   ^B&&&P....G&&&GJP?:!#&&&J   .P&&&P. ~!~:.^P&&&B?:  :G&&&J~#&BY?!^:...    \s
                  .P&P7:    .G#&&&&&P:                :G&&#P7. .PG!.     J#&&B:   !5#&#~    ^B#&&P.  .P&&&BY:  ~#&&&J   .P&&&P.       .Y&&&G?:  J&&5..J&&&&#G5J7^.   \s
                   .Y#GJ!:. .G###&&G.               . :B&&&5.  .^.       J#&&B:  .J#&&B~    ~B&&&P.  .P&&&B:   ~#&&&?.. .P&&&P.        .J#&&G?^^5#J. ..:?G#&&&&#GJ^  \s
                     :7JJ!!^.G###&#7              .!?.:B&&&5.            J#&&G:  .P&&&B7::~?JB&&&5.  .P&&&B:   ~#&&#J   .P&&&G. .:.     .?&&&G??G?. .??^ ..~JP#&&B7  \s
                            .P#&&&B:             .!J^ .P&&&J.            7#&&P:.:.?&&&&#GGG5!G&&&Y..:.J&&&P.   :B&&#7.:..5&&&5:~!!.      .7#&&BY~  ..7B?^..  .^P&#^  \s
                            .Y&&&&G:         ..:^JP~  .5&#P~             :B&&G~^: .J#&&&#Y^  7#&&J~^..?&&#7    .5&&&!^:. ~&&&BJ!.  .   ... ~#&Y. .....Y#GJ!^^^~5P~.  \s
                         .:^?#&&&&&J~^^^:^^^~7YG##~   .^!:.               .!?!:     :!7~.     :!7~.   ^7^.      .!7!:.   .^77~.        ....^GJ.........:~!!~^^:.     \s
                         ..:~~~~~~~~^^:::::^^~~~~:                                                                                    ...:?BJ.........               \s
                                                                                                                                     ...^Y&G......                   \s
                                                                                                                                    ...^P&&?..........               \s
                                                                                                                                   ....?&&&5:.....:~^...             \s
                                                                                                                                  .....~B&&&Y~^:^^7!.....            \s
                                                                                                                                  ......:!Y5P5?7!^:.....             \s
                """);


        AnsiConsole.systemUninstall();

    }

    public void islandPrint(int islandIndex, ClientModelManager cmm) {
        for(int i = 0; i < islandIndex; i++) {
            EnumMap<Color, Integer> studentsIsland = cmm.getStudentsInIsland(i);
                System.out.println("    ________     \n" +
                        "   /        \\    \n" +
                        "  /  "+ studentsIsland.get(Color.RED) +"    " + studentsIsland.get(Color.BLUE) +"    \\   \n" +
                        " /   "+ studentsIsland.get(Color.GREEN) +"    " + studentsIsland.get(Color.PINK) +"         \\  \n" +
                        " \\  "+ studentsIsland.get(Color.YELLOW) +"          /  \n" +
                        "  \\          /   \n" +
                        "   \\________/ "+ i +"   \n");


        }

    }

    public void moves() {
        System.out.println("   ______________________________     \n" +
                " / \\                             \\.   \n" +
                "|   |     What's your move?      |.   \n" +
                " \\_ |                            |.   \n" +
                "    |  1.PLAY_ASSISTANT_CARD     |.   \n" +
                "    |                            |.   \n" +
                "    |  2.MOVE_STUDENT            |.   \n" +
                "    |                            |.   \n" +
                "    |  3.ACTIVATE_CHARACTER_CARD |.   \n" +
                "    |                            |.   \n" +
                "    |  4.MOVE_MOTHER_NATURE      |.   \n" +
                "    |                            |.   \n" +
                "    |  5.PICK_CLOUD              |.   \n" +
                "    |   _________________________|___ \n" +
                "    |  /                            /.\n" +
                "    \\_/____________________________/. \n");
    }

    public void schoolCard(ClientModelManager cmm) {
        for(int i = 0; i < cmm.getPlayers().length; i++)
        System.out.println("    " + username.toUpperCase(Locale.ROOT) +
                " ________________ \n" +
                "| P  P  P  P  P  |\n" +
                "|----------------|\n" +
                "| 01 00 00 00 00 |\n" +
                "|----------------|\n" +
                "| $00            |\n" +
                "|________________|\n");
    }


    @Override
    public void printStatus() {


    }

    @Override
    public void showPublicRooms(List<RoomInfo> rooms) {
        System.out.println(ansi().render("@|bg_black,bold,fg_yellow Public Games:|@").bgDefault().fgDefault());
        if(rooms.size() == 0) {
            System.out.println("There are no public rooms available. \n");
        } else {
            for (RoomInfo room : rooms) {
                System.out.println("______________________________________________________________");
                System.out.print(room.toString());
            }
        }

    }

    @Override
    public void showMessage(Message message) {
        switch (message.getMessageType()) {
            case NACK -> {
                System.out.println("NACK");
            }
            case ADD_PLAYER -> {
                AddPlayer ap = (AddPlayer) message;
                System.out.println("Player " + ap.username() +" joined! ");
            }
            case END_GAME -> {
                System.out.println("Game Over \n" +
                        "Winner/s: " );
                EndGame eg = (EndGame) message;
                for(int i = 0; i < eg.winners().length; i++)
                {
                    System.out.println((eg.winners()[i]));
                }
            }
            case PLAYER_DISCONNECT -> {
                PlayerDisconnect pd = (PlayerDisconnect) message;
                System.out.println("Player" + pd.username() + "has left the room \n" +
                        "Please, return to Lobby. ");
            }
            default ->
                System.out.print(message.getMessageType());
        }

    }

    @Override
    public void createGameView(int numberOfPlayer, boolean expert, ClientModelManager cmm){
        islandPrint(cmm.getIslands().size(), cmm);

    }

    protected void printClear() {
        AnsiConsole.systemInstall();
        Ansi ansi = Ansi.ansi();
        System.out.println( ansi.eraseScreen() );
        System.out.println( ansi.cursor(0, 0) );
        AnsiConsole.systemUninstall();
    }

}
