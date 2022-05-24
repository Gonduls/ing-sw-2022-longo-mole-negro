package it.polimi.ingsw.model;

import it.polimi.ingsw.server.ModelObserver;

import java.util.EnumMap;

public class Professors {
    ModelObserver modelObserver;
    private final EnumMap<Color, Player> owners;

    /**
     * Keeps track of which Player controls which Professor.
     * If no Player controls a professor, its owner is the default value: null
     */
    Professors(){
        owners = new EnumMap<>(Color.class);
        for(Color color : Color.values())
            owners.put(color, null);
    }

    void setModelObserver(ModelObserver modelObserver){
        this.modelObserver = modelObserver;
    }

    /**
     * Sets the target professor's ownership to player
     * @param professor: target professor
     * @param player: target player
     */
    void setToPlayer(Color professor, Player player){
        owners.put(professor, player);
        if(modelObserver != null) {
            modelObserver.setProfessorTo(professor, player.getPlayerNumber());
        }
    }

    /**
     * @return a copy of owners via an EnumMap
     */
    EnumMap<Color, Player> getOwners(){
        return (new EnumMap<>(owners));
    }
}
