package it.polimi.deib.ingsw.gruppo44.Server.Model;

import java.io.Serializable;
import java.util.*;

/**
 * class to keep track of the unused objects
 * @author zenomess
 */

public class NotOwnedObjects implements Serializable {
    //private Map<Color, Integer> students;
    private Map<Color, Boolean> freeProfessors;
    private int bankMoney;
    private GameMode gameMode;
    private List<Color> students;

    public NotOwnedObjects(GameMode gameMode){
        this.gameMode = gameMode;
        freeProfessors = new HashMap<>();
        for(Color color1 : Color.values())
            freeProfessors.put(color1, true);
        if(gameMode.isExpertMode()) bankMoney=20;
        else bankMoney=0;
        students = new ArrayList<>();
        for(Color c : Color.values()){
            for(int i=0; i<24; i++)
                students.add(c);
        }
    }

    public void giveCoin(){bankMoney--;}

    public void receiveCoin(){bankMoney++;}

    /**
     * fills the passed cloud at the beginning anf at the end of every round
     * @param cloud
     */
    public void fillCloud(Cloud cloud){
        Random rand = new Random();
        boolean studentAddedCorrectly;
        int randIndex;
        for(int i=0; i<gameMode.getCloudStudents(); i++){
            randIndex = rand.nextInt(students.size());
            Color tempColor = students.get(randIndex);;
            studentAddedCorrectly = cloud.addStudent(tempColor);
            if(studentAddedCorrectly) students.remove(tempColor);
        }

    }

    /**
     * Initializes the passed school at the creation of the game
     * @param school to initialize
     */
    public void fillEntrance(School school){
        Random rand = new Random();
        boolean studentAddedCorrectly;
        int randIndex;
        for(int i=0; i<school.getMaxEntranceStudentsNum(); i++){
            randIndex = rand.nextInt(students.size());
            Color tempColor = students.get(randIndex);;
            studentAddedCorrectly = school.addEntranceStudent(tempColor);
            if(studentAddedCorrectly) students.remove(tempColor);
        }
    }


    /**
     * @param index in the List of students
     * @return the color of the drawed student
     */
    public Color drawStudent(int index){
        return students.remove(index);
    }


    /**
     * @return the current number of unused students
     */
    public int getStudentsSize() {return students.size();}

}