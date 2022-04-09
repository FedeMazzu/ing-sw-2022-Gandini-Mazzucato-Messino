package it.polimi.deib.ingsw.gruppo44.Model;

import java.util.HashMap;
import java.util.Map;

/**
 * class to manage the clouds
 * @author filippogandini
 */

public class Cloud {
    private Map<Color, Integer> students;
    private boolean empty;
    private int sizeMod;

    public Cloud(int sizeMod){
        this.sizeMod = sizeMod;
        students = new HashMap<>();
        for (Color color : Color.values()) {
            students.put(color, 0);
        }
        empty=true;
    }

    public boolean isEmpty() {
        return empty;
    }

    public int getStudentsNum(Color color){
        return students.get(color);
    }

    public void setStudents (Color color,int val){
        int sum = 0;
        for(Color color1 : Color.values()){
            sum += getStudentsNum(color1);
        }
        if((sum+val)<=sizeMod){
            {students.put(color, val);}}
        empty=false;
    }

    /**
     * method to wipe the clouds clean
     */

    public void wipeCloud(){
        for (Color color : Color.values()) {
            while(getStudentsNum(color)>0){
                students.remove(color, 1);
            }
        }
        empty = true;
    }

}


