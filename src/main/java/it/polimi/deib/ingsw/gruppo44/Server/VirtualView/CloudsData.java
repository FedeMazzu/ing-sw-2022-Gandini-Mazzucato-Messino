package it.polimi.deib.ingsw.gruppo44.Server.VirtualView;

import it.polimi.deib.ingsw.gruppo44.Server.Model.Color;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Clouds in the virtual view
 * Note: the index of the List identifies the cloud
 */
public class CloudsData implements Serializable {
    private final List<Map<Color,Integer>> students;

    public CloudsData(int cloudsNumber){
        students = new ArrayList<>();
        for(int i=0; i< cloudsNumber; i++) students.add(new HashMap<>());
    }

    /**
     * @param cloudId - Cloud to set the value
     * @param color    to set the number of students
     * @param value    - number of students
     */
    public void setStudentsNum(int cloudId, Color color, int value) {
        students.get(cloudId).put(color, value);
    }

    public int getStudentsNum(int cloudId, Color color) {
        return students.get(cloudId).get(color);
    }

    public boolean isEmpty(int cloudId) {
        return students.get(cloudId)
                .entrySet()
                .stream()
                .noneMatch(colorIntegerEntry -> colorIntegerEntry.getValue() > 0);
    }

}
