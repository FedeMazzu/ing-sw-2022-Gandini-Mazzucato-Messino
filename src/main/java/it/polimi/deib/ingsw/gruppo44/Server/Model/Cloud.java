package it.polimi.deib.ingsw.gruppo44.Server.Model;

import it.polimi.deib.ingsw.gruppo44.Server.Observable;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * class to manage the clouds
 */

public class Cloud implements Observable, Serializable {
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

    /**
     *
     * @param color of the student to add
     * @return a boolean which indicates if the cloud was filled correctly
     */
    public boolean addStudent(Color color){
        try{
            int numStudents = 0;
            for(Color col : Color.values()) numStudents += students.get(col);
            if(numStudents >= sizeMod) throw new Exception();
            students.put(color,students.get(color)+1);
            notifyObserver();
            return true;
        }catch(Exception e ){
            System.out.println("Cloud already full");
            return false;
        }

    }

    /**
     * @return true if the cloud doesn't contain students
     */
    public boolean isEmpty(){
        boolean isEmpty = true;
        for(Color color : Color.values()){
            if(students.get(color)>0){
                isEmpty =false;
                break;
            }
        }
        return isEmpty;
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