package it.polimi.ingsw.messages;

/**
 * Message that states that The game has ended
 * @param winners The name of the players that won the game
 */
public record EndGame(String[] winners) implements Message {

    /**
     * @return The MessageType of this message (END_GAME)
     */
    @Override
    public MessageType getMessageType() {
        return MessageType.END_GAME;
    }

    /**
     * Standard equals
     * @param o The reference object with which to compare.
     * @return If o and this have the same winners
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EndGame m = (EndGame) o;

        if(m.winners.length != winners.length)
            return false;

        for(int i = 0; i < winners.length; i++){
            if(! winners[i].equals(m.winners[i]))
                return false;
        }

        return true;
    }

    /**
     * @return The hashCode of all winners' names appended
     */
    @Override
    public int hashCode() {
        StringBuilder sb = new StringBuilder();
        for(String s : winners){
            sb.append(s);
        }
        return sb.hashCode();
    }

    /**
     * @return The winners message
     */
    @Override
    public String toString() {
        if(winners.length == 1){
            return "The winner is: " + winners[0] + "!";
        }

        return "The winners are: " + winners[0] + ", " + winners[1] + "!";
    }
}

