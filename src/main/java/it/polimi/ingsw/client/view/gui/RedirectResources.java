package it.polimi.ingsw.client.view.gui;

import javafx.scene.image.Image;

public final class RedirectResources {

    private RedirectResources() {

    }

    public static Image characterCardsImages(int ccIndex) {
        //System.out.println("Sono in redirector CC");
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
            default -> {return new Image(String.valueOf(RedirectResources.class.getResource("/images/Personaggi/CC11.png")));}
        }
    }

    public static Image studentsImages(String color) {
        //System.out.println("sono in redirector Stud");
        switch (color) {
            case "RED" -> {return new Image(String.valueOf(RedirectResources.class.getResource("/images/Elements/RoundRed.png")));}
            case "BLUE" -> {return new Image(String.valueOf(RedirectResources.class.getResource("/images/Elements/RoundBlue.png")));}
            case "GREEN" -> {return new Image(String.valueOf(RedirectResources.class.getResource("/images/Elements/RoundGreen.png")));}
            case "PINK" -> {return new Image(String.valueOf(RedirectResources.class.getResource("/images/Elements/RoundPink.png")));}
            case "YELLOW" -> {return new Image(String.valueOf(RedirectResources.class.getResource("/images/Elements/RoundYellow.png")));}
            default -> {return new Image(String.valueOf(RedirectResources.class.getResource("/images/Elements/RoundRed.png")));}

        }

    }

    public static Image minTowersImages(String color) {
        switch (color) {
            case "WHITE" -> {return new Image(String.valueOf(RedirectResources.class.getResource("/images/Elements/MinWhiteTower.png")));}
            case "GREY" -> {return new Image(String.valueOf(RedirectResources.class.getResource("/images/Elements/MinGreyTower.png")));}
            default -> {return new Image(String.valueOf(RedirectResources.class.getResource("/images/Elements/MinBlackTower.png")));}

        }

    }

    public static Image towersImages(String color) {
        switch (color) {
            case "WHITE" -> {return new Image(String.valueOf(RedirectResources.class.getResource("/images/Elements/WhiteTower.png")));}
            case "GREY" -> {return new Image(String.valueOf(RedirectResources.class.getResource("/images/Elements/GreyTower.png")));}
            default -> {return new Image(String.valueOf(RedirectResources.class.getResource("/images/Elements/BlackTower.png")));}

        }

    }

    public static Image ACImages(int acIndex) {
        //System.out.println("Sono in redirector CC");
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
}
