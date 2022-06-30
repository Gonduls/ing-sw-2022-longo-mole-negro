package it.polimi.ingsw.client.view.gui;

import javafx.scene.image.Image;

public final class RedirectResources {

    private RedirectResources() {

    }

    public static Image characterCardsImages(int ccIndex) {
        switch (ccIndex) {
            case 0 -> {return new Image(String.valueOf(RedirectResources.class.getResource("/images/Personaggi/CC0.png")));}
            case 1 -> {return new Image(String.valueOf(RedirectResources.class.getResource("/images/Personaggi/CC1.png")));}
            case 2 -> {return new Image(String.valueOf(RedirectResources.class.getResource("/images/Personaggi/CC2.png")));}
            case 3 -> {return new Image(String.valueOf(RedirectResources.class.getResource("/images/Personaggi/CC3.png")));}
            case 4 -> {return new Image(String.valueOf(RedirectResources.class.getResource("/images/Personaggi/CC4.png")));}
            case 5 -> {return new Image(String.valueOf(RedirectResources.class.getResource("/images/Personaggi/CC5.png")));}
            case 6 -> {return new Image(String.valueOf(RedirectResources.class.getResource("/images/Personaggi/CC6.png")));}
            case 7 -> {return new Image(String.valueOf(RedirectResources.class.getResource("/images/Personaggi/CC7.png")));}
            case 8 -> {return new Image(String.valueOf(RedirectResources.class.getResource("/images/Personaggi/CC8.png")));}
            case 9 -> {return new Image(String.valueOf(RedirectResources.class.getResource("/images/Personaggi/CC9.png")));}
            case 10 -> {return new Image(String.valueOf(RedirectResources.class.getResource("/images/Personaggi/CC10.png")));}
            case 11 -> {return new Image(String.valueOf(RedirectResources.class.getResource("/images/Personaggi/CC11.png")));}
            default -> {return null;}
        }
    }

    public static Image characterCardsDescImages(int ccIndex) {
        switch (ccIndex) {
            case 0 -> {return new Image(String.valueOf(RedirectResources.class.getResource("/images/Personaggi/DescCC0.png")));}
            case 1 -> {return new Image(String.valueOf(RedirectResources.class.getResource("/images/Personaggi/DescCC1.png")));}
            case 2 -> {return new Image(String.valueOf(RedirectResources.class.getResource("/images/Personaggi/DescCC2.png")));}
            case 3 -> {return new Image(String.valueOf(RedirectResources.class.getResource("/images/Personaggi/DescCC3.png")));}
            case 4 -> {return new Image(String.valueOf(RedirectResources.class.getResource("/images/Personaggi/DescCC4.png")));}
            case 5 -> {return new Image(String.valueOf(RedirectResources.class.getResource("/images/Personaggi/DescCC5.png")));}
            case 6 -> {return new Image(String.valueOf(RedirectResources.class.getResource("/images/Personaggi/DescCC6.png")));}
            case 7 -> {return new Image(String.valueOf(RedirectResources.class.getResource("/images/Personaggi/DescCC7.png")));}
            case 8 -> {return new Image(String.valueOf(RedirectResources.class.getResource("/images/Personaggi/DescCC8.png")));}
            case 9 -> {return new Image(String.valueOf(RedirectResources.class.getResource("/images/Personaggi/DescCC9.png")));}
            case 10 -> {return new Image(String.valueOf(RedirectResources.class.getResource("/images/Personaggi/DescCC10.png")));}
            case 11 -> {return new Image(String.valueOf(RedirectResources.class.getResource("/images/Personaggi/DescCC11.png")));}
            default -> {return null;}
        }
    }

    public static Image studentsImages(String color) {
        switch (color) {
            case "RED" -> {return new Image(String.valueOf(RedirectResources.class.getResource("/images/Elements/RoundRed.png")));}
            case "BLUE" -> {return new Image(String.valueOf(RedirectResources.class.getResource("/images/Elements/RoundBlue.png")));}
            case "GREEN" -> {return new Image(String.valueOf(RedirectResources.class.getResource("/images/Elements/RoundGreen.png")));}
            case "PINK" -> {return new Image(String.valueOf(RedirectResources.class.getResource("/images/Elements/RoundPink.png")));}
            case "YELLOW" -> {return new Image(String.valueOf(RedirectResources.class.getResource("/images/Elements/RoundYellow.png")));}
            default -> {return null;}

        }

    }

    public static Image minTowersImages(String color) {
        switch (color) {
            case "WHITE" -> {return new Image(String.valueOf(RedirectResources.class.getResource("/images/Elements/MinWhiteTower.png")));}
            case "GREY" -> {return new Image(String.valueOf(RedirectResources.class.getResource("/images/Elements/MinGreyTower.png")));}
            case "BLACK" -> {return new Image(String.valueOf(RedirectResources.class.getResource("/images/Elements/MinBlackTower.png")));}
            default -> {return null;}

        }

    }

    public static Image towersImages(String color) {
        switch (color) {
            case "WHITE" -> {return new Image(String.valueOf(RedirectResources.class.getResource("/images/Elements/WhiteTower.png")));}
            case "GREY" -> {return new Image(String.valueOf(RedirectResources.class.getResource("/images/Elements/GreyTower.png")));}
            case "BLACK" -> {return new Image(String.valueOf(RedirectResources.class.getResource("/images/Elements/BlackTower.png")));}
            default -> {return null;}
        }

    }

    public static Image ACImages(int acIndex) {
        switch (acIndex) {
            case 1 -> {return new Image(String.valueOf(RedirectResources.class.getResource("/images/Elements/Assistente1.png")));}
            case 2 -> {return new Image(String.valueOf(RedirectResources.class.getResource("/images/Elements/Assistente2.png")));}
            case 3 -> {return new Image(String.valueOf(RedirectResources.class.getResource("/images/Elements/Assistente3.png")));}
            case 4 -> {return new Image(String.valueOf(RedirectResources.class.getResource("/images/Elements/Assistente4.png")));}
            case 5 -> {return new Image(String.valueOf(RedirectResources.class.getResource("/images/Elements/Assistente5.png")));}
            case 6 -> {return new Image(String.valueOf(RedirectResources.class.getResource("/images/Elements/Assistente6.png")));}
            case 7 -> {return new Image(String.valueOf(RedirectResources.class.getResource("/images/Elements/Assistente7.png")));}
            case 8 -> {return new Image(String.valueOf(RedirectResources.class.getResource("/images/Elements/Assistente8.png")));}
            case 9 -> {return new Image(String.valueOf(RedirectResources.class.getResource("/images/Elements/Assistente9.png")));}
            case 10 -> {return new Image(String.valueOf(RedirectResources.class.getResource("/images/Elements/Assistente10.png")));}
            default -> {return null;}
        }
    }

    public static Image getDeckImages(int index) {
        switch (index) {
            case 0 -> {
                return new Image(String.valueOf(RedirectResources.class.getResource("/images/Elements/Back1.png")));
            }
            case 1 -> {
                return new Image(String.valueOf(RedirectResources.class.getResource("/images/Elements/Back2.png")));
            }
            case 2 -> {
                return new Image(String.valueOf(RedirectResources.class.getResource("/images/Elements/Back3.png")));
            }
            case 3 -> {
                return new Image(String.valueOf(RedirectResources.class.getResource("/images/Elements/Back4.png")));
            }
            default -> {return null;}
        }

    }

    public static Image getNoEntryImage() {
        return new Image(String.valueOf(RedirectResources.class.getResource("/images/Elements/NoEntry.png")));
    }

    public static String fromURLtoElement(String url) {
        int index = url.lastIndexOf('/');
        String name = url.substring(index+1);
        if (name.indexOf(".") > 0)
            name = name.substring(0, name.lastIndexOf("."));

        name = name.replace("Min", "");
        switch (name) {
            case "RoundRed"-> {return "RED";}
            case "RoundPink" -> {return "PINK";}
            case "RoundYellow" -> {return "YELLOW";}
            case "RoundBlue" -> {return "BLUE";}
            case "RoundGreen" -> {return "GREEN";}
            case "MotherNature" -> {return "MOTHERNATURE";}
            case "NoEntry" -> {return "NOENTRY";}
            default -> {return name;}
        }
    }
}
