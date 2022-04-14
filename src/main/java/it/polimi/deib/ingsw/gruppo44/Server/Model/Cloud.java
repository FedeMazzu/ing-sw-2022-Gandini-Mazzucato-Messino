package it.polimi.deib.ingsw.gruppo44.Server.Model;

import it.polimi.deib.ingsw.gruppo44.Observable;

import java.util.HashMap;
import java.util.Map;

/**
 * class to manage the clouds
 * @author zenomess
 */

public class Cloud implements Observable {
    private Map<Color, Integer> students;
    private int cloudId;
    private int sizeMod;
    private CloudsObserver cloudsObserver;

    public Cloud(int sizeMod, int id){
        this.sizeMod = sizeMod;
        this.cloudId = id;
        students = new HashMap<>();
        for (Color color : Color.values()) {
            students.put(color, 0);
        }
    }

    @Override
    public void notifyObserver() {
        cloudsObserver.update(cloudId);
    }

    public void addStudent(Color color){
        try{
            int numStudents = 0;
            for(Color col : Color.values()) numStudents += students.get(col);
            if(numStudents >= sizeMod) throw new Exception();
            students.put(color,students.get(color)+1);
            notifyObserver();
        }catch(Exception e ){
            e.printStackTrace();
        }

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
        notifyObserver();
    }

    /**
     * useful just for testing
     * @param color
     * @return the number of students of the passed color on the island
     */
    public int getStudentsNum(Color color){
        return students.get(color);
    }

    public int getCloudId() {
        return cloudId;
    }

    public void setCloudsObserver(CloudsObserver cloudsObserver) {
        this.cloudsObserver = cloudsObserver;
    }
}