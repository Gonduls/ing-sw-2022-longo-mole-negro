package it.polimi.ingsw.client.view.cli;

import it.polimi.ingsw.client.ClientController;
import it.polimi.ingsw.client.ClientIsland;
import it.polimi.ingsw.client.ClientModelManager;
import it.polimi.ingsw.controller.GamePhase;
import it.polimi.ingsw.model.AssistantCard;
import it.polimi.ingsw.model.Color;
import org.fusesource.jansi.Ansi;
import org.fusesource.jansi.AnsiConsole;

import java.util.*;

import static org.fusesource.jansi.Ansi.ansi;

/**
 * Takes care of all the board related prints in CLI
 */
public class BoardStatus {
    private final Coordinates[] clouds, schools;
    private final List<Coordinates> islands = new ArrayList<>();
    private final boolean expert;

    /**
     * Initializes the board status by setting coordinates and items based on parameters passed.
     * @param numberOfPlayers The number of playing players, 2, 3 or 4
     * @param expert True if the game is expert mode, false otherwise
     */
    public BoardStatus(int numberOfPlayers, boolean expert){
        this.expert = expert;
        clouds = new Coordinates[numberOfPlayers];
        schools = new Coordinates[numberOfPlayers];

        for(int i = 0; i<12; i++){
            switch (i){
                case 0 -> islands.add(new Coordinates(5, 64));
                case 1 -> islands.add(new Coordinates(3, 79));
                case 2 -> islands.add(new Coordinates(3, 95));
                case 3 -> islands.add(new Coordinates(5, 110));
                case 4 -> islands.add(new Coordinates(10, 122));
                case 5 -> islands.add(new Coordinates(18, 122));
                case 6 -> islands.add(new Coordinates(23, 110));
                case 7 -> islands.add(new Coordinates(25, 95));
                case 8 -> islands.add(new Coordinates(25, 79));
                case 9 -> islands.add(new Coordinates(23, 64));
                case 10 -> islands.add(new Coordinates(18, 51));
                default -> islands.add(new Coordinates(10, 51));

            }
        }

        for(int i = 0; i < numberOfPlayers; i++){
            schools[i] = new Coordinates(1 + 8*i, 148);
        }

        Coordinates[] cloudsC = new Coordinates[]{new Coordinates(11, 75), new Coordinates(12, 97), new Coordinates(17, 75), new Coordinates(19, 99)};
        if(numberOfPlayers == 4){
            clouds[0] = cloudsC[0];
            clouds[1] = cloudsC[1];
            clouds[2] = cloudsC[2];
            clouds[3] = cloudsC[3];
        } else {
            clouds[0] = cloudsC[1];
            clouds[1] = cloudsC[2];
            if (numberOfPlayers == 3)
                clouds[2] = cloudsC[3];
        }
        printClear();
    }

    /**
     * Removes the island coordinates at the target index, from the list containing all islands' coordinates
     * @param islandIndex The target island to be removed from the view
     */
    public void merge(int islandIndex){
        islands.remove(islands.get(islandIndex));
    }

    /**
     * Refreshes the board by clearing all the target lines and printing back on top
     * @param cmm Has the pieces of information regarding students, towers and professors
     * @param cc Has the pieces of information regarding Assistant Cards, Character Cards, phase, turn, players
     */
    public void printStatus(ClientModelManager cmm, ClientController cc){
        printClearBoard();

        AnsiConsole.systemInstall();
        printClouds(cmm);
        printIslands(cmm);
        printSchools(cmm, cc.getPlayers());
        printCards(cmm, cc);
        printInfo(cmm.getPlayers()[cc.getPlayingPlayer()], cc.getPhase(), cc.getActions());
        AnsiConsole.out().print(Ansi.ansi().cursor(40, 13).a("> "));
        AnsiConsole.systemUninstall();
    }

    /**
     * For all islands in the model, prints island content
     * @param cmm The model containing needed information
     */
    void printIslands(ClientModelManager cmm){
        Ansi ansi = Ansi.ansi();
        int i = 0;
        for(ClientIsland ci :cmm.getIslands()){
            Coordinates c = islands.get(i);
            int x = c.column();
            int y = c.row();
            List<String> lines = new ArrayList<>();
            lines.add("   _______");
            lines.add("  /       \\");
            lines.add(" /         \\");
            lines.add("/           \\");
            lines.add("\\           /");
            lines.add(" \\         /");
            lines.add("  \\_______/" + i);

            ansi.cursor(y -1, x);
            for(String s : lines){
                ansi.cursorDownLine().cursorRight(x).a(s);
            }

            // printing noEntries
            if(ci.getNoEntry() > 0){
                ansi.cursor(y + 1, x + 7).a(render("X", 0xff0000));
            }

            // printing MN
            ansi.cursor(y + 2, x +5);
            if(cmm.getMotherNature() == i){
                ansi.a(render("M", 0xff5d00)).fgDefault();
            }

            //printing Towers
            ansi.cursor(y + 2, x +8);
            if(ci.getTowers() >0 && ci.getTc() != null){
                switch (ci.getTc()) {
                    case BLACK -> ansi.a("T:").fgBrightBlack().a(ci.getTowers()).fgDefault();
                    case WHITE -> ansi.a("T:" + ci.getTowers());
                    case GREY -> ansi.a("T:").a(render(ci.getTowers(), 0xa8a8a8));
                }
            }

            //printing students
            ansi.cursor(y + 4, x +3);
            ansi.a(renderColor(ci.getStudents().get(Color.RED), Color.RED)).cursorRight(3);
            ansi.a(renderColor(ci.getStudents().get(Color.GREEN), Color.GREEN)).cursorRight(3);
            ansi.a(renderColor(ci.getStudents().get(Color.PINK), Color.PINK)).cursor(y + 5, x + 5);
            ansi.a(renderColor(ci.getStudents().get(Color.YELLOW), Color.YELLOW)).cursorRight(3);
            ansi.a(renderColor(ci.getStudents().get(Color.BLUE), Color.BLUE)).cursorRight(2);
            AnsiConsole.out().print(ansi);
            i++;
        }
    }

    /**
     * Prints all schools with relative player name and coins
     * @param cmm The model containing needed information
     * @param players The playing players' names
     */
    void printSchools(ClientModelManager cmm, String[] players){
        Ansi ansi = Ansi.ansi();
        int j = 0;

        for(Coordinates c : schools){
            int x = c.column();
            int y = c.row();

            // printing template
            List<String> lines = new ArrayList<>();
            lines.add(" ___________________");
            lines.add("| " + j + "                 |");
            lines.add("|-------------------|");
            lines.add("|  E:               |");
            lines.add("| DR:               |");
            lines.add("|-------------------|");
            lines.add("|                   |");
            lines.add("|___________________|");

            ansi.cursor(y, x);
            for(String s : lines){
                ansi.cursorDownLine().cursorRight(x).a(s);
            }

            // printing username
            if (CLI.getInstance().getUsername().equals(players[j]))
                ansi.cursor(y + 2, x +4).a("★");

            // printing professors, dining rooms and entrances
            ansi.cursor(y + 2, x + 7);
            for(Color color : Color.values()){
                if(cmm.getProfessors().get(color) == j)
                    ansi.a(renderColor("P", color));
                else
                    ansi.a(" ");

                ansi.cursorLeft(1).cursorDown(2);

                ansi.a(renderColor(cmm.getEntrance(j).get(color), color)).cursorDown(1).cursorLeft(1);
                ansi.a(renderColor(cmm.getDiningRooms(j).get(color), color)).cursorRight(2).cursorUp(3);

            }

            // printing coins
            ansi.cursor(y + 7, x + 3);
            if(expert)
                ansi.a("$" + cmm.getCoins(j));

            // printing towers
            ansi.cursorRight(1);
            if(cmm.getTowers(j) > 0){
                switch (j) {
                    case 0 -> ansi.fgBrightBlack().a("T:B" + cmm.getTowers(j)).fgDefault();
                    case 1 -> ansi.a("T:W" + cmm.getTowers(j));
                    default -> ansi.a(render("T:G" + cmm.getTowers(j), 0xa8a8a8a8));
                }
            } else {
                if (j % 2 == 0) {
                    ansi.fgBrightBlack().a("T:B").fgDefault();
                } else {
                    ansi.a("T:W");
                }
            }

            ansi.cursorRight(2).a(players[j]);


            AnsiConsole.out().print(ansi);
            j++;

        }
    }

    /**
     * For all clouds in the model, prints cloud content
     * @param cmm The model containing needed information
     */
    void printClouds(ClientModelManager cmm){
        Ansi ansi = Ansi.ansi();
        int i = 0;

        for(Coordinates c : clouds) {
            int x = c.column();
            int y = c.row();
            List<String> lines = new ArrayList<>();
            lines.add("    __        ");
            lines.add("  (`   ). " + i);
            lines.add("(  0  0  ')'`.");
            lines.add("( 0  0  0  ) )");
            lines.add(" ` __.:'-' ");

            ansi.cursor(y - 1, x);
            for(String s : lines){
                ansi.cursorDownLine().cursorRight(x).a(s);
            }

            ansi.cursor(y + 2, x + 4 );
            ansi.a(renderColor(cmm.getCloud(i).get(Color.YELLOW), Color.YELLOW)).cursorRight(2);
            ansi.a(renderColor(cmm.getCloud(i).get(Color.BLUE), Color.BLUE)).cursorLeft(5).cursorDown(1);
            ansi.a(renderColor(cmm.getCloud(i).get(Color.RED), Color.RED)).cursorRight(2);
            ansi.a(renderColor(cmm.getCloud(i).get(Color.GREEN), Color.GREEN)).cursorRight(2);
            ansi.a(renderColor(cmm.getCloud(i).get(Color.PINK), Color.PINK)).cursorRight(2);

            AnsiConsole.out().print(ansi);
            i++;
        }
    }

    /**
     * Prints the player that is playing the turn, the phase that he is in and
     * the possible actions available at that given point
     * @param player The player that has to play the turn
     * @param phase The phase the playing player is in
     * @param actions The actions currently available
     */
    public void printInfo(String player, GamePhase phase, List<String> actions){
        Ansi ansi = Ansi.ansi();

        List<String> lines = new ArrayList<>();
        lines.add(" _________________________________");
        lines.add("| Turn:                           |");
        lines.add("|---------------------------------|");
        lines.add("| Phase:                          |");
        lines.add("|---------------------------------|");
        lines.add("| Possible actions:               |");
        String empty = "|                                 |";
        lines.add(empty);
        lines.add(empty);
        lines.add(empty);
        lines.add(empty);
        lines.add(empty);
        lines.add(empty);
        lines.add(empty);
        lines.add(empty);
        lines.add("|_________________________________|");

        ansi.cursor(2, 8);
        for (String s : lines) {
            ansi.cursorDownLine().cursorRight(8).a(s);
        }
        ansi.cursor(4, 18).a(player);
        String phaseS = null;
        if(phase != null)
            switch (phase){
                case PLANNING_PHASE -> phaseS = "Planning";
                case ACTION_PHASE_ONE -> phaseS = "Move Students";
                case ACTION_PHASE_TWO -> phaseS = "Move Mother Nature";
                case ACTION_PHASE_THREE -> phaseS = "Choose Cloud";
            }

        ansi.cursor(6, 20).a(phaseS);

        ansi.cursor(9, 0);
        for(String s : actions){
            ansi.cursorRight(10).a(s).cursorDownLine();
        }
        AnsiConsole.out().print(ansi);
    }

    /**
     * Prints Assistant card deck with relative maximum step per card,
     * if the mode is expert prints information relative to character cards
     * @param cmm The model containing needed information
     * @param cc Contains cards played during current turn
     */
    void printCards(ClientModelManager cmm, ClientController cc){
        Ansi ansi = Ansi.ansi();
        List<String> lines = new ArrayList<>();

        ansi.cursor(24, 0);

        lines.add(" _________________________________ ");
        lines.add("| Assistant card left:            |");
        lines.add("|                                 |");
        lines.add("| Steps for assistant card:       |");
        lines.add("|                                 |");
        lines.add("| Assistant played this turn:     |");
        lines.add("|                                 |");
        lines.add("|_________________________________|");

        for(String s : lines)
            ansi.cursorDownLine().cursorRight(8).a(s);

        // filling assistant card info
        List<AssistantCard> deck = cmm.getDeck();
        ansi.cursor(27, 11);
        for(AssistantCard ac : deck){
            if(ac.getValue() != 10) {
                ansi.a(ac.getValue()).cursorLeft(1).cursorDown(2);
                ansi.a(ac.getSteps()).cursorRight(2).cursorUp(2);
            }
            else{
                ansi.cursorLeft(1).a(ac.getValue()).cursorLeft(1).cursorDown(2);
                ansi.a(ac.getSteps());
            }
        }
        ansi.cursor(31, 11);
        for(int i = 0; i < 4; i++){
            if(cc.getAssistantCardsPlayed()[i] != -1) {
                ansi.a("(" + i + ", " + cc.getAssistantCardsPlayed()[i] + ") ");
            }
        }

        if(expert){
            // adding expert part
            lines = new ArrayList<>();
            lines.add(" _________________________________ ");
            lines.add("| Card used in this turn:         |");
            lines.add("| Available cards:                |");
            lines.add("|   ) $                           |");
            lines.add("|   ) $                           |");
            lines.add("|   ) $                           |");
            lines.add("|---------------------------------|");

            // adding empty expert part
            ansi.cursor(18, 0);
            for(String s : lines)
                ansi.cursorDownLine().cursorRight(8).a(s);

            // filling expert part
            ansi.cursor(20, 36).a(cc.getActiveCharacterCard() == -1 ? "no" : cc.getActiveCharacterCard());
            Integer[] indexes = cmm.getCharactersIndexes().toArray(Integer[]::new);

            ansi.cursor(21, 29).a(indexes[0]+ ", " + indexes[1]+ ", " + indexes[2]);

            ansi.cursor( 22, 11);
            for(int index : indexes){
                if(index < 10) {
                    ansi.cursorRight(1);
                }

                ansi.a(index).cursorRight(3).a(cmm.getPrice(index)).cursorRight(2);

                Map<Color, Integer> studs = cmm.getCharacterStudents(index);
                if(studs != null){
                    ansi.a("Students: ");
                    for(Color color: Color.values())
                        ansi.a(renderColor(studs.get(color), color)).cursorRight(1);
                }

                if(index == 5) {
                    ansi.a("No entries: " + cmm.getNoEntries());
                }

                ansi.cursorDownLine().cursorRight(10);
            }
            AnsiConsole.out().print(ansi);
        }

        ansi.cursor(40,0);
        AnsiConsole.out().print(ansi);
    }

    /**
     * Clears out all rows containing board elements
     */
    void printClearBoard() {
        AnsiConsole.systemInstall();
        Ansi ansi = Ansi.ansi();

        ansi.cursor(0, 0);
        for(int i = 0; i < 40 ; i++){
            AnsiConsole.out().print(ansi().eraseLine(Ansi.Erase.FORWARD));
        }
        AnsiConsole.systemUninstall();
    }

    /**
     * erases everything
     */
    static void printClear(){
        AnsiConsole.systemInstall();
        Ansi ansi = Ansi.ansi();
        System.out.println( ansi.eraseScreen() );
        System.out.println( ansi.cursor(0, 0) );
        AnsiConsole.systemUninstall();
    }

    /**
     * Creates an ansi object based on the color passed, " " if the passed object is 0
     * @param object The object that is to be converted to an ansi colored object
     * @param color The chosen color
     * @return An ansi object based on the color passed
     */
    private Object renderColor(Object object, Color color){

        if (object.getClass() == Integer.class){
            int a = (Integer) object;
            if (a == 0)
                return " ";
        }

        return switch (color) {
            case RED -> ansi().fgRgb(0xee2e2c).a(object).fgDefault();
            case BLUE -> ansi().fgBrightCyan().a(object).fgDefault();
            case GREEN -> ansi().fgBrightGreen().a(object).fgDefault();
            case PINK -> ansi().fgRgb(0xff3996).a(object).fgDefault();
            case YELLOW -> ansi().fgBrightYellow().a(object).fgDefault();
        };
    }

    /**
     * Renders an object into an ansi colored object
     * @param object The object to be rendered
     * @param hexadecimal The color of the rendering
     * @return The rendered object
     */
    private Object render(Object object, int hexadecimal){
        return ansi().fgRgb(hexadecimal).a(object).fgDefault();
    }
}
