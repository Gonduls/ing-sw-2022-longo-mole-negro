package it.polimi.ingsw.client.view.cli;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

import com.google.gson.Gson;
import it.polimi.ingsw.client.ClientController;
import it.polimi.ingsw.client.ClientModelManager;
import it.polimi.ingsw.client.view.UI;
import it.polimi.ingsw.client.ConfigServer;
import it.polimi.ingsw.exceptions.UnexpectedMessageException;
import it.polimi.ingsw.messages.*;
import it.polimi.ingsw.server.RoomInfo;
import org.fusesource.jansi.AnsiConsole;


import static org.fusesource.jansi.Ansi.ansi;


public class CLI implements UI {
    private ClientController clientController;
    private final PrintStream output;
    private final Scanner input;
    private String username;
    boolean inARoom = false;
    int playersNumber;
    private ClientModelManager cmm;
    private BoardStatus bs;

    public CLI() {
        this.output = System.out;
        this.input = new Scanner(System.in);
    }

    public void start(){
        AnsiConsole.systemInstall();
        printClear();

        System.out.println("Welcome to /n");
        gameTitle();
        String ipAddress;
        int serverPort;


        try{
            Gson gson = new Gson();
            ConfigServer config = gson.fromJson( new String(Files.readAllBytes(Paths.get("config.json"))), ConfigServer.class);
            if(config != null) {
                clientController = new ClientController(this, config.getAddress(), config.getPort());
            }
        } catch (IOException e){
            clientController = null;
        }

        while (clientController == null){
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
        }

        boolean login = false;
        do {
            System.out.println("What's your nickname? ");
            username = input.nextLine();
            try {
                login = clientController.login(username);
                System.out.println(login);
            } catch (UnexpectedMessageException e){
                System.out.println("Could not login, unexpected message exception");
            }
        } while (!login);

        do {
            preGame();
            if(inARoom)
                game();
            else
                return;
        } while(true);
    }

    public void preGame(){
        do{
            System.out.println("""
                    What would you like to do?\s
                    1. REFRESH AVAILABLE PUBLIC ROOMS\s
                    2. CREATE GAME\s
                    3. ACCESS ROOM\s
                    4. LOGOUT\s
                    """);
            String chosenAction = input.nextLine();
            switch (chosenAction) {
                case ("1") -> {
                    System.out.println("Do you want to specify the number of players (2/3/4/ default: all games): ");
                    try {
                        playersNumber = Integer.parseInt(input.nextLine());
                    } catch (NumberFormatException e) {
                        playersNumber = 0;
                    }
                    System.out.println("Is the mode expert? (true/false/ default: all games) ");
                    String expert = input.nextLine();
                    if (expert.startsWith("true")) {
                        if (playersNumber == 0) {
                            clientController.getPublicRooms(new GetPublicRooms(true));
                            break;
                        }
                        clientController.getPublicRooms(new GetPublicRooms(playersNumber, true));
                        break;
                    }
                    if (expert.startsWith("false")) {
                        if (playersNumber == 0) {
                            clientController.getPublicRooms(new GetPublicRooms(false));
                            break;
                        }
                        clientController.getPublicRooms(new GetPublicRooms(playersNumber, false));
                        break;
                    }
                    printClear();
                    if (playersNumber == 0) {
                        clientController.getPublicRooms(new GetPublicRooms());
                        break;
                    }
                    clientController.getPublicRooms(new GetPublicRooms(playersNumber));
                }
                case ("2") -> {
                    System.out.println("Enter the number of players (2/3/4): ");
                    playersNumber = input.nextInt();
                    System.out.println("Is the mode expert? (true/false) ");
                    boolean isExpert = input.nextBoolean();
                    System.out.println("Is your game private? (true/false) ");
                    boolean isPrivate = input.nextBoolean();
                    CreateRoom message = new CreateRoom(playersNumber, isExpert, isPrivate);
                    int roomID = clientController.createRoom(message);
                    if (roomID < 0) {
                        System.out.println("Could not create room, please try again");
                        break;
                    }
                    printClear();
                    System.out.println("Your room ID is: " + roomID);
                    inARoom = true;
                }
                case ("3") -> {
                    System.out.println("Enter the room ID: ");
                    int id = Integer.parseInt(input.nextLine());
                    if (clientController.accessRoom(id)) {
                        inARoom = true;
                    } else {
                        System.out.println("Could not enter room, please try again");
                    }
                }
                case ("4") -> {
                    if (clientController.logout())
                        return;
                    System.out.println("Could not logout");
                }
                default -> {
                    printClear();
                    System.out.println("Please enter a valid choice");
                }
            }



        }while(!inARoom);
    }

    public void game() {
         while(true){
            if(input.nextLine().equals("close"))
                return;
            System.out.println(cmm);
        }
    }

    public void gameTitle() {
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

    /*public void islandPrint(int islandIndex, ClientModelManager cmm) {
        for(int i = 0; i < islandIndex; i++) {
            EnumMap<Color, Integer> studentsIsland = cmm.getStudentsInIsland(i);
            int red = studentsIsland.get(Color.RED);
            int blue = studentsIsland.get(Color.BLUE);
            int green = studentsIsland.get(Color.GREEN);
            int pink = studentsIsland.get(Color.PINK);
            int yellow = studentsIsland.get(Color.YELLOW);
            System.out.println("    ________     \n" +
                    "   /        \\    \n" +
            "  /  " + (cmm.getMotherNature() == i ? "M" : " ") +"  "+ "T:" + cmm.getIslands().get(i).getTowers() + "  \\   \n" +
                    " /     " + (cmm.getIslands().get(i).getNoEntry() > 0 ? "X" : " ") + "      \\   \n" +
                    " \\ "+ renderColor((red < 10 ? " " : "") + red, Color.RED) +"  "+ renderColor((green < 10 ? " " : "") + green, Color.GREEN) +"  " + renderColor((pink < 10 ? " " : "") + pink, Color.PINK)+ " /  \n" +
            "  \\  "+ renderColor((yellow < 10 ? " " : "") + yellow, Color.YELLOW) +"  " + renderColor((blue < 10 ? " " : "") + blue, Color.BLUE) +"  /  \n" +
                    "   \\________/ "+ i  +"   \n");


        }

    }*/

    /*public void moves() {
        System.out.println("""
                   ______________________________    \s
                 / \\                             \\.  \s
                |   |     What's your move?      |.  \s
                 \\_ |                            |.  \s
                    |  1.PLAY_ASSISTANT_CARD     |.  \s
                    |                            |.  \s
                    |  2.MOVE_STUDENT            |.  \s
                    |                            |.  \s
                    |  3.ACTIVATE_CHARACTER_CARD |.  \s
                    |                            |.  \s
                    |  4.MOVE_MOTHER_NATURE      |.  \s
                    |                            |.  \s
                    |  5.PICK_CLOUD              |.  \s
                    |   _________________________|___\s
                    |  /                            /.
                    \\_/____________________________/.\s
                """);
    }*/

    /*public void schoolCard(ClientModelManager cmm) {
        for(int i = 0; i < cmm.getPlayers().length; i++) {
            int eRed = cmm.getEntrance(i).get(Color.RED);
            int eBlue = cmm.getEntrance(i).get(Color.BLUE);
            int eGreen = cmm.getEntrance(i).get(Color.GREEN);
            int ePink= cmm.getEntrance(i).get(Color.PINK);
            int eYellow= cmm.getEntrance(i).get(Color.YELLOW);
            int drRed = cmm.getDiningRooms(i).get(Color.RED);
            int drBlue = cmm.getDiningRooms(i).get(Color.BLUE);
            int drGreen = cmm.getDiningRooms(i).get(Color.GREEN);
            int drPink = cmm.getDiningRooms(i).get(Color.PINK);
            int drYellow = cmm.getDiningRooms(i).get(Color.YELLOW);

            System.out.println("    " + cmm.getPlayers()[i].toUpperCase(Locale.ROOT) + "\n" +
                    " ___________________ \n" +
                    "|    " + (cmm.getProfessors().get(Color.RED) == i ? renderColor("P", Color.RED) : " " )+"  "+ (cmm.getProfessors().get(Color.BLUE) == i ? renderColor("P", Color.BLUE) : " " ) +"  "+(cmm.getProfessors().get(Color.GREEN) == i ? renderColor("P", Color.GREEN) : " " )+"  "+ (cmm.getProfessors().get(Color.PINK) == i ? renderColor("P", Color.PINK) : " " ) +"  "+(cmm.getProfessors().get(Color.YELLOW) == i ? renderColor("P", Color.YELLOW) : " " )+"  |\n" +
                    "|-------------------|\n" +
                    "| E: " + renderColor((eRed < 10 ? " " : "" ) + eRed, Color.RED) +" "+ renderColor((eBlue < 10 ? " " : "" )+ eBlue, Color.BLUE) +" "+ renderColor((eGreen < 10 ? " " : "") + eGreen, Color.GREEN)+" "+ renderColor((ePink < 10 ? " " : "" )+ ePink, Color.PINK)+" "+renderColor((eYellow < 10 ? " " : "" )+ eYellow, Color.YELLOW)+" |\n" +
                    "|DR: " + renderColor((drRed < 10 ? " " : "" ) + drRed, Color.RED) +" "+ renderColor((drBlue < 10 ? " " : "" )+ drBlue, Color.BLUE) +" "+ renderColor((drGreen < 10 ? " " : "" )+ drGreen, Color.GREEN)+" "+ renderColor((drPink < 10 ? " " : "" )+ drPink, Color.PINK)+" "+renderColor((drYellow < 10 ? " " : "" )+ drYellow, Color.YELLOW)+" |\n" +
                    "|-------------------|\n" +
                    "| $" + ((cmm.getCoins(i) < 10 ? " " : "") + cmm.getCoins(i))+"               |\n" +
                    "|___________________|\n");
        }
    }*/

    @Override
    public void createGame(int numberOfPlayer, boolean expert, ClientModelManager cmm){
        this.cmm = cmm;
        this.bs = new BoardStatus(numberOfPlayer, expert);
    }

    @Override
    public void printStatus() {
        if(clientController.getPlayingPlayer() == -1)
            return;
        bs.printStatus(cmm, clientController);
    }

    @Override
    public void showPublicRooms(List<RoomInfo> rooms) {
        System.out.println(ansi().render("@|bg_black,bold,fg_yellow Public Games:|@").bgDefault().fgDefault());
        if(rooms.isEmpty()) {
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
            case NACK -> System.out.println("NACK");
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
                System.out.println("Player " + pd.username() + " has left the room \n" +"Press anything to return to Lobby. ");
                input.nextLine();
                // todo: kill "game process"
            }
            default ->
                System.out.print(message.getMessageType());
        }

    }

    protected void printClear() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }


}
