package it.polimi.ingsw.model;

public class Cloud extends StudentHolder{
    final int size;
    final Bag bag;

    public Cloud(int size, GameManager gameManager){
        super(size, size);
        this.size = size;
        bag = gameManager.getBag();
    }

    void moveAllStudents(School school) throws NoSpaceForStudentException{

        for(Color c: Color.values()){
            for(int i = getStudentByColor(c); i>0; i--) {
                try{
                    moveStudentTo(c, school.getStudentsAtEntrance());
                } catch(NoSuchStudentException e){
                    System.out.println("Errors in finding students in cloud");
                }
            }
        }

    }

    void refill() throws NoSpaceForStudentException{
        for(int i = 0; i< size; i++){
            addStudent(bag.extractRandomStudent());
        }
    }
}
