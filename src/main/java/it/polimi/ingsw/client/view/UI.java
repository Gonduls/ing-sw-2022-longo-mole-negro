package it.polimi.ingsw.client.view;

import it.polimi.ingsw.server.RoomInfo;

import java.util.List;

public interface UI {

    //todo: check functions' names, add functions
    void printStatus();
    void showPublicRooms(List<RoomInfo> rooms);
}
