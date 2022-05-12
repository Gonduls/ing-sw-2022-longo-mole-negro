package it.polimi.ingsw.messages;


public record EndGame(String[] winners) implements Message {

    @Override
    public MessageType getMessageType() {
        return MessageType.END_GAME;
    }

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

    @Override
    public int hashCode() {
        StringBuilder sb = new StringBuilder();
        for(String s : winners){
            sb.append(s);
        }
        return sb.hashCode();
    }

    @Override
    public String toString() {
        if(winners.length == 1){
            return "The winner is: " + winners[0] + "!";
        }

        return "The winners are: " + winners[0] + ", " + winners[1] + "!";
    }
}

