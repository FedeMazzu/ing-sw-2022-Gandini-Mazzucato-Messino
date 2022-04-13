package it.polimi.deib.ingsw.gruppo44.Server.VirtualView;

import it.polimi.deib.ingsw.gruppo44.Server.Model.Color;
import it.polimi.deib.ingsw.gruppo44.Server.Model.Island;
import it.polimi.deib.ingsw.gruppo44.Server.Model.Tower;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Islands in the virtual view
 * Note that the index of the arrays/List corresponds to the island identifier
 * @author filippogandini
 */

public class IslandsData {
    private int group[]; //representing the connection between the island with the UnionFind conventions
    private List<Map<Color, Integer>> students;
    private Tower[] towers;
    private boolean[] hasTower;
    private int [] groupSize;

    public IslandsData() {
        students = new ArrayList<>();
        for (int i = 0; i < 12; i++) students.add(new HashMap<>());
        towers = new Tower[12];
        hasTower = new boolean[12];
        group = new int[12];
        groupSize = new int[12];
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
        group[islandId] = value;
    }
    /**
     * called for initialization and if the island gets a tower
     */
    public void setGroupSize(int islandId, int value){
        groupSize[islandId] = value;
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

}
