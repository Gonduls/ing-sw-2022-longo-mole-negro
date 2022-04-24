package it.polimi.ingsw.model;

public enum AssistantCard {
        ONE(1,1),
        TWO(2,1),
        THREE(3,2),
        FOUR(4,2),
        FIVE(5,3),
        SIX(6,3),
        SEVEN(7,4),
        EIGHT(8,4),
        NINE(9,5),
        TEN(10,5);

        AssistantCard(int parAssistant, int parSteps){
            value = parAssistant;
            steps = parSteps;
        }

    private final  int value;
    private final int steps;

    public int getValue() {
        return value;
    }

    public int getSteps() {
        return steps;
    }
}
