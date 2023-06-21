package it.polimi.deib.ingsw.gruppo44.Server.VirtualView;

import it.polimi.deib.ingsw.gruppo44.Server.Model.Color;
import it.polimi.deib.ingsw.gruppo44.Server.Model.Tower;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Islands in the virtual view
 * Note that the index of the arrays/List corresponds to the island identifier
 */

public class IslandsData implements Serializable {
    final private int group[]; //representing the connection between the island with the UnionFind conventions
    final private List<Map<Color, Integer>> students;
    final private Tower[] towers;
    final private boolean[] hasTower;
    final private int [] groupSize;
    private int numOfIslands;

    public IslandsData() {
        students = new ArrayList<>();
        for (int i = 0; i < 12; i++){
            students.add(new HashMap<>());
           }
        towers = new Tower[12];
        hasTower = new boolean[12];
        group = new int[12];
        groupSize = new int[12];
        numOfIslands = 12;
    }

    /**
     * @param islandId - Island to set the value
     * @param color    to set the number of students
     * @param value    - number of students
     */
    public void setStudentsNum(int islandId, Color color, int value) {
        students.get(islandId).put(color, value);
    }

    public void setHasTower(int islandId, boolean value) {
        hasTower[islandId] = value;
    }

    /**
     * called only if the island gets a tower
     */
    public void setTower(int islandId, Tower tower) {
        towers[islandId] = tower;
    }

    /**
     * called for initialization and if the island gets a tower
     */
    public void setGroup(int islandId, int value){
        if(value == islandId) group[islandId] = -1;
        else group[islandId] = value;
    }

    /**
     * called for initialization and if the island gets a tower
     */


    public void setGroupSize(int islandId, int value){
        groupSize[islandId] = value;
    }

    public void setNumOfIslands(int numOfIslands) {
        this.numOfIslands = numOfIslands;
    }

    public int getNumOfIslands() {
        return numOfIslands;
    }

    public int findGroup(int islandId){
        if(group[islandId] == -1) return islandId;
        group[islandId] = findGroup(group[islandId]);
        return group[islandId];
    }

    public int getGroupSize(int islandId){
        return groupSize[findGroup(islandId)];
    }

    public int getStudentsNum(int islandId, Color color){
        return students.get(islandId).get(color);
    }

    public int getGroup(int islandId) {
        return group[islandId];
    }

    public boolean getHasTower(int islandId) {
        return hasTower[islandId];
    }
    public Tower getTowerType(int islandId){
        return towers[islandId];
    }
}
