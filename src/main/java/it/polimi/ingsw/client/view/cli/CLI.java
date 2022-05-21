package it.polimi.ingsw.client.view.cli;
import java.io.PrintStream;
import java.util.*;

import it.polimi.ingsw.client.ClientController;
import it.polimi.ingsw.client.ClientModelManager;
import it.polimi.ingsw.client.view.UI;
import it.polimi.ingsw.messages.Message;
import it.polimi.ingsw.messages.PublicRooms;
import it.polimi.ingsw.model.Color;
import it.polimi.ingsw.server.Lobby;
import it.polimi.ingsw.server.RoomInfo;

import java.util.concurrent.TimeUnit;

import static org.fusesource.jansi.Ansi.ansi;


public class CLI implements UI {

    private PrintStream output;
    private Scanner input;
    private String[] args;
    //private ClientSocket client;

    public CLI(String[] args) {
        this.output = System.out;
        this.input = new Scanner(System.in);
        this.args = args.clone();
        //listening to each other
    }

    public void start(){
        //todo: de-commentare sotto
        //AnsiConsole.systemInstall();
        gameTitle();
        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.print(ansi().fgCyan() + "Welcome! \n" +
                "Would you like to play? \n" +
                ansi().render("@|underline Insert the Server IP address: |@") + "\n");
        System.out.print(
                ansi().render("@|fg_cyan,underline Insert the Server Port: |@") + "\n");
        //check connection

        System.out.print(ansi().fgCyan() + "Now your " +
                ansi().render("@|underline nickname: |@") + "\n");
        //check nickname

        System.out.print(ansi().fgCyan() +"Here's the list of Public Games currently available \n");
        //showPublicRooms(PublicRooms.getRooms());

        System.out.print(ansi().fgCyan() +"What would you like to do? \n" +
                "CREATE NEW GAME \n" +
                "JOIN PUBLIC GAME \n" +
                "JOIN PRIVATE GAME \n");
        //parse input


        ansi().cursorDownLine();
        moves();
        schoolCard();


    }

    /*public void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }*/

    public void gameTitle() {
        //System.out.println(ANSIColors.CYAN_BRIGHT);
        System.out.print("                                                                                                                                                      \n" +
                "        ..:^~~!~!!??!!77!!!!!!!!!7.                      ^!!!~.                                                                                       \n" +
                "     .!?YYYYJJJGPB#P??JJ??Y5GBBBBG?                     7GGBBPP                                             .^                                        \n" +
                "   .?YPJ~:.   ?G#&&?        .:!G&#P7                    ?@&&&&G                                            .PB.                                       \n" +
                "  ^PP#!      JG&&&&?            :YBGJ                   .JGG5!.                                           :5G#.                                       \n" +
                "  PG#J      :##&&&&7   :^~!!!~.   .!Y. .:::.    .^::.    .^^:            ..:^^^^::     .::.    .:^:.   .^75B&B~^~.  .^~~.     .!?!^    .:~~~~^::^!:   \n" +
                " .P##~      :B&&&&&~.^?5PGPY555^     .~?5PP?  ^?YPP5J: ^?YPP5J.       :~JYYJJ5PPPP.  ^7YP5J  :7YPP5Y^ :JYG#&&GYJ?..~?PBGY~.  ~PB&#57. ~?5YYY5PP55Y.   \n" +
                " .5#B!      .G&&&&#7?P#BJ^.  ?B!    ~?JB&##5^75B&&&&B^~!^Y&&&G:     ^75BB7:.^#&&&P.:??G&&&P~!5B#&&&#?  .:G&&&G: .!JG#&@@&G7. ^J#&&&G?~G&P~....!GG:    \n" +
                "  J##J.     .G&&#&BP&B?.     ~?.    :.^B&##5JY~:P&&G~    ?&&&B:   .7Y#&G:   ^B&&&P....G&&&GJP?:!#&&&J   .P&&&P. ~!~:.^P&&&B?:  :G&&&J~#&BY?!^:...     \n" +
                "  .P&P7:    .G#&&&&&P:                :G&&#P7. .PG!.     J#&&B:   !5#&#~    ^B#&&P.  .P&&&BY:  ~#&&&J   .P&&&P.       .Y&&&G?:  J&&5..J&&&&#G5J7^.    \n" +
                "   .Y#GJ!:. .G###&&G.               . :B&&&5.  .^.       J#&&B:  .J#&&B~    ~B&&&P.  .P&&&B:   ~#&&&?.. .P&&&P.        .J#&&G?^^5#J. ..:?G#&&&&#GJ^   \n" +
                "     :7JJ!!^.G###&#7              .!?.:B&&&5.            J#&&G:  .P&&&B7::~?JB&&&5.  .P&&&B:   ~#&&#J   .P&&&G. .:.     .?&&&G??G?. .??^ ..~JP#&&B7   \n" +
                "            .P#&&&B:             .!J^ .P&&&J.            7#&&P:.:.?&&&&#GGG5!G&&&Y..:.J&&&P.   :B&&#7.:..5&&&5:~!!.      .7#&&BY~  ..7B?^..  .^P&#^   \n" +
                "            .Y&&&&G:         ..:^JP~  .5&#P~             :B&&G~^: .J#&&&#Y^  7#&&J~^..?&&#7    .5&&&!^:. ~&&&BJ!.  .   ... ~#&Y. .....Y#GJ!^^^~5P~.   \n" +
                "         .:^?#&&&&&J~^^^:^^^~7YG##~   .^!:.               .!?!:     :!7~.     :!7~.   ^7^.      .!7!:.   .^77~.        ....^GJ.........:~!!~^^:.      \n" +
                "         ..:~~~~~~~~^^:::::^^~~~~:                                                                                    ...:?BJ.........                \n" +
                "                                                                                                                     ...^Y&G......                    \n" +
                "                                                                                                                    ...^P&&?..........                \n" +
                "                                                                                                                   ....?&&&5:.....:~^...              \n" +
                "                                                                                                                  .....~B&&&Y~^:^^7!.....             \n" +
                "                                                                                                                  ......:!Y5P5?7!^:.....              \n");


        //System.out.println( Ansi.ansi().eraseScreen().render("@|red Hello|@ @|green World|@") );

    }

    public void islandPrint(int islandIndex, ClientModelManager cmm) {
        for(int i = 0; i < islandIndex; i++) {
                System.out.println("    ________     \n" +
                        "   /        \\    \n" +
                        "  /  "+ cmm.getStudentsInIsland(i).get(Color.RED) +"    " + cmm.getStudentsInIsland(i).get(Color.BLUE) +"    \\   \n" +
                        " /   "+ cmm.getStudentsInIsland(i).get(Color.GREEN) +"    " + cmm.getStudentsInIsland(i).get(Color.PINK) +"         \\  \n" +
                        " \\  "+ cmm.getStudentsInIsland(i).get(Color.YELLOW) +"          /  \n" +
                        "  \\          /   \n" +
                        "   \\________/    \n");


        }




    }

    public void moves() {
        System.out.println("   ______________________________     \n" +
                " / \\                             \\.   \n" +
                "|   |     What's your move?      |.   \n" +
                " \\_ |                            |.   \n" +
                "    |  -PLAY_ASSISTANT_CARD      |.   \n" +
                "    |                            |.   \n" +
                "    |  -MOVE_STUDENT             |.   \n" +
                "    |                            |.   \n" +
                "    |  -ACTIVATE_CHARACTER_CARD  |.   \n" +
                "    |                            |.   \n" +
                "    |  -MOVE_MOTHER_NATURE       |.   \n" +
                "    |                            |.   \n" +
                "    |   _________________________|___ \n" +
                "    |  /                            /.\n" +
                "    \\_/____________________________/. \n");
    }

    public void schoolCard() {
        System.out.println(" NAME             \n" +
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
        System.out.println(ansi().render("@|fg_black,bold,bg_yellow Public Games:|@") );
        for(RoomInfo room : rooms){
            if(room.isPrivate() == false) {
                System.out.println("______________________________________________________________");
                System.out.print(room.toString());
            }
        }

    }

    @Override
    public void showMessage(Message message) {

    }

    @Override
    public void createGameView(int numberOfPlayer, boolean expert, ClientModelManager cmm){
        islandPrint(cmm.getIslands().size(), cmm);


    }

}
