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

public class BoardStatus {
    private final Coordinates[] clouds, schools;
    private final List<Coordinates> islands;
    private final int[] islandsRemove;
    private final boolean expert;

    public BoardStatus(int numberOfPlayers, boolean expert){
        this.expert = expert;
        islands = new ArrayList<>();
        clouds = new Coordinates[numberOfPlayers];
        schools = new Coordinates[numberOfPlayers];
        islandsRemove = new int[11];

        for(int i = 0; i<12; i++){
            switch (i){
                case 0 -> {
                    islands.add(new Coordinates(3, 60));
                    islandsRemove[i] = 0;
                }
                case 1 -> {
                    islands.add(new Coordinates(3, 77));
                    islandsRemove[i] = 1;
                }
                case 2 -> {
                    islands.add(new Coordinates(3, 94));
                    islandsRemove[i] = 0;
                }
                case 3 -> {
                    islands.add(new Coordinates(3, 111));
                    islandsRemove[i] = 0;
                }
                case 4 -> {
                    islands.add(new Coordinates(10, 121));
                    islandsRemove[i] = 4;
                }
                case 5 -> {
                    islands.add(new Coordinates(18, 121));
                    islandsRemove[i] = 4;
                }
                case 6 -> {
                    islands.add(new Coordinates(25, 111));
                    islandsRemove[i] = 2;
                }
                case 7 -> {
                    islands.add(new Coordinates(25, 94));
                    islandsRemove[i] = 7;
                }
                case 8 -> {
                    islands.add(new Coordinates(25, 77));
                    islandsRemove[i] = 5;
                }
                case 9 -> {
                    islands.add(new Coordinates(25, 60));
                    islandsRemove[i] = 3;
                }
                case 10 -> {
                    islands.add(new Coordinates(18, 49));
                    islandsRemove[i] = 1;
                }
                default -> islands.add(new Coordinates(10, 49));

            }
        }

        for(int i = 0; i < numberOfPlayers; i++){
            schools[i] = new Coordinates(1 + 8*i, 146);
        }

        Coordinates[] cloudsC = new Coordinates[]{new Coordinates(11, 73), new Coordinates(12, 95), new Coordinates(17, 73), new Coordinates(19, 97)};
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
    }

    public void merge(){
        islands.remove(islandsRemove[islands.size() - 2]);
    }

    public List<Coordinates> getIslands() {
        return islands;
    }

    public void printStatus(ClientModelManager cmm, ClientController cc){
        printClear();

        AnsiConsole.systemInstall();
        printClouds(cmm);
        printIslands(cmm);
        printSchools(cmm, cc.getPlayers());
        printCards(cmm, cc);
        printInfo(cmm.getPlayers()[cc.getPlayingPlayer()], cc.getPhase(), cc.getActions());
        AnsiConsole.out().print(Ansi.ansi().cursor(40, 10));
        AnsiConsole.systemUninstall();
    }

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
            lines.add("  \\_______/ " + i);

            ansi.cursor(y -1, x);
            for(String s : lines){
                ansi.cursorDownLine().cursorRight(x).a(s);
            }

            // printing noEntries
            if(ci.getNoEntry() > 0){
                ansi.cursor(y + 1, x + 7).a(render("X", Ansi.Color.RED));
            }

            // printing MN
            ansi.cursor(y + 2, x +5);
            if(cmm.getMotherNature() == i){
                ansi.a(render("M", Ansi.Color.CYAN));
            }

            //printing Towers
            ansi.cursorRight(3);
            if(ci.getTowers() >0){
                switch (ci.getTc()) {
                    case BLACK -> ansi.a(render("T:" + ci.getTowers(), Ansi.Color.BLACK));
                    case WHITE -> ansi.a(render("T:" + ci.getTowers(), Ansi.Color.WHITE));
                    case GREY -> ansi.a(render("T:" + ci.getTowers(), Ansi.Color.CYAN));
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

    void printSchools(ClientModelManager cmm, String[] players){
        Ansi ansi = Ansi.ansi();
        int j = 0;

        for(Coordinates c : schools){
            int x = c.column();
            int y = c.row();

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

            ansi.cursor(y + 7, x + 3);
            if(expert)
                ansi.a("$" + cmm.getCoins(j));

            ansi.cursorRight(1);
            // todo: change tower colors
            if(cmm.getTowers(j) > 0)
                ansi.a(render("T" + cmm.getTowers(j), Ansi.Color.WHITE)).cursorLeft(2);

            ansi.cursorRight(4).a(players[j]);

            AnsiConsole.out().print(ansi);
            j++;

        }
    }

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

    public void printInfo(String player, GamePhase phase, List<String> actions){
        Ansi ansi = Ansi.ansi();
        ansi.cursor(2, 8);
    }

    void printCards(ClientModelManager cmm, ClientController cc){
        Ansi ansi = Ansi.ansi();
        List<String> lines = new ArrayList<>();

        lines.add(" ______________________________ ");

        if(expert){
            // adding expert part
            lines.add("| Card used in this turn:      |");
            lines.add("| Available cards:             |");
            lines.add("|   ) $                        |");
            lines.add("|   ) $                        |");
            lines.add("|   ) $                        |");
            lines.add("|------------------------------|");

            // preemptively filling expert part
            ansi.cursor(20, 34).a(cc.getActiveCharacterCard() == -1 ? "no" : cc.getActiveCharacterCard());
            Integer[] indexes = cmm.getCharactersIndexes().toArray(Integer[]::new);

            ansi.cursor(21, 27).a(indexes[0]+ ", " + indexes[1]+ ", " + indexes[2]);

            ansi.cursor( 22, 10);
            for(int index : indexes){
                if(index < 10)
                    ansi.cursorRight(1);

                ansi.a(index).cursorRight(3).a(cmm.getPrice(index)).cursorRight(2);

                Map<Color, Integer> studs = cmm.getCharacterStudents(index);
                if(studs != null){
                    ansi.a("Students: ");
                    for(Color color: Color.values())
                        ansi.a(renderColor(studs.get(color), color)).cursorRight(1);
                }

                if(index == 5)
                    ansi.a(cmm.getNoEntries());

                ansi.cursorDownLine().cursorRight(10);
            }

            // moving cursor to "expert position"
            ansi.cursor(18, 0);
        } else{
            // moving cursor to "normal mode position"
            ansi.cursor(24, 0);
        }

        lines.add("| Assistant card left:         |");
        lines.add("|                              |");
        lines.add("| Steps for card:              |");
        lines.add("|                              |");
        lines.add("| Assistant played this turn:  |");
        lines.add("|                              |");
        lines.add("|______________________________|");

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
        ansi.cursor(31, 10);
        for(int i = 0; i < 4; i++){
            if(cc.getAssistantCardsPlayed()[i] != -1) {
                ansi.a("(" + i + ", " + cc.getAssistantCardsPlayed()[i] + ") ");
            }
        }



        AnsiConsole.out().print(ansi);
    }

    void printClear() {
        AnsiConsole.systemInstall();
        Ansi ansi = Ansi.ansi();
        System.out.println( ansi.eraseScreen() );
        System.out.println( ansi.cursor(0, 0) );
        AnsiConsole.systemUninstall();
    }

    public Object renderColor(Object object, Color color){
        return switch (color) {
            case RED -> ansi().fgBrightRed().a(object).fgDefault();
            case BLUE -> ansi().fgBrightCyan().a(object).fgDefault();
            case GREEN -> ansi().fgBrightGreen().a(object).fgDefault();
            case PINK -> ansi().fgBrightMagenta().a(object).fgDefault();
            case YELLOW -> ansi().fgBrightYellow().a(object).fgDefault();
        };
    }
    public Object render(Object object, Ansi.Color color){
        return ansi().fgBright(color).a(object).fgDefault();
    }
}
