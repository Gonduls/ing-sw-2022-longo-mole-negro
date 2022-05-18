package it.polimi.ingsw.client.view.cli;
import java.io.IOException;
import java.io.PrintStream;
import java.util.*;

import it.polimi.ingsw.client.view.UI;
import it.polimi.ingsw.messages.Message;
import it.polimi.ingsw.server.RoomInfo;
import org.fusesource.jansi.Ansi;
import org.fusesource.jansi.Ansi.*;
import java.util.concurrent.TimeUnit;


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

    public static void mainCLI(){
        gameTitle();


    }

    public static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    public static void gameTitle() {
        //System.out.println(ANSIColors.CYAN_BRIGHT);
        System.out.println("                                                                                                                                                        \n" +
                "                                                                                                                                                      \n" +
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
                "                                                                                                                  ......:!Y5P5?7!^:.....              \n" +
                "                                                                                                                                                        ");
        System.out.println( Ansi.ansi().eraseScreen().render("@|red Hello|@ @|green World|@") );

    }

    public static void hexagones() {
        System.out.println("   ____\n" +
                "         /    \\\n" +
                "        /      \\\n" +
                "        \\      /\n" +
                "         \\____/   ");


    }


    @Override
    public void printStatus() {

    }

    @Override
    public void showPublicRooms(List<RoomInfo> rooms) {

    }

    @Override
    public void showMessage(Message message) {

    }
}
