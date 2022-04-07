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

    public Cloud(int playerNum){
        int size;
        if (playerNum == 3) size = 4;
        else size = 3;
        sizeMod = size;
        students = new HashMap<>();
        for (Color color : Color.values()) {
            students.put(color, 0);
        }
    }


        public boolean isEmpty() {return empty;}


        public int getStudentsNum(Color color){
            return students.get(color);
        }

        public void setStudents (Color color,int val){
            students.put(color, val);
        }

        public int getSizeMod () {
            return sizeMod;
        }

        /**
         * method to wipe the clouds clean
         */

        public void wipeCloud(Cloud cloud){
            for (Color color : Color.values()) {
                students.remove(color, 4);
            }
            empty = true;
        }

    }
