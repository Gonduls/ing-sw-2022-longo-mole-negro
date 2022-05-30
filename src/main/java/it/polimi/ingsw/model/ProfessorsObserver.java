package it.polimi.ingsw.model;

public class ProfessorsObserver implements StudentHolderObserver{
    private final StudentHolder observed;
    private final Player player;
    private final Professors professors;
    private final  GameManager gameManager;

    /**
     * Observer that automatically updates the professors' ownership
     * everytime a student is added or removed from the target Player's tables
     * @param player: the Player that has the School that contains the tables to be observed
     * @param professors: the professors to be updated
     */
    ProfessorsObserver(Player player, Professors professors, GameManager gameManager){
        this.observed = player.getSchool().getStudentsAtTables();
        this.player = player;
        this.professors = professors;
        this.gameManager = gameManager;
    }

    /**
     * Checks if the current owner of the professor has fewer students in tables than the observed Player:
     * if it does, it switches ownerships
     */
    public void update(){
        for(Color color : Color.values()){
            if(observed.getStudentByColor(color) == 0)
                continue;

            if(professors.getOwners().get(color) == null) {
                professors.setToPlayer(color, player);
                continue;
            }


            Player previousOwner = professors.getOwners().get(color);
            //the card that changes the rule for assigning professors
            if(gameManager.getUsedCard() == 4){
                if(previousOwner.getSchool().getStudentsAtTables().getStudentByColor(color) <= observed.getStudentByColor(color))
                    professors.setToPlayer(color, player);
            } else {
                if (previousOwner.getSchool().getStudentsAtTables().getStudentByColor(color) < observed.getStudentByColor(color))
                    professors.setToPlayer(color, player);
            }

        }
    }
}
