package it.polimi.ingsw.model;

import java.util.EnumMap;

public class Professors {
    private static Professors singleton;
    private final EnumMap<Color, Player> owners;

    private Professors(){
        owners = new EnumMap<>(Color.class);
        for(Color color : Color.values())
            owners.put(color, null);
    }

    public static Professors getInstance(){
        if (singleton == null)
            singleton = new Professors();

        return singleton;
    }

    void setToPlayer(Color color, Player player){
        owners.put(color, player);
    }

    EnumMap<Color, Player> getOwners(){
        return (new EnumMap<>(owners));
    }
}
