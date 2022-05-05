package it.polimi.deib.ingsw.gruppo44.Server.Model;

import it.polimi.deib.ingsw.gruppo44.Server.VirtualView.IslandsData;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * class to manage the islands
 * @author FedeMazzu
 */
public class UnionFind implements Serializable {

    private Island [] islands;
    private int [] group; // where the ID of the group is sorted for each island
    private int size; //number of groups of islands
    private int [] groupSize;
    private IslandsObserver islandsObserver;
    public List<Team> teams;
    private int characterUsed;
    private int currTeamIndexForChar8;
    private Color colorChosenForChar9;

    public UnionFind(Game game){
        islands = new Island[12];
        group = new int [12];
        groupSize = new int [12];
        size = 12;
        characterUsed = 0;
        currTeamIndexForChar8 = -1;
        ArrayList <Color> tempArr = new ArrayList<>();
        teams = game.getTeams();
        Random rand = new Random();
        for(Color c:Color.values()){
            tempArr.add(c);
            tempArr.add(c);
        }
        for(int i=0;i<12;i++){

            group[i] = -1;
            islands[i] = new Island(this,i);
            groupSize[i] = 1;
        }
        // setting up the islands observer
        islandsObserver = new IslandsObserver(islands);
        IslandsData islandsData = islandsObserver.getIslandsData();
        for(int i=0; i<12; i++){
            islands[i].setIslandsObserver(islandsObserver);
            //to initialize the group values in the VirtualView
            islandsData.setGroup(i, group[i]);
            islandsData.setGroupSize(i,groupSize[i]);
        }
        //initializing the students on the islands
        for(int i=0; i<12; i++){
            if (i % 6 != 0) {
                //adding one student of a random color from five pairs
                //no need to subtract from the notOwnedObj since this amount is constant
                int randIndex = rand.nextInt(tempArr.size());
                Color tempColor = tempArr.get(randIndex);
                islands[i].addStudent(tempColor);
                tempArr.remove(randIndex);
            }
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
        mergeInfluence(getIsland(group1),getIsland(group2)); //vanno passate  le isole dei capigruppi non le isole is1 e is2
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

    public int getGroup(int islandId){
        return group[islandId];
    }
    public IslandsObserver getIslandsObserver() {
        return islandsObserver;
    }

    public void setCharacterUsed(int characterUsed) {
        this.characterUsed = characterUsed;
    }

    public int getCharacterUsed() {
        return characterUsed;
    }

    public int getCurrTeamIndexForChar8() {
        return currTeamIndexForChar8;
    }

    public void setCurrTeamIndexForChar8(int currTeamIndexForChar8) {
        this.currTeamIndexForChar8 = currTeamIndexForChar8;
    }

    public Color getColorChosenForChar9() {
        return colorChosenForChar9;
    }

    public void setColorChosenForChar9(Color colorChosenForChar9) {
        this.colorChosenForChar9 = colorChosenForChar9;
    }
}