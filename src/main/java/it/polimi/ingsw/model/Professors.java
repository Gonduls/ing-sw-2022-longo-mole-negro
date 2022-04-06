package it.polimi.ingsw.model;

import java.util.EnumMap;

public class Professors {
    private final EnumMap<Color, Player> owners;

    Professors(){
        owners = new EnumMap<>(Color.class);
        for(Color color : Color.values())
            owners.put(color, null);
    }

    void setToPlayer(Color color, Player player){
        owners.put(color, player);
    }

    /**
     * @return a copy of owners
     */
    EnumMap<Color, Player> getOwners(){
        return (new EnumMap<>(owners));
    }
}
