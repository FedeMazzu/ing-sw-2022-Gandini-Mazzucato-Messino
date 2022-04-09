package it.polimi.deib.ingsw.gruppo44.Model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * class to represent the islands
 * @author filippogandini
 */
public class Island {
    private UnionFind unionFind;
    private Map<Color, Integer> students;
    private Tower tower;
    private int islandID;
    //private List<Team> teams;
    private boolean hasTower;

    public Island(UnionFind uf,int  id){
        islandID = id;
        students = new HashMap<>();
        for(Color color:Color.values()) {
            students.put(color, 0);
        }
        unionFind = uf;
        hasTower = false;

    }

    private int influence(Team team){
        int score;
        School school;
        if(!hasTower) score = 0;
        else score = unionFind.getGroupSize(this.islandID);

        for(Player player : team.getPlayers()){
            school = player.getSchool();
            for(Color color : Color.values()){
                if(school.hasProfessor(color)) score += students.get(color);
            }
        }
        return score;
    }

    public void islandClaim(){
        int bestScore = 0;
        Team bestTeam = null;
        for(Team t : unionFind.teams){
            int tempScore = influence(t);
            if(tempScore > bestScore){
                bestScore = tempScore;
                bestTeam = t;
            }
        }

        try{
            if(getTowerColor().equals(bestTeam.getTower()) && getHasTower()) return;
        }
        catch (NullPointerException e){
            e.getMessage(); //Non deve succedere nulla il messaggio e` per debug ç
        }

        //From here on if there are towers they are of a color that is not of the bestTeam

        if(getHasTower()){
            //there is at least one tower
            int islandSize = unionFind.getGroupSize(islandID);
            for(Team t : unionFind.teams){
                if(t.getTower() == getTowerColor()) t.setTowerCount(t.getTowerCount() + islandSize);
            }
            tower = bestTeam.getTower();
            bestTeam.setTowerCount(bestTeam.getTowerCount() - islandSize); // negative count is ok as long as the win condition checks for it
        }
        else{
            //there aren't any towers
            tower = bestTeam.getTower();
            bestTeam.setTowerCount(bestTeam.getTowerCount() - 1);
            hasTower = true;
        }

        //check for merge conditions

        int currGroup = unionFind.findGroup(islandID);
        int pos = currGroup;

        //forward
        while(currGroup == unionFind.findGroup(pos)){
            pos = (pos+1)%12;
        }
        Island forwardGroup = unionFind.getIsland(pos);
        if(forwardGroup.hasTower && unionFind.getIsland(pos).getTowerColor().equals(tower)){
            unionFind.merge(this,forwardGroup);
        }

        //check win conditions ç

        //backward
        pos = currGroup;
        while(currGroup == unionFind.findGroup(pos)){
            pos = (pos-1)%12;
        }
        Island backwardGroup = unionFind.getIsland(pos);
        if(backwardGroup.hasTower && unionFind.getIsland(pos).getTowerColor().equals(tower)){
            unionFind.merge(this,backwardGroup);
        }

        //check win conditions ç

    }

    public int getIslandID() {
        return islandID;
    }

    public int getStudentNum(Color color){
        return students.get(color);
    }

    public void setStudents(Color color, int val){
        students.put(color,val);
    }

    public void addStudent(Color color) {students.put(color,students.get(color)+1);}

    public boolean getHasTower() {
        return hasTower;
    }

    public Tower getTowerColor(){ return tower;};


}