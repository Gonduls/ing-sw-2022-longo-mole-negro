package it.polimi.ingsw.model;

public interface StudentHolderObserver {

    /**
     * It is called every time a student is added or removed from a StudentHolder,
     * as long as the observer id attached to that StudentHolder
     */
    void update();
}
