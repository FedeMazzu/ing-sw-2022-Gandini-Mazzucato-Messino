package it.polimi.deib.ingsw.gruppo44.Server.Model;

import it.polimi.deib.ingsw.gruppo44.Server.Observable;

import java.io.Serializable;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Class to manage the clouds
 */

public class Cloud implements Observable, Serializable {
    private final Map<Color, Integer> students;
    private final int cloudId;
    private final int sizeMod;
    private CloudsObserver cloudsObserver;

    public Cloud(final int sizeMod,
                 final int id){
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
    public boolean addStudent(final Color color) {
        final int numStudents = Arrays
                .stream(Color.values())
                .mapToInt(students::get)
                .sum();
        if(breachCloudLimit(numStudents)) {
            System.out.println("Cloud already full");
            return false;
        } else {
            students.put(color,students.get(color)+1);
            notifyObserver();
            return true;
        }
    }

    private boolean breachCloudLimit (final int numOfStudents) {
        return numOfStudents >= sizeMod;
    }

    /**
     * @return true if the cloud doesn't contain students
     */
    public boolean isEmpty() {
        return students
                .entrySet()
                .stream()
                .noneMatch(colorIntegerEntry -> colorIntegerEntry.getValue() > 0);
    }

    /**
     * Method to wipe the clouds clean
     */
    public void wipeCloud(final Player player) {
        School school = player.getSchool();

        Arrays.stream(Color.values())
                .forEach(color -> {
                    addStudentsToSchool(school, color);
                    resetStudentsForColor(color);
                });
        notifyObserver();
    }

    private void resetStudentsForColor(final Color color) {students.put(color, 0);}

    private void addStudentsToSchool(
            final School school,
            final Color color) {
        for(int i=0; i<students.get(color); i++) {
            school.addEntranceStudent(color);
        }
    }

    /**
     * Visible for testing
     * @param color of the students you want to know the number of
     * @return the number of students of the passed color on the island
     */
    public int getStudentsNum(final Color color) {return students.get(color);}

    public int getCloudId() {return cloudId;}

    public void setCloudsObserver(
            final CloudsObserver cloudsObserver) {
        this.cloudsObserver = cloudsObserver;}
}