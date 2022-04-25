package it.polimi.ingsw.model;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;

public class StudentHolder {
    EnumMap<Color, Integer> students;
    int studentsMax;
    int studentsMaxColor;
    List<StudentHolderObserver> observers = new ArrayList<>();

    /**
     * Default constructor of StudentHolder, initializes all students to 0 and sets their max value to Integer.MAX_VALUE
     */
    public StudentHolder(){
        students = new EnumMap<>(Color.class);
        for(Color color : Color.values())
            students.put(color, 0);
        studentsMax = Integer.MAX_VALUE;
        studentsMaxColor = Integer.MAX_VALUE;

    }

    /**
     * Constructor of StudentHolder, initializes all students to 0 and sets their max values to the parameters
     * @param studentsMax is the total maximum number for all students (i.e. an entrance can only contain 9 students max at all times)
     * @param studentsMaxColor is the maximum number for all student in each color (i.e. tables can hold 10 students of each color)
     */
    public StudentHolder(int studentsMax, int studentsMaxColor){
        this();
        this.studentsMax = studentsMax;
        this.studentsMaxColor = studentsMaxColor;
    }

    /**
     * Returns a representation of all students via a EnumMap
     * @return a deep copy of students in the StudentHolder
     */
    public EnumMap<Color, Integer> getAllStudents() {
        return new EnumMap<>(students);
    }

    /**
     * Returns how many students with that color are in the StudentHolder
     * @param color: the color of the students
     * @return the total count of students with that color
     */
    public Integer getStudentByColor(Color color){
        return students.get(color);
    }

    /**
     * Adds a single student to the StudentHolder
     * @param student: the color of the student
     * @throws NoSpaceForStudentException if adding the student would exceed studentsMax or studentsMaxColor
     */
    void addStudent(Color student) throws NoSpaceForStudentException{
        if(students.values().stream().reduce(0, Integer::sum) == studentsMax)
            throw new NoSpaceForStudentException("No more students can be added to this holder");

        if(students.get(student) == studentsMaxColor)
            throw new NoSpaceForStudentException("No more students of this color can be added to this holder");

        students.put(student, students.get(student) + 1);
        notifyObserver();
    }

    /**
     * Removes a single student from the StudentHolder
     * @param student: the color of the student
     * @throws NoSuchStudentException if no students of the selected color are in the StudentHolder
     */
    void removeStudent(Color student) throws NoSuchStudentException{
        if(students.get(student) == 0)
            throw new NoSuchStudentException("No student of this color are present");

        students.put(student, students.get(student) - 1);
        notifyObserver();
    }

    /**
     * Attaches an observer to the StudentHolder
     * @param observer: the observer to be attached
     */
    void attach(StudentHolderObserver observer){
        observers.add(observer);
    }

    /**
     * Detaches the first occurrence of the observer from the StudentHolder
     * @param observer: the observer to be detached
     * @return true if the observer was present
     */
    boolean detach(StudentHolderObserver observer){
        return(observers.remove(observer));
    }

    /**
     * Calls observer.update() for every observer attached.
     * Is called every time a student is added or removed from the StudentHolder
     */
    void notifyObserver(){
        for (StudentHolderObserver observer : observers) observer.update();
    }

    /**
     * Moves a student from the current StudentHolder to a target StudentHolder
     * @param student: the color of the student to be moved
     * @param holder: the StudentHolder that receives the student
     * @throws NoSpaceForStudentException if calling addStudent(student) from the target StudentHolder throws it
     * @throws NoSuchStudentException if that student is not in the current StudentHolder
     */
    void moveStudentTo(Color student, StudentHolder holder) throws NoSpaceForStudentException, NoSuchStudentException{
        try{
            removeStudent(student);
            holder.addStudent(student);
        }catch (NoSpaceForStudentException e){
            addStudent(student);
            throw e;
        }
    }
}
