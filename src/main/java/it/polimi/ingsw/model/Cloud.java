package it.polimi.ingsw.model;

import it.polimi.ingsw.exceptions.NoSpaceForStudentException;
import it.polimi.ingsw.exceptions.NoSuchStudentException;

public class Cloud extends StudentHolder{
    final int size;
    final Bag bag;

    /**
     * The representation of the in-game cloud, will hold 3 (or 4) students depending on how many players there are
     * @param size: studentsMax as per the definition of StudentHolder
     * @param bag: needed in order to refill the cloud
     */
    public Cloud(int size, Bag bag){
        super(size, size);
        this.size = size;
        this.bag = bag;
    }

    /**
     * Empties the Cloud in the entrance of the target School
     * @param player: The player that owns the entrance
     * @throws NoSpaceForStudentException if the entrance of the target School is still full
     * @throws NoSuchStudentException if the cloud was already empty
     */
    void moveAllStudents(Player player) throws NoSpaceForStudentException, NoSuchStudentException {
        boolean empty = true;
        for(Color c: Color.values()){
            for(int i = getStudentByColor(c); i>0; i--) {
                moveStudentTo(c, player.getSchool().getStudentsAtEntrance());
                empty = false;
            }
        }
        if(empty)
            throw new NoSuchStudentException("This cloud was already empty");
    }

    /**
     * Automatically refills the cloud by taking random students from the bag
     * @throws NoSpaceForStudentException if the cloud was still full
     */
    void refill() throws NoSpaceForStudentException{
        for(int i = 0; i< size; i++){
            addStudent(bag.extractRandomStudent());
        }
    }
}
