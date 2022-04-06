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
    private List<Team> teams;
    private boolean hasTower;

    public Island(UnionFind uf,int  id){
        islandID = id;
        students = new HashMap<>();
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

    public void islandClaim(Island island){
        int bestScore = 0;
        Team bestTeam;
        //mi serve arrivare alle squadre
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

    public boolean getHasTower() {
        return hasTower;
    }

    public Tower getTowerColor(){ return tower};


}
