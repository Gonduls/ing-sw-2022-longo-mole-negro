package it.polimi.ingsw.model;

public class TablesObserver implements StudentHolderObserver{
    private final StudentHolder observed;
    private final Player player;
    private final Professors professors;

    TablesObserver(Player player, StudentHolder observed, GameManager gameManager){
        this.observed = observed;
        this.player = player;
        professors = gameManager.getProfessors();
    }

    public void update(){
        updateProfessors();
    }

    public void updateProfessors(){
        for(Color color : Color.values()){
            Player previousOwner = professors.getOwners().get(color);

            if(previousOwner.getSchool().getStudentsAtTables().getStudentByColor(color) < observed.getStudentByColor(color))
                professors.setToPlayer(color, player);
        }
    }
}
