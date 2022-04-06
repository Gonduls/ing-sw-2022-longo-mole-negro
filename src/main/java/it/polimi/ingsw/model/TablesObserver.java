package it.polimi.ingsw.model;

public class TablesObserver implements StudentHolderObserver{
    private final StudentHolder observed;
    private final Player player;

    TablesObserver(Player player, StudentHolder observed){
        this.observed = observed;
        this.player = player;
    }

    public void update(){
        Professors professors = Professors.getInstance();
        for(Color color : Color.values()){
            Player previousOwner = professors.getOwners().get(color);

            if(previousOwner.getSchool().getStudentsAtTables().getStudentByColor(color) < observed.getStudentByColor(color))
                professors.setToPlayer(color, player);
        }
    }
}
