package it.polimi.ingsw.client.view.gui;

import javafx.scene.image.Image;

public class RedirectResources {

    public Image characterCardsImages(int ccIndex) {
        switch (ccIndex) {
            case 1 -> {return new Image(String.valueOf(getClass().getResource("/images/Personaggi/CC0.png")));}
            case 2 -> {return new Image(String.valueOf(getClass().getResource("/images/Personaggi/CC1.png")));}
            case 0 -> {return new Image(String.valueOf(getClass().getResource("/images/Personaggi/CC2.png")));}
            case 3 -> {return new Image(String.valueOf(getClass().getResource("/images/Personaggi/CC3.png")));}
            case 4 -> {return new Image(String.valueOf(getClass().getResource("/images/Personaggi/CC4.png")));}
            case 5 -> {return new Image(String.valueOf(getClass().getResource("/images/Personaggi/CC5.png")));}
            case 6 -> {return new Image(String.valueOf(getClass().getResource("/images/Personaggi/CC6.png")));}
            case 7 -> {return new Image(String.valueOf(getClass().getResource("/images/Personaggi/CC7.png")));}
            case 8 -> {return new Image(String.valueOf(getClass().getResource("/images/Personaggi/CC8.png")));}
            case 9 -> {return new Image(String.valueOf(getClass().getResource("/images/Personaggi/CC9.png")));}
            case 10 -> {return new Image(String.valueOf(getClass().getResource("/images/Personaggi/CC10.png")));}
            default -> {return new Image(String.valueOf(getClass().getResource("/images/Personaggi/CC11.png")));}
        }
    }

    public Image studentsImages(String color) {
        switch (color) {
            case "RED" -> {return new Image(String.valueOf(getClass().getResource("/images/Elements/RoundRed.png")));}
            case "BLUE" -> {return new Image(String.valueOf(getClass().getResource("/images/Elements/RoundBlue.png")));}
            case "GREEN" -> {return new Image(String.valueOf(getClass().getResource("/images/Elements/RoundGreen.png")));}
            case "PINK" -> {return new Image(String.valueOf(getClass().getResource("/images/Elements/RoundPink.png")));}
            case "YELLOW" -> {return new Image(String.valueOf(getClass().getResource("/images/Elements/RoundYellow.png")));}
            default -> {return new Image(String.valueOf(getClass().getResource("/images/Elements/RoundRed.png")));}

        }

    }
}
