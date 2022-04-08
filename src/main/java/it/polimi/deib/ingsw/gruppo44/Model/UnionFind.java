package it.polimi.deib.ingsw.gruppo44.Model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * class to manage the islands
 * @author FedeMazzu
 */
public class UnionFind {

    private Island [] islands;
    private int [] group; // where the ID of the group is sorted for each island
    private int size; //number of groups of islands
    private int [] groupSize;

    public UnionFind(){
        islands = new Island[12];
        group = new int [12];
        groupSize = new int [12];
        size = 12;
        ArrayList <Color> tempArr = new ArrayList<>();
        Random rand = new Random();
        for(Color c:Color.values()){
            tempArr.add(c);
            tempArr.add(c);
        }
        for(int i=0;i<12;i++){

            group[i] = -1;
            islands[i] = new Island(this,i);
            if(i%6 != 0){
                //adding one student of a random color from five pairs
                //no need to subtract from the notOwnedObj since this amount is constant
                int randIndex = rand.nextInt(tempArr.size());
                Color tempColor = tempArr.get(randIndex);
                islands[i].addStudent(tempColor);
                tempArr.remove(randIndex);

            }
            groupSize[i] = 1;
        }
    }



    public int findGroup(int is){
        if(group[is] == -1) return is;
        group[is] = findGroup(group[is]);
        return group[is];
    }

    /**
     *
     * @param is1 the island with Mother Nature
     * @param is2 the island getting merged
     */
    public void merge(Island is1, Island is2){
        int group1 = findGroup(is1.getIslandID());
        int group2 = findGroup(is2.getIslandID());
        group[group2] = group1;
        mergeInfluence(is1,is2);
        size--;
        groupSize[group1] += groupSize[group2]; // groupSize[group2] will not be called again beacuse the parent is not -1
    }

    private void mergeInfluence(Island is1, Island is2){
        for(Color color : Color.values()){
            is1.setStudents(color, is2.getStudentNum(color)+ is1.getStudentNum(color));
            is2.setStudents(color,0);
        }
    }

    public int getGroupSize(int id){
        return groupSize[findGroup(id)];
    }

    public int getSize() {
        return size;
    }

    public Island getIsland(int index){ return islands[index];}
}
