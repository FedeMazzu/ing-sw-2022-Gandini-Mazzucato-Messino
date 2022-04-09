package it.polimi.deib.ingsw.gruppo44.Model;

import java.util.HashMap;
import java.util.Map;

/**
 * class to manage the clouds
 * @author zenomess
 */

public class Cloud {
    private Map<Color, Integer> students;
    //private boolean empty;
    private int sizeMod;

    public Cloud(int sizeMod){
        this.sizeMod = sizeMod;
        students = new HashMap<>();
        for (Color color : Color.values()) {
            students.put(color, 0);
        }
        //empty=true;
    }

    /*public boolean isEmpty() {
        return empty;
    }*/


    public void addStudent(Color color){
        students.put(color,students.get(color)+1);
        //empty=false;
    }

    /**
     * method to wipe the clouds clean
     */
    public void wipeCloud(Player player){
        School school = player.getSchool();
        int numStudents;
        for (Color color : Color.values()) {
            numStudents = students.get(color);
            for(int i=0; i<numStudents; i++){
                school.addEntranceStudent(color);
            }
            students.put(color, 0);
        }
        //empty = true;
    }

    /**
     * useful just for testing
     * @param color
     * @return the number of students of the passed color on the island
     */
    public int getStudentsNum(Color color){
        return students.get(color);
    }


}